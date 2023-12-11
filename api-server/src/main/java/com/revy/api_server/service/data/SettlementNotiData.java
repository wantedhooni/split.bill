package com.revy.api_server.service.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.08
 */

@Getter
@NoArgsConstructor
public class SettlementNotiData {

    private Long requestUserId;
    private Long targetUserId;
    private BigDecimal unsettledAmount;

    public SettlementNotiData(Long requestUserId, Long targetUserId, BigDecimal unsettledAmount) {
        this.requestUserId = requestUserId;
        this.targetUserId = targetUserId;
        this.unsettledAmount = unsettledAmount;
    }

    public String getMessage(){
        return "%s 님께서 %s님에게 %s 정산을 요청하셨습니다.".formatted(requestUserId, targetUserId, unsettledAmount);
    }



}
