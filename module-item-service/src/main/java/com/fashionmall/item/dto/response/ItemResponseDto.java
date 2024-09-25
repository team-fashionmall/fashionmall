package com.fashionmall.item.dto.response;

import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

    private long id;
    private String itemName;
    private String itemState; // 상품 전시 상태

    private String color;
    private String size;

    private String itemDetailName; // 상품 상세명
    private int itemPrice;
    private int quantity; // 재고 현황
    private String itemDetailState; // 상품 전시 상태

    /*// presigned url 후 연결
    private String mainImage;

    private String detailImage;*/

    public static ItemResponseDto from (Item item) {

        List<ItemDetail> itemDetails = item.getItemDetails();

        // 가장 최근의 ItemDetail 선택
        ItemDetail selectedDetail = itemDetails.stream()
                .max(Comparator.comparing(ItemDetail::getCreatedAt)) // createdAt으로 정렬
                .orElse(null);

        return ItemResponseDto.builder()
                .id (item.getId())
                .itemName (item.getName())
                .itemState (item.getStatus().name())
                .color(selectedDetail != null ? selectedDetail.getItemColor().getColor() : null)
                .size(selectedDetail != null ? selectedDetail.getItemSize().getSize() : null)
                .itemDetailName(selectedDetail != null ? selectedDetail.getName() : null)
                .itemPrice(selectedDetail != null ? selectedDetail.getPrice() : 0)
                .quantity(selectedDetail != null ? selectedDetail.getQuantity() : 0)
                .itemDetailState(selectedDetail != null ? selectedDetail.getStatus().name() : null)
                .build();
    }
}
