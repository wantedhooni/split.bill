package com.revy.api_server.endpoint.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Revy on 2023.12.08
 */

@Getter
@NoArgsConstructor
public class CreateSettlementRes {

    @JsonProperty("message")
    private String message;

    public CreateSettlementRes(String message) {
        this.message = message;
    }
}
