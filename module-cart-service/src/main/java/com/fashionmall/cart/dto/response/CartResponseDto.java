package com.fashionmall.cart.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private Long id;
    private Long itemDetailId;

    private Long imageId;
    private String imageUrl;

    private int quantity;
    private int price;
    private boolean isSelected;

}
