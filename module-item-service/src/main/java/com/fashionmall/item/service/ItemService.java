package com.fashionmall.item.service;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.*;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId);
    ItemDiscountResponseDto createItemDiscount (ItemDiscountRequestDto itemDiscountRequestDto, Long workerId);
    ItemUpdateResponseDto updateItem (Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId);
    String deleteItem (Long itemId, Long workerId);

    // gateway
    Map<Long, Integer> getItemStockApi (Long itemDetailId, Long workerId);

    String getItemNameApi (Long itemId);

    void deductItemStockApi (List<OrderItemDto> orderItemDto, Long workerId);

    void restoreItemApi (List<OrderItemDto> orderItemDto, Long workerId);

    ItemDetailResponseDto getItemDetailApi (Long itemDetailId, Long workerId);

    // 조회
    PageInfoResponseDto<ItemListResponseDto> getItemList(int pageNo, int size, String itemName, Long category1, Long category2);

    PageInfoResponseDto<ItemDetailListResponseDto> getItemDetailList(Long itemId, int pageNo, int size);

    PageInfoResponseDto<AdminItemResponseDto> getAdminItemList(int pageNo, int size, String itemName, Long category1, Long category2);

    PageInfoResponseDto<AdminItemDetailResponseDto> getAdminItemDetailList(Long itemId, int pageNo, int size);

}
