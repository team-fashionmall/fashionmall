package com.fashionmall.common.moduleApi.dto;

import com.fashionmall.common.moduleApi.enums.ItemDiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailInfoDto {

    private String itemDetailName;
    private int price;
    private int itemDiscountValue;
    private ItemDiscountTypeEnum discountType;
    private String imageUrl;

}
