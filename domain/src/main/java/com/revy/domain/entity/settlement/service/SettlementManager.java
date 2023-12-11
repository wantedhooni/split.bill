package com.revy.domain.entity.settlement.service;

import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.user.User;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.08
 */
public interface SettlementManager {
    Settlement createSettlement(User requestUser, BigDecimal totalAmount, String kakaoRoomId);

    Settlement pay(Settlement settlement, BigDecimal paidAmount);


}
