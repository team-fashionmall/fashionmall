package com.fashionmall.common.exception;

import com.fashionmall.common.response.CustomResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final CustomResponseCode code;

    public CustomException(CustomResponseCode code) {
        this.code = code;
    }
}
