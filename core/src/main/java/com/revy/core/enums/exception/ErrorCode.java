package com.revy.core.enums.exception;

import lombok.Getter;

/**
 * Created by Revy on 2023.12.09
 */
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 내부에 오류가 발생하였습니다."),
    BAD_REQUEST(400, "잘못된 요청 정보입니다."),
    ENTITY_NOT_EXISTS(404, "엔티티가 존재하지 않습니다."),
    USER_BALANCE_INSUFFICIENT(400, "잔고가 부족합니다."),
    AMOUNT_INCONSISTENCY(400, "요청금액 불일치")
    ;

    private final int code;
    private final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
