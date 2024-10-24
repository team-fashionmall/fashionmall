package com.fashionmall.item.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    PageInfoResponseDto <ItemListResponseDto> itemListPageNation (Pageable pageable, String itemName, Long category1, Long category2);

}
