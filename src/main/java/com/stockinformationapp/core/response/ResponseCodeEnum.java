package com.stockinformationapp.core.response;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    SUCCESS(1111),
    UNSUCCESSFUL(9999);

    private final int code;

    ResponseCodeEnum(int code) {
        this.code = code;
    }
}
