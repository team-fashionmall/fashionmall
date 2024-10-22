package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private Long imageUrl;
}
