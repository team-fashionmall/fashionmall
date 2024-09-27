package com.fashionmall.cart.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartStatusEnum {
    ACTIVATED ("활성화"), INACTIVATED ("비활성화");
    private final String status;
}