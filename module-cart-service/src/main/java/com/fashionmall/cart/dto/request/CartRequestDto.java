package com.fashionmall.cart.dto.request;

import com.fashionmall.cart.entity.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {

    @NotNull (message = "상품아이디를 입력해주세요")
    private Long itemDetailId;

    @NotNull @Positive (message = "해당 상품의 개수를 입력해주세요")
    private int quantity;

    @JsonProperty("is_selected")
    @NotNull (message = "장바구니 선택 여부를 입력해주세요")
    private boolean isSelected;

    public Cart toEntity(Long userId) {
        return Cart.builder()
                .userId(userId)
                .itemDetailId(this.itemDetailId)
                .quantity(this.quantity)
                .isSelected(this.isSelected)
                .build();
    }

}
