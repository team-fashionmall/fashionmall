package com.fashionmall.item.service;

import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;

public interface ItemService {
    ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId);
}
