package com.fashionmall.common.util;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.exception.ErrorResponseCode;

import static com.fashionmall.common.exception.ErrorResponseCode.*;

public class ApiResponseUtil {

    public static <T> CommonResponse<T> success(){
        return new CommonResponse<>(SUCCESS.getStatus().value(), SUCCESS.getMessage(), null);
    }

    public static <T>CommonResponse<T> success(T data){
        return new CommonResponse<>(SUCCESS.getStatus().value(), SUCCESS.getMessage(), data);
    }

    public static <T>CommonResponse<T> failure(ErrorResponseCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.getMessage());
    }

    public static <T>CommonResponse<T> failure(Integer statusCode, String message) {
        return new CommonResponse<>(statusCode, message);
    }
}
