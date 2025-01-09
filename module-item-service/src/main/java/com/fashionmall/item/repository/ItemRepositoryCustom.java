package com.fashionmall.item.repository;

import com.fashionmall.common.moduleApi.dto.ItemPriceNameDto;
import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.CategoryResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    LikeItemListResponseDto getItemInfo(Long itemId);

    PageInfoResponseDto<ItemListResponseDto> itemListPageNation(Pageable pageable, String itemName, Long category1, Long category2);

    PageInfoResponseDto<AdminItemResponseDto> adminItemListPageNation(Pageable pageable, String itemName, Long category1, Long category2);

    PageInfoResponseDto<AdminItemDetailResponseDto> adminItemDetailListPageNation(Long itemId, Pageable pageable);

    ItemPriceNameDto getDiscountPrice(Long itemDetailId, Long itemDiscountId);

    List<CategoryResponseDto> getCategoryList();
}
