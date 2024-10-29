package com.fashionmall.item.repository;

import com.fashionmall.common.moduleApi.dto.ItemPriceNameDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    PageInfoResponseDto <ItemListResponseDto> itemListPageNation (Pageable pageable, String itemName, Long category1, Long category2);

    List<ItemDetailListResponseDto> itemDetailListPageNation (Long itemId);

    PageInfoResponseDto <AdminItemResponseDto> adminItemListPageNation (Pageable pageable, String itemName, Long category1, Long category2);

    PageInfoResponseDto <AdminItemDetailResponseDto> adminItemDetailListPageNation (Long itemId, Pageable pageable);

    ItemPriceNameDto getDiscountPrice (Long itemDetailId, Long itemDiscountId);
}
