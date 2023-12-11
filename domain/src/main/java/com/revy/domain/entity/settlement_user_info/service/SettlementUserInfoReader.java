package com.revy.domain.entity.settlement_user_info.service;

import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;

import java.util.List;
import java.util.Optional;

/**
 * Created by Revy on 2023.12.08
 */
public interface SettlementUserInfoReader {
    Optional<SettlementUserInfo> findOneByUserIdAndSettlementId(Long userId, Long settlementId);
    List<SettlementUserInfo> findBySettlementId(Long settlementId);
    List<SettlementUserInfo> findByTargetUserId(Long targetUserId, int pageNumber, int pageSize);
}
