package com.fashionmall.item.dto.response;

import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDiscount;
import com.fashionmall.item.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDiscountResponseDto {

    private Long id;
    private List<ItemDiscountDtos> itemDiscountDtoList;

    public static ItemDiscountResponseDto from (Item item) {
        List <ItemDiscountDtos> itemDiscountResponseDtoList = item.getItemDiscounts().stream()
                .map(ItemDiscountDtos::from)
                .toList();

        return ItemDiscountResponseDto.builder()
                .id(item.getId())
                .itemDiscountDtoList(itemDiscountResponseDtoList)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDiscountDtos {

        private Long id;
        private ItemDiscountTypeEnum type;
        private int value;
        private StatusEnum status;

        public static ItemDiscountDtos from (ItemDiscount itemDiscount) {
            return ItemDiscountDtos.builder()
                    .id(itemDiscount.getId())
                    .type(itemDiscount.getType())
                    .value(itemDiscount.getValue())
                    .status(itemDiscount.getStatus())
                    .build();
        }
    }
}
