package com.fashionmall.item.dto.response;

import com.fashionmall.item.entity.Category1;
import com.fashionmall.item.enums.ItemDiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailListResponseDto {

    private ItemInfo itemInfo;
    private ItemDetailInfo itemDetailInfo;
    private ItemDiscountInfo itemDiscountInfo;
    private ItemCategoryInfo itemCategoryInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemInfo {

        private long id;
        private String name;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailInfo {

        private String color;
        private String size;
        private String name; // 상품 상세명
        private int price;
        private int discountPrice;
        private int quantity; // 재고 현황
        private long imageId;
        private String imageUrl;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountInfo {

        private ItemDiscountTypeEnum type;
        private int value;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCategoryInfo {

        private long category1;
        private long category2;
    }

    //찜개수

}