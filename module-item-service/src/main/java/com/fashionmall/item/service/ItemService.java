package com.fashionmall.item.service;

import com.fashionmall.common.moduleApi.dto.*;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.*;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemResponseDto createItem(ItemRequestDto itemRequestDto, Long workerId);

    ItemDiscountResponseDto createItemDiscount(ItemDiscountRequestDto itemDiscountRequestDto, Long workerId);

    ItemUpdateResponseDto updateItem(Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId);

    Long deleteItem(Long itemId, Long workerId);

    // gateway
    Map<Long, Integer> getItemStockApi(List<Long> itemDetailId, Long workerId);

    String getItemNameApi(Long itemId);

    List<ItemDetailInfoDto> getItemDetailInfoApi(List<Long> itemDetailId);

    void deductItemStockApi(List<OrderItemDto> orderItemDto, Long workerId);

    void restoreItemStockApi(List<OrderItemDto> orderItemDto, Long workerId);

    ItemDetailResponseDto getItemDetailApi(Long itemDetailId, Long workerId);

    List<ItemPriceNameDto> getItemPriceAndNameApi(List<Long> itemDetailId, Long workerId);

    List<LikeItemListResponseDto> getItemInfoApi(Long itemId, Long userId);

    // 조회
    PageInfoResponseDto<ItemListResponseDto> getItemList(int pageNo, int size, String itemName, Long category1, Long category2);

    List<ItemDetailListResponseDto> getItemDetailList(Long itemId);

    PageInfoResponseDto<AdminItemResponseDto> getAdminItemList(int pageNo, int size, String itemName, Long category1, Long category2, Long workerId);

    PageInfoResponseDto<AdminItemDetailResponseDto> getAdminItemDetailList(Long itemId, int pageNo, int size, Long workerId);

    List<CategoryResponseDto> getCategoryList();

}
