package com.fashionmall.common.util;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.CustomResponseCode;

import static com.fashionmall.common.response.CustomResponseCode.*;

public class ApiResponseUtil {

    public static <T> CommonResponse<T> success(){
        return new CommonResponse<>(SUCCESS.getStatus().value(), SUCCESS.getMessage(), null);
    }

    public static <T>CommonResponse<T> success(T data){
        return new CommonResponse<>(SUCCESS.getStatus().value(), SUCCESS.getMessage(), data);
    }

    public static <T>CommonResponse<T> failure(CustomResponseCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.getMessage());
    }

    public static <T>CommonResponse<T> failure(Integer statusCode, String message) {
        return new CommonResponse<>(statusCode, message);
    }
}
