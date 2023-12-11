package com.revy.api_server.endpoint.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.09
 */

@Getter
@NoArgsConstructor
public class PaySettlementReq {
    @NotNull
    @JsonProperty("settlementId")
    private Long settlementId;

    @NotNull
    @JsonProperty("paidAmount")
    private BigDecimal paidAmount;
}
