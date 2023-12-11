package com.revy.api_server.service;

import com.revy.api_server.service.data.CreateSettlementData;
import com.revy.api_server.service.data.SettlementResultData;
import com.revy.api_server.service.data.PaySettlementResultData;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.08
 */
public interface SettlementService {

    /**
     * 정산하기를 생성한다.
     * @param createSettlementData
     */
    SettlementResultData createSettlement(CreateSettlementData createSettlementData);

    /**
     * 정산하기
     * 요청한 요청자에게 금액을 송금 한다.
     * @param userId 송금 유저
     * @param settlementId 정산 객체 ID
     * @param paidAmount 송금 금액
     * @return PaySettlementResultData
     */
    PaySettlementResultData paySettlement(Long userId, Long settlementId, BigDecimal paidAmount);


    /**
     * 해당 정산 객체의 미완료건들에 대해서 리마인드를 요청한다.
     * @param requestUserId
     * @param settlementId
     */
    void remind(Long requestUserId, Long settlementId);

    /**
     * 정산 요청한 목록을 반환한다.
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<SettlementResultData> getSettlementRequestList(Long userId, int pageNumber, int pageSize);

    /**
     * 정산 요청 받은 목록을 반환한다.
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<PaySettlementResultData> getSettlementRequestedList(Long userId, int pageNumber, int pageSize);
}
