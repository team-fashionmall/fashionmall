package com.fashionmall.item.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.entity.*;
import com.fashionmall.item.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j (topic = "itemService")
@Transactional (readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemColorRepository itemColorRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final CategoryRepository categoryRepository;

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

        // 색, 사이즈 저장
        ItemColor itemColor = ItemColor.builder()
                .color(itemRequestDto.getColor())
                .build();
        itemColorRepository.save(itemColor);
        log.info("색상 저장: {}", itemColor);

        ItemSize itemSize = ItemSize.builder()
                .size(itemRequestDto.getSize())
                .build();
        itemSizeRepository.save(itemSize);
        log.info("사이즈 저장: {}", itemSize);

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

        // 1. 카테고리 초기화
        if (categoryRepository.count() == 0 || !categoryRepository.existsByParentIsNull()) {
            initializeCategories();
            log.info("카테고리 초기화 완료");
        }
        // 2. parentId 검증
        Long parentId = itemRequestDto.getParentId();
        MainCategoryEnum mainCategory = itemRequestDto.getMainCategory();
        validateParentCategoryId(parentId, mainCategory);
        log.info("부모 카테고리 ID 검증 완료: {}", parentId);

        // 3. 카테고리 연결 검증
        isValidSubCategory(String.valueOf(itemRequestDto.getMainCategory()), itemRequestDto.getSubCategory());
        log.info("카테고리 연결 검증 완료");

        // 4. 카테고리 저장
        Category parentCategory = parentId != null ? categoryRepository.findById(parentId).orElse(null) : null;
        Category category = Category.builder()
                .mainCategory(itemRequestDto.getMainCategory())
                .subCategory(itemRequestDto.getSubCategory())
                .parent(parentCategory)
                .build();
        categoryRepository.save(category);
        log.info("카테고리 저장 완료: {}", category);

        return ItemResponseDto.from(item);
    }

    // 1.
    private void initializeCategories() {

        for (MainCategoryEnum mainCategory : MainCategoryEnum.values()) {
            Category parentCategory = Category.builder()
                    .mainCategory(mainCategory)
                    .subCategory(String.valueOf(mainCategory))
                    .parent(null) // 부모 ID를 Null로 설정
                    .build();
            categoryRepository.save(parentCategory);
        }
    }

    // 2.
    private void validateParentCategoryId(Long parentId, MainCategoryEnum mainCategoryEnum) {
        if (parentId != null) {

            // 요청된 부모 카테고리 ID가 실제로 존재하는지 확인
            Optional<Category> optionalParent = categoryRepository.findById(parentId);
            if (optionalParent.isEmpty()) {
                throw new CustomException(ErrorResponseCode.NOT_EXIST_PARENT_ID);
            }

            // 메인 카테고리의 올바른 부모인지 확인
            Category parentCategory = optionalParent.get();
            if (!parentCategory.getMainCategory().equals(mainCategoryEnum) || parentCategory.getParentId() != null) {
                throw new CustomException(ErrorResponseCode.WRONG_PARENT_ID);
            }

        }
    }

    // 3.
    public boolean isValidSubCategory(String mainCategory, String subCategory) {

        MainCategoryEnum mainCategoryEnum = MainCategoryEnum.valueOf(mainCategory);
        SubCategoryEnum subCategoryEnum = SubCategoryEnum.valueOf(subCategory);

        // 유효성 검사: 서브 카테고리가 올바른 메인 카테고리에 속하는지 확인
        if (subCategoryEnum.getMainCategoryEnum() != mainCategoryEnum) {
            throw new CustomException(ErrorResponseCode.BAD_CATEGORY);
        }
        return true;
    }

}
