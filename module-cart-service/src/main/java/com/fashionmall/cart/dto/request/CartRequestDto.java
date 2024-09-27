package com.fashionmall.cart.dto.request;

import com.fashionmall.cart.entity.CartStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    @NotBlank (message = "상품아이디를 입력해주세요")
    private Long itemId;

    @Positive (message = "해당 상품의 개수를 입력해주세요")
    private int cartQuantity;

    @NotBlank(message = "상품 선택 여부")
    private CartStatusEnum cartStatus;

    @Positive (message = "상품의 colorId를 입력해주세요")
    private String itemColor;

    @Positive(message = "상품의 sizeId를 입력해주세요")
    private String itemSize;

    private String itemName; // 추후 상품에서 받아올 예정
    private int itemPrice;
}
