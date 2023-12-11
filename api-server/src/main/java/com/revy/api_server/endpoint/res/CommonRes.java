package com.revy.api_server.endpoint.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Revy on 2023.12.09
 */


@Getter
@NoArgsConstructor
public class CommonRes {
    @JsonProperty("message")
    private String message;

    public CommonRes(String message) {
        this.message = message;
    }
}
