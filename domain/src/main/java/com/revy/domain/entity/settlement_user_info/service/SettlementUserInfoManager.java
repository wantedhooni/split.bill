package com.revy.domain.entity.settlement_user_info.service;

import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;
import com.revy.domain.entity.user.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.08
 */
public interface SettlementUserInfoManager {

    SettlementUserInfo createSettlementUserInfo(User requestUser, User orCreateNewUser, Settlement targetSettlement, BigDecimal amount);
    List<SettlementUserInfo> save(List<SettlementUserInfo> entities);
    SettlementUserInfo pay(SettlementUserInfo targetSettlementUserInfo, BigDecimal paidAmount);


}
