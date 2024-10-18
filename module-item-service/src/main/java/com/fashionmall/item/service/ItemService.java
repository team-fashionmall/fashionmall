package com.fashionmall.item.service;

import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.item.dto.response.ItemResponseDto;

public interface ItemService {
    ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId);

    ItemDiscountResponseDto createItemDiscount (ItemDiscountRequestDto itemDiscountRequestDto, Long workerId);

    ItemDetailResponseDto getItemDetail (Long itemDetailId, Long workerId);
}
