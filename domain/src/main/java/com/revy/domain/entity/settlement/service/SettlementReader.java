package com.revy.domain.entity.settlement.service;

import com.revy.domain.entity.settlement.Settlement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by Revy on 2023.12.08
 */
public interface SettlementReader {
    Optional<Settlement> findByIdAndRequestUserId(Long settlementId, Long requestUserId);

    List<Settlement> findByUserId(Long userId, int pageNumber, int pageSize);
}
