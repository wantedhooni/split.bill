package com.revy.core.exception.common;

import com.revy.core.enums.exception.ErrorCode;
import lombok.Getter;

/**
 * Created by Revy on 2023.11.13
 * Common Exception
 */

@Getter
public class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
