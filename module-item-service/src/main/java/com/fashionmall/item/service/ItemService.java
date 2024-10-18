package com.fashionmall.item.service;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId);
    ItemDiscountResponseDto createItemDiscount (ItemDiscountRequestDto itemDiscountRequestDto, Long workerId);
    ItemUpdateResponseDto updateItem (Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId);
    String deleteItem (Long itemId, Long workerId);

    int getItemQuantityApi (Long itemDetailId, Long workerId);

    Map<Long, String> getItemDetailNameApi (List<Long> itemDetailIds);

    void deductItemQuantityApi (List<OrderItemDto> orderItemDto, Long workerId);

    void restoreItemApi (List<OrderItemDto> orderItemDto, Long workerId);

}
