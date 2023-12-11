package com.revy.api_server.endpoint.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Revy on 2023.12.08
 */

@Getter
@ToString
@NoArgsConstructor
public class CreateSettlementReq {

    @NotNull
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @NotNull
    @JsonProperty("settlementList")
    private List<CreateSettlementDetail> settlementList;

    @Getter
    @ToString
    @NoArgsConstructor
    public static class CreateSettlementDetail {
        @NotNull
        @JsonProperty("userId")
        private Long userId;

        @NotNull
        @JsonProperty("amount")
        private BigDecimal amount;
    }
}


