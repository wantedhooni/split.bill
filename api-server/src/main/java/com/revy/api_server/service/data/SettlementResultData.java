package com.revy.api_server.service.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.09
 */

@Getter
@NoArgsConstructor
public class SettlementResultData {
    private Long requestUserId;
    private BigDecimal totalAmount;
    private BigDecimal settledAmount;
    private List<PaySettlementResultData> settlementDetailList;

    @Builder
    public SettlementResultData(Long requestUserId, BigDecimal totalAmount, BigDecimal settledAmount, List<PaySettlementResultData> settlementDetailList) {
        this.requestUserId = requestUserId;
        this.totalAmount = totalAmount;
        this.settledAmount = settledAmount;
        this.settlementDetailList = settlementDetailList;
    }
}
