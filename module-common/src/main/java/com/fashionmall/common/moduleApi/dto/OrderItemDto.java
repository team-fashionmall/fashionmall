package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long itemDetailId;
    private int quantity;

}