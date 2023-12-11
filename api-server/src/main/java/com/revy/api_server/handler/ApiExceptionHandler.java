package com.revy.api_server.handler;

import com.revy.api_server.handler.res.ErrorResponse;
import com.revy.core.enums.exception.ErrorCode;
import com.revy.core.exception.common.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * Created by Revy on 2023.12.05
 * Api Exception Handler
 */

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBindException(BindingResult bindingResult) {
        return ErrorResponse.of(
                ErrorCode.BAD_REQUEST.getCode(),
                Objects.requireNonNullElse(bindingResult.getFieldError().getDefaultMessage(), ErrorCode.BAD_REQUEST.getDescription())
        );
    }


    @ExceptionHandler({CommonException.class})
    protected ResponseEntity<ErrorResponse> handleBindException(CommonException commonException) {
        ErrorResponse body = ErrorResponse.of(commonException.getErrorCode());
        return ResponseEntity.status(body.getCode()).body(body);
    }


}
