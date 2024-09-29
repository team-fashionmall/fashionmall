package com.fashionmall.item.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.entity.*;
import com.fashionmall.item.enums.ItemStatusEnum;
import com.fashionmall.item.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j (topic = "itemService")
@Transactional (readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemColorRepository itemColorRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final CategoryMainRepository categoryMainRepository;
    private final CategorySubRepository categorySubRepository;
    private final ItemCategoryMappingRepository itemCategoryMappingRepository;

    @Override
    @Transactional
    public ItemResponseDto createItem (ItemRequestDto itemRequestDto) {

        // 유저 검증 코드 (관리자) -> 추후 연결

        // 상품 등록 & 저장
        Item item = Item.builder()
                .name(itemRequestDto.getItemName())
                .status(ItemStatusEnum.valueOf(itemRequestDto.getItemState()))
                .build();
        itemRepository.save(item);
        log.info("상품 등록: {}", item);

        // 색, 사이즈 검증
        ItemColor itemColor = matchColor(itemRequestDto.getColor());
        ItemSize itemSize = matchSize(itemRequestDto.getSize());

        // 상품 상세 등록 저장
        ItemDetail itemDetail = ItemDetail.builder()
                .item(item)
                .itemColor(itemColor)
                .itemSize(itemSize)
                .name(itemRequestDto.getItemDetailName())
                .price(itemRequestDto.getItemPrice())
                .quantity(itemRequestDto.getQuantity())
                .status(ItemStatusEnum.valueOf(itemRequestDto.getItemDetailState()))
                .build();
        itemDetailRepository.save(itemDetail);
        item.getItemDetails().add(itemDetail); // item 객체의 itemDetails 컬렉션에 itemDetail 객체 추가
        log.info("상품 상세 등록: {}", itemDetail);

        CategoryMain mainCategory = matchMainCategory(itemRequestDto.getMainCategory());
        CategorySub subCategory = matchSubCategory(itemRequestDto.getSubCategory());

        // 4. 카테고리 저장
        ItemCategoryMapping itemCategoryMapping = ItemCategoryMapping.builder()
                .item(item)
                .mainCategory(mainCategory)
                .subCategoryId(subCategory.getId())
                .build();
        itemCategoryMappingRepository.save(itemCategoryMapping);
        log.info("카테고리 저장 완료: {}", itemCategoryMapping);

        return ItemResponseDto.from(item);
    }

    // 색 찾기
    private ItemColor matchColor (String color) {
        return itemColorRepository.findByColor(color).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_STRING)); // "변수명을 다시 확인해주세요"
    }

    // 사이즈 찾기
    private ItemSize matchSize (String size) {
        return itemSizeRepository.findBySize(size).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_STRING)); // "변수명을 다시 확인해주세요"
    }

    // 카테고리 찾기
    private CategoryMain matchMainCategory (String mainCategory) {
        return categoryMainRepository.findByName (mainCategory).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_STRING));
    }

    private CategorySub matchSubCategory (String subCategory) {
        return categorySubRepository.findByName (subCategory).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_STRING));
    }

}
