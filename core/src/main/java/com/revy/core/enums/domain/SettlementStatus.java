package com.revy.core.enums.domain;

import lombok.Getter;

/**
 * Created by Revy on 2023.12.08
 */

@Getter
public enum SettlementStatus {
    PREPARATION("준비"),
    PROCEEDING("진행 중"),
    COMPLETE("완료"),
    ;

    private final String description;

    SettlementStatus(String description) {
        this.description = description;
    }
}
