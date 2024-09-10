package com.fashionmall.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorResponseCode code;

    public CustomException(ErrorResponseCode code) {
        this.code = code;
    }
}
