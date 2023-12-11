package com.revy.domain.entity.settlement_user_info.service.impl;

import com.revy.core.enums.domain.SettlementStatus;
import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfoRepository;
import com.revy.domain.entity.settlement_user_info.service.SettlementUserInfoManager;
import com.revy.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.08
 */

@Service
@Transactional
@RequiredArgsConstructor
public class SettlementUserInfoManagerImpl implements SettlementUserInfoManager {

    private final SettlementUserInfoRepository repository;

    @Override
    public SettlementUserInfo createSettlementUserInfo(User requestUser, User targetUser, Settlement targetSettlement, BigDecimal amount) {
        Assert.notNull(requestUser, "requestUser must not be null.");
        Assert.notNull(targetUser, "targetUser must not be null.");
        Assert.notNull(targetSettlement, "targetSettlement must not be null.");
        Assert.notNull(amount, "amount must not be null.");
        return SettlementUserInfo.builder()
                .requestUser(requestUser)
                .targetUser(targetUser)
                .settlement(targetSettlement)
                .status(SettlementStatus.PREPARATION)
                .totalAmount(amount)
                .unsettledAmount(amount)
                .build();
    }

    @Override
    public List<SettlementUserInfo> save(List<SettlementUserInfo> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public SettlementUserInfo pay(SettlementUserInfo targetSettlementUserInfo, BigDecimal paidAmount) {
        targetSettlementUserInfo.pay(paidAmount);
        User requestUser = targetSettlementUserInfo.getRequestUser();
        User targetUser = targetSettlementUserInfo.getTargetUser();
        if (requestUser != targetUser) {
            targetSettlementUserInfo.getTargetUser().subtractBalance(paidAmount);
            targetSettlementUserInfo.getRequestUser().addBalance(paidAmount);
        }
        return targetSettlementUserInfo;
    }
}
