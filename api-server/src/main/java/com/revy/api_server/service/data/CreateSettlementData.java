package com.revy.api_server.service.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.09
 */

@Getter
@NoArgsConstructor
public class CreateSettlementData {
    private Long requestUserId;
    private BigDecimal totalAmount;
    private List<CreateSettlementDetailData> settlementList;
    private String kakaoRoomId;

    @Builder
    public CreateSettlementData(Long requestUserId, BigDecimal totalAmount, List<CreateSettlementDetailData> settlementList, String kakaoRoomId) {
        this.requestUserId = requestUserId;
        this.totalAmount = totalAmount;
        this.settlementList = settlementList;
        this.kakaoRoomId = kakaoRoomId;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class CreateSettlementDetailData {
        private Long userId;
        private BigDecimal amount;

        public CreateSettlementDetailData(Long userId, BigDecimal amount) {
            this.userId = userId;
            this.amount = amount;
        }
    }
}
