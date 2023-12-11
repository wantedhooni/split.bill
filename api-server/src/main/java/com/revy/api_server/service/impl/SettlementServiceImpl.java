package com.revy.api_server.service.impl;

import com.revy.api_server.service.SettlementService;
import com.revy.api_server.service.data.*;
import com.revy.core.enums.domain.SettlementStatus;
import com.revy.core.enums.exception.ErrorCode;
import com.revy.core.exception.SettlementException;
import com.revy.core.exception.UserException;
import com.revy.core.util.BigDecimalUtils;
import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.settlement.service.SettlementManager;
import com.revy.domain.entity.settlement.service.SettlementReader;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;
import com.revy.domain.entity.settlement_user_info.service.SettlementUserInfoManager;
import com.revy.domain.entity.settlement_user_info.service.SettlementUserInfoReader;
import com.revy.domain.entity.user.User;
import com.revy.domain.entity.user.service.UserManager;
import com.revy.domain.entity.user.service.UserReader;
import com.revy.redis.aop.annotation.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.08
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private final UserManager userManager;
    private final UserReader userReader;
    private final SettlementManager settlementManager;
    private final SettlementReader settlementReader;
    private final SettlementUserInfoManager settlementUserInfoManager;
    private final SettlementUserInfoReader settlementUserInfoReader;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SettlementResultData createSettlement(CreateSettlementData createSettlementData) {
        Assert.notNull(createSettlementData, "createSettlementData must not be null.");
        Assert.notNull(createSettlementData.getRequestUserId(), "createSettlementData.requestUserId must not be null.");
        Assert.notNull(createSettlementData.getTotalAmount(), "createSettlementData.TotalAmount must not be null.");
        Assert.notEmpty(createSettlementData.getSettlementList(), "createSettlementData.SettlementList must not be null");

        // Validation - 토탈금액과 상세금액 일치여부
        BigDecimal checkAmount = createSettlementData.getSettlementList()
                .stream()
                .map(CreateSettlementData.CreateSettlementDetailData::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (BigDecimalUtils.notEq(createSettlementData.getTotalAmount(), checkAmount)) {
            throw new SettlementException(ErrorCode.AMOUNT_INCONSISTENCY);
        }

        // 데이터 생성 시작
        User requestUser = userManager.getOrCreateNewUser(createSettlementData.getRequestUserId());
        Settlement targetSettlement = settlementManager.createSettlement(requestUser, createSettlementData.getTotalAmount(), createSettlementData.getKakaoRoomId());

        List<SettlementUserInfo> settlementUserInfoList = createSettlementData.getSettlementList()
                .stream()
                .map(data -> settlementUserInfoManager.createSettlementUserInfo(requestUser, userManager.getOrCreateNewUser(data.getUserId()), targetSettlement, data.getAmount())
                ).toList();

        // 요청 당사자 정산 처리
        SettlementUserInfo requesterSettlementUserInfo = settlementUserInfoList
                .stream()
                .filter(data -> requestUser.getId().equals(data.getTargetUser().getId()))
                .findFirst().orElseThrow(() -> new SettlementException(ErrorCode.ENTITY_NOT_EXISTS));
        paySettlementBySelf(requesterSettlementUserInfo);

        // 저장
        settlementUserInfoManager.save(settlementUserInfoList);

        // PUSH
        push(targetSettlement);

        // 카카오톡 방 푸쉬는 최소생성시에만 진행한다.
        if (StringUtils.hasText(targetSettlement.getKakaoRoomId())) {
            eventPublisher.publishEvent(new KakaoRoomNotiData(targetSettlement.getKakaoRoomId(), "1/N 정산 요청이 도착하였습니다."));
        }

        return mapSettlementResultData(targetSettlement);
    }

    @Override
    @DistributedLock(key = "settlementId")
    public PaySettlementResultData paySettlement(Long userId, Long settlementId, BigDecimal paidAmount) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(settlementId, "settlementId must not be null");
        Assert.notNull(paidAmount, "paidAmount must not be null.");

        // validation
        User fromUser = userReader.findById(userId).orElseThrow(() -> new UserException(ErrorCode.ENTITY_NOT_EXISTS));
        if (BigDecimalUtils.lt(fromUser.getBalance(), paidAmount)) {
            throw new SettlementException(ErrorCode.USER_BALANCE_INSUFFICIENT);
        }

        SettlementUserInfo targetSettlementUserInfo = settlementUserInfoReader
                .findOneByUserIdAndSettlementId(userId, settlementId)
                .orElseThrow(() -> new SettlementException(ErrorCode.ENTITY_NOT_EXISTS));

        Settlement targetSettlement = targetSettlementUserInfo.getSettlement();

        settlementUserInfoManager.pay(targetSettlementUserInfo, paidAmount);
        settlementManager.pay(targetSettlement, paidAmount);

        return mapPaySettlementResultData(targetSettlementUserInfo);
    }

    @Override
    public void remind(Long requestUserId, Long settlementId) {
        Settlement targetSettlement = settlementReader.findByIdAndRequestUserId(settlementId, requestUserId)
                .orElseThrow(() -> new SettlementException(ErrorCode.ENTITY_NOT_EXISTS));
        push(targetSettlement);
    }

    @Override
    public List<SettlementResultData> getSettlementRequestList(Long userId, int pageNumber, int pageSize) {
        return settlementReader.findByUserId(userId, pageNumber, pageSize)
                .stream()
                .map(data -> mapSettlementResultData(data))
                .toList();
    }

    @Override
    public List<PaySettlementResultData> getSettlementRequestedList(Long userId, int pageNumber, int pageSize) {
        return settlementUserInfoReader.findByTargetUserId(userId, pageNumber, pageSize).stream().map(
                this::mapPaySettlementResultData).toList();
    }


    /**
     * 정산 요청자 강제 정산
     *
     * @param settlementUserInfo
     * @return
     */
    private SettlementUserInfo paySettlementBySelf(SettlementUserInfo settlementUserInfo) {
        BigDecimal paidAmount = settlementUserInfo.getTotalAmount();
        settlementUserInfoManager.pay(settlementUserInfo, paidAmount);
        settlementManager.pay(settlementUserInfo.getSettlement(), paidAmount);
        return settlementUserInfo;
    }

    /**
     * 노티 이벤트를 발행합니다.
     *
     * @param targetSettlement
     */
    private void push(Settlement targetSettlement) {
        if (targetSettlement.getStatus() == SettlementStatus.COMPLETE) {
            return;
        }

        settlementUserInfoReader.findBySettlementId(targetSettlement.getId()).stream()
                .filter(data -> data.getStatus() != SettlementStatus.COMPLETE)
                .forEach(data -> eventPublisher.publishEvent(new SettlementNotiData(data.getRequestUser().getId(), data.getTargetUser().getId(), data.getUnsettledAmount())));
    }

    private SettlementResultData mapSettlementResultData(Settlement settlement) {
        return SettlementResultData.builder()
                .requestUserId(settlement.getRequestUser().getId())
                .totalAmount(settlement.getTotalAmount())
                .settledAmount(settlement.getSettledAmount())
                .settlementDetailList(settlement.getSettlementUserInfoList().stream().map(this::mapPaySettlementResultData).toList())
                .build();
    }


    private PaySettlementResultData mapPaySettlementResultData(SettlementUserInfo settlementUserInfo) {
        return PaySettlementResultData.builder()
                .settlementUserInfoId(settlementUserInfo.getId())
                .settlementId(settlementUserInfo.getSettlement().getId())
                .settlementStatus(settlementUserInfo.getStatus())
                .totalAmount(settlementUserInfo.getTotalAmount())
                .unsettledAmount(settlementUserInfo.getUnsettledAmount())
                .build();
    }
}
