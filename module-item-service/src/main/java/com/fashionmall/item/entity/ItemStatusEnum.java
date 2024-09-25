package com.fashionmall.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatusEnum {

    ACTIVATED ("활성화"), INACTIVATED ("비활성화");

    private final String status;

}
