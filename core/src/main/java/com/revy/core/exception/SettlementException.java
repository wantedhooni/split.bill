package com.revy.core.exception;

import com.revy.core.enums.exception.ErrorCode;
import com.revy.core.exception.common.CommonException;

/**
 * Created by Revy on 2023.12.08
 */
public class SettlementException extends CommonException {
    public SettlementException(ErrorCode errorCode) {
        super(errorCode);
    }
}
