package com.fashionmall.order.dto.response.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IamPortResponseDto<T> {

    private Integer code;
    private String message;
    private T response;

}
