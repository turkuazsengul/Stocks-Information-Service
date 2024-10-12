package com.stockinformationapp.core.response;

import lombok.Getter;

@Getter
public enum ResponseMessageEnum {
    SUCCESS("Your transaction has been successfully completed."),
    UNSUCCESSFUL("Your transaction could not be completed successfully");

    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }
}
