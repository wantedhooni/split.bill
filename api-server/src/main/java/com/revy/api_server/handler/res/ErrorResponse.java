package com.revy.api_server.handler.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revy.core.enums.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Revy on 2023.11.14
 * Error Response
 */

@Getter
@NoArgsConstructor
public class ErrorResponse {
    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getDescription());
    }

}
