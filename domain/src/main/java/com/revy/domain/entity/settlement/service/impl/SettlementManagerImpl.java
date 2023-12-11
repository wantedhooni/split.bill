package com.revy.domain.entity.settlement.service.impl;

import com.revy.core.enums.domain.SettlementStatus;
import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.settlement.SettlementRepository;
import com.revy.domain.entity.settlement.service.SettlementManager;
import com.revy.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.08
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SettlementManagerImpl implements SettlementManager {

    private final SettlementRepository repository;

    @Override
    public Settlement createSettlement(User requestUser, BigDecimal totalAmount, String kakaoRoomId) {
        return repository.save(
                Settlement.builder()
                        .requestUser(requestUser)
                        .totalAmount(totalAmount)
                        .settledAmount(BigDecimal.ZERO)
                        .status(SettlementStatus.PREPARATION)
                        .kakaoRoomId(kakaoRoomId)
                        .build()
        );

    }

    @Override
    public Settlement pay(Settlement settlement, BigDecimal paidAmount) {
        settlement.pay(paidAmount);
        return settlement;
    }
}
