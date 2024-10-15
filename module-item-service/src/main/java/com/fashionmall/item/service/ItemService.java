package com.fashionmall.item.service;

import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;

public interface ItemService {
    ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId);
    ItemDiscountResponseDto createItemDiscount (ItemDiscountRequestDto itemDiscountRequestDto, Long workerId);
    ItemUpdateResponseDto updateItem (Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId);
    String deleteItem (Long itemId, Long workerId);
}
