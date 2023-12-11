package com.revy.api_server.service.data;

import com.revy.core.enums.domain.SettlementStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.09
 */

@Getter
@NoArgsConstructor
public class PaySettlementResultData {
    private Long settlementUserInfoId;
    private Long settlementId;
    private SettlementStatus settlementStatus;
    private BigDecimal totalAmount;
    private BigDecimal unsettledAmount;

    @Builder
    public PaySettlementResultData(Long settlementUserInfoId, Long settlementId, SettlementStatus settlementStatus, BigDecimal totalAmount, BigDecimal unsettledAmount) {
        this.settlementUserInfoId = settlementUserInfoId;
        this.settlementId = settlementId;
        this.settlementStatus = settlementStatus;
        this.totalAmount = totalAmount;
        this.unsettledAmount = unsettledAmount;
    }
}
