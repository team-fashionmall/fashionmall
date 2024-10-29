package com.fashionmall.item.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.*;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;
import com.fashionmall.item.entity.*;
import com.fashionmall.item.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j (topic = "itemService")
@Transactional (readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemColorRepository itemColorRepository;
    private final ItemSizeRepository itemSizeRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final Category1Repository category1Repository;
    private final Category2Repository category2Repository;
    private final ItemCategoryMappingRepository itemCategoryMappingRepository;
    private final ItemDiscountRepository itemDiscountRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    public PageInfoResponseDto<ItemListResponseDto> getItemList(int pageNo, int size, String itemName, Long category1, Long category2) {
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.itemListPageNation (pageRequest, itemName, category1, category2);
    }

    @Override
    public PageInfoResponseDto<ItemDetailListResponseDto> getItemDetailList(Long itemId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.itemDetailListPageNation (itemId, pageRequest);
    }

    @Override
    @Transactional
    public ItemResponseDto createItem (ItemRequestDto itemRequestDto, Long workerId) {

        // 유저 검증 코드 (관리자) -> 추후 연결

        // 상품 등록
        Item item = Item.builder()
                .workerId(workerId)
                .imageId(0L)
                .imageUrl("무슨 내용이 들어갈까요?")
                .name(itemRequestDto.getName())
                .status(itemRequestDto.getState())
                .build();
        itemRepository.save(item);

//        Map<Long, String> mainImage = uploadImageApi(itemRequestDto.getImageFileName(), item.getId(), ReferencTypeEnum.ITEM, ImageTypeEnum.MAIN);
//        Long imageId = mainImage.keySet().iterator().next(); // 첫 번째 키 가져오기
//        String imageUrl = mainImage.get(imageId); // 키에 해당하는 값 가져오기
//        item.updateImageId(imageId);
        // 연결 후 위에껄로 수정 예정
        String imageUrl = "메인이다";
        item.updateImageId(2L);
        item.updateImageUrl(imageUrl);
        itemRepository.save(item);

        log.info("상품 등록: {}", item);

        // 카테고리 등록
        for (ItemRequestDto.CategoryRequestDto categoryRequestDto : itemRequestDto.getCategoryRequestDtoList()) {

            Category1 category1 = findCategory1(categoryRequestDto.getCategory1Id());
            Category2 category2 = findCategory2(categoryRequestDto.getCategory2Id(), categoryRequestDto.getCategory1Id());

            ItemCategoryMapping itemCategoryMapping = ItemCategoryMapping.builder()
                    .item(item)
                    .category1(category1)
                    .category2Id(category2.getId())
                    .build();
            itemCategoryMappingRepository.save(itemCategoryMapping);
            log.info("카테고리 저장 완료: {}", itemCategoryMapping);

        }

        // 상품 상세 등록
        for (ItemRequestDto.ItemDetailRequestDto itemDetailRequestDto : itemRequestDto.getItemDetailRequestDtoList()) {

            ItemSize itemSize = findSizeId(itemDetailRequestDto.getSizeId());
            ItemColor itemColor = findColorId(itemDetailRequestDto.getColorId());

            // 상품 상세 등록 저장
            ItemDetail itemDetail = ItemDetail.builder()
                    .item(item)
                    .itemColor(itemColor)
                    .itemSize(itemSize)
                    .imageId(0L)
                    .imageUrl("서브 이미지 url")
                    .name(itemDetailRequestDto.getName())
                    .price(itemDetailRequestDto.getPrice())
                    .quantity(itemDetailRequestDto.getQuantity())
                    .status(itemDetailRequestDto.getStatus())
                    .build();
            itemDetailRepository.save(itemDetail);

//            Map<Long, String> subImage = uploadImageApi(itemDetailRequestDto.getImageFileName(), itemDetail.getId(), ReferencTypeEnum.ITEM, ImageTypeEnum.DESCRIPTION);
//            Long subImageId = subImage.keySet().iterator().next(); // 첫 번째 키 가져오기
//            String subImageUrl = subImage.get(imageId); // 키에 해당하는 값 가져오기
//            itemDetail.updateImageId(subImageId);

            // msa 연결 예정
            itemDetail.updateImageId(5L);
            itemDetail.updateImageUrl("서브다");
            itemDetailRepository.save(itemDetail);


            item.getItemDetails().add(itemDetail);
            log.info("상품 상세 등록: {}", itemDetail);

        }

        return ItemResponseDto.from(item, imageUrl);
    }

    @Override
    public PageInfoResponseDto<AdminItemResponseDto> getAdminItemList(int pageNo, int size, String itemName, Long category1, Long category2) {
        // 유저 검증코드 추가
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.adminItemListPageNation (pageRequest, itemName, category1, category2);
    }

    @Override
    public PageInfoResponseDto<AdminItemDetailResponseDto> getAdminItemDetailList(Long itemId, int pageNo, int size) {
        // 유저 검증코드 추가
        PageRequest pageRequest = PageRequest.of(pageNo -1, size);
        return itemRepository.adminItemDetailListPageNation (itemId, pageRequest);
    }

    // 색 찾기
    private ItemColor findColorId (Long colorId) {
        return itemColorRepository.findById(colorId).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    // 사이즈 찾기
    private ItemSize findSizeId (Long sizeId) {
        return itemSizeRepository.findById(sizeId).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    // 카테고리 찾기
    private Category1 findCategory1 (Long category1) {
        return category1Repository.findById (category1).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_CATEGORY_ID));
    }

    private Category2 findCategory2 (Long category2, Long category1) {
        return category2Repository.findByIdAndCategory1Id (category2, category1).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_CATEGORY_ID));
    }

//    private Map<Long, String> uploadImageApi (String fileName, Long referenceId, ReferenceTypeEnum referenceType, ImageTypeEnum imageType) {
//
//        ImageUploadDto imageUploadDtoList = ImageUploadDto.builder ()
//                .fileName (fileName)
//                .referenceId (referenceId)
//                .referenceType (referenceType)
//                .imageType (imageType)
//                .build();
//
//        Map<Long,String> response = moduleApiUtil.uploadImageApi(imageUploadDtoList);
//
//        return response;
//    }

    @Override
    @Transactional
    public ItemDiscountResponseDto createItemDiscount (ItemDiscountRequestDto itemDiscountRequestDto, Long workerId) {

        // 본인 인증
        // 해당 아이템이 있는지 확인하기
        Item item = findByIdAndWorkerId(itemDiscountRequestDto.getId(), workerId);

        for (ItemDiscountRequestDto.ItemDiscountDtos itemDiscountDtos : itemDiscountRequestDto.getItemDiscountRequestDtoList()) {

            validateItemDiscountValue(itemDiscountDtos.getType(), itemDiscountDtos.getValue());

            ItemDiscount itemDiscount = ItemDiscount.builder()
                    .item(item)
                    .type(itemDiscountDtos.getType())
                    .value(itemDiscountDtos.getValue())
                    .status(itemDiscountDtos.getStatus())
                    .build();
            itemDiscountRepository.save(itemDiscount);
        }

        return ItemDiscountResponseDto.from(item);
    }

    @Override
    @Transactional
    public ItemUpdateResponseDto updateItem (Long itemId, ItemUpdateRequestDto itemUpdateRequestDto, Long workerId) {

        // 관리자 확인
        // 어떤 사람의 데이터를 수정해야하는걸까? & 해당 관리자인지 검증 -> 추후
        Item item = findByIdAndWorkerId(itemId, workerId);

        // 검증 및 데이터 수정
        List<ItemCategoryMapping> updatedCategoryMappings = new ArrayList<>();
        List<ItemDetail> updatedItemDetails = new ArrayList<>();
        List<ItemDiscount> updatedItemDiscounts = new ArrayList<>();

        // 검증 null "" // 데이터 수정하기
        if (!itemUpdateRequestDto.getName().isEmpty()) {
            item.updateItemName (itemUpdateRequestDto.getName());
        }

        item.updateItemState(itemUpdateRequestDto.getState());

        if (!itemUpdateRequestDto.getImageFileName().isEmpty()) {
//            moduleApiUtil.deleteImageApi(item.getImageId());
//
//            Map<Long,String> updateImage = moduleApiUtil.uploadImageApi(itemUpdateRequestDto.getImageFileName(), item.getImageId(), ReferencTypeEnum.ITEM, ImageTypeEnum.MAIN);
//            Long imageId = updateImage.keySet().iterator().next(); // 첫 번째 키 가져오기
//            String imageUrl = updateImage.get(imageId); // 키에 해당하는 값 가져오기
            Long imageId = 27L;
            String imageUrl = "수정된 메인 이미지 url";

            item.updateImageId(imageId);
            item.updateImageUrl(imageUrl);
        }

        // 카테고리
        for (ItemUpdateRequestDto.CategoryRequestDto categoryDto : itemUpdateRequestDto.getCategoryRequestDtoList()) {

            if (categoryDto.getId() != null) {
                ItemCategoryMapping itemCategoryMapping = itemCategoryMappingRepository.findByIdAndItemId(categoryDto.getId(), itemId)
                        .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

                if (categoryDto.getCategory1Id() != null) {
                    Category1 category1 = findCategory1(categoryDto.getCategory1Id());

                    if (categoryDto.getCategory2Id() != null) { // 둘 다 수정해야하는 경우
                        Category2 category2 = findCategory2(categoryDto.getCategory2Id(), categoryDto.getCategory1Id());
                        itemCategoryMapping.updateCategories(category1, category2.getId());
                    } else { // category 1만 수정하는 경우
                        itemCategoryMapping.updateCategory1(category1);
                    }
                }
                if (categoryDto.getCategory2Id() != null) {
                    Category2 category2 = findCategory2(categoryDto.getCategory2Id(), categoryDto.getCategory1Id());
                    itemCategoryMapping.updateCategory2(category2.getId());
                }
                updatedCategoryMappings.add(itemCategoryMapping);
            }
        }

        // Itemdetail
        for (ItemUpdateRequestDto.ItemDetailRequestDto itemDetailDto : itemUpdateRequestDto.getItemDetailRequestDtoList()) {

            if (itemDetailDto.getId() != null) {
                ItemDetail itemDetail = itemDetailRepository.findByIdAndItemId(itemDetailDto.getId(), itemId)
                        .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

                if (itemDetailDto.getName() != null) {
                    itemDetail.updateName(itemDetailDto.getName());
                }

                itemDetail.updateState(itemDetailDto.getStatus());

                if (!itemDetailDto.getImageFileName().isEmpty()) {
//                    moduleApiUtil.deleteImageApi(itemDetail.getImageId());
//
//                    Map<Long,String> updateImage = moduleApiUtil.uploadImageApi(itemDetailDto.getImageFileName(), itemDetail.getImageId(), ReferencTypeEnum.ITEM, ImageTypeEnum.DESCRIPTION);
//                    Long imageId = updateImage.keySet().iterator().next(); // 첫 번째 키 가져오기
//                    String imageUrl = updateImage.get(imageId); // 키에 해당하는 값 가져오기

                    Long imageId = 13L;
                    String imageUrl = "수정된 서브 이미지 url";

                    itemDetail.updateImageId(imageId);
                    itemDetail.updateImageUrl(imageUrl);
                }

                if (itemDetailDto.getPrice() > 0) {
                    itemDetail.updatePrice(itemDetailDto.getPrice());
                }

                if (itemDetailDto.getQuantity() > 0) {
                    itemDetail.updateQuantity(itemDetailDto.getQuantity());
                }

                if (itemDetailDto.getSizeId() != 0) {
                    ItemSize itemSize = findSizeId(itemDetailDto.getSizeId());
                    itemDetail.updateSize(itemSize);
                }

                if (itemDetailDto.getColorId() != 0) {
                    ItemColor itemColor = findColorId(itemDetailDto.getColorId());
                    itemDetail.updateColor(itemColor);
                }
                updatedItemDetails.add(itemDetail);
            }
        }

        // ItemDiscount
        for (ItemUpdateRequestDto.ItemDiscountRequestDto itemDiscountDto : itemUpdateRequestDto.getItemDiscountRequestDtoList()) {

            if (itemDiscountDto.getId() != null) {
                ItemDiscount itemDiscount = itemDiscountRepository.findByIdAndItemId(itemDiscountDto.getId(),itemId)
                        .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_DISCOUNT_ID));

                if (itemDiscountDto.getType() != null) {
                    itemDiscount.updateItemDiscountType(itemDiscountDto.getType());
                }

                if (itemDiscountDto.getValue() > 0) {
                    validateItemDiscountValue(itemDiscountDto.getType(), itemDiscountDto.getValue());
                    itemDiscount.updateValue(itemDiscountDto.getValue());
                }

                if (itemDiscountDto.getStatus() != null) {
                    itemDiscount.updateStatus(itemDiscountDto.getStatus());
                }
                updatedItemDiscounts.add(itemDiscount);
            }
        }

        return ItemUpdateResponseDto.from(item, updatedCategoryMappings, updatedItemDetails, updatedItemDiscounts);
    }

    @Override
    @Transactional
    public String deleteItem (Long itemId, Long workerId) {

        // 관리자 확인
        // 상품 아이디가 맞는지 확인 & 해당 관리자인지 확인 (추후)
        Item item = findByIdAndWorkerId(itemId,workerId);
//        moduleApiUtil.deleteImageApi(item.getImageId());
//        moduleApiUtil.deleteImageApi(item.getItemDetails().getImageId);
//
//        for (ItemDetail itemDetail : item.getItemDetails()) {
//            moduleApiUtil.deleteImageApi(itemDetail.getImageId());
//        }

        // 상품 삭제
        itemRepository.deleteById(itemId);

        return "상품 삭제가 완료되었습니다.";
    }

    private Item findByIdAndWorkerId (Long itemId, Long workerId) {
        return itemRepository.findByIdAndWorkerId(itemId, workerId).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));
    }

    private void validateItemDiscountValue (ItemDiscountTypeEnum type, int value) {

        if (type == ItemDiscountTypeEnum.RATE) {
            if (value < 0 || value > 100) {
                throw new CustomException(ErrorResponseCode.WRONG_RATE); // "정률(%)에 맞는 값을 입력해주세요."
            }
        } else if (type == ItemDiscountTypeEnum.AMOUNT) {
            if (value < 0) {
                throw new CustomException(ErrorResponseCode.WRONG_AMOUNT); // "정액(원)에 맞는 값을 입력해주세요."
            }
        }
    }

    @Override
    @Transactional
    public Map<Long, Integer> getItemStockApi (Long itemDetailId, Long workerId) {

        ItemDetail itemDetail = findByItemDetailIdAndWorkerId(itemDetailId, workerId);

        Map<Long, Integer> itemStock = new HashMap<>();
        itemStock.put(itemDetail.getId(), itemDetail.getQuantity());

        return itemStock;
    }

    @Override
    @Transactional
    public Map<Long, String> getItemDetailNameApi (List<Long> itemDetailIds) {

        Map<Long, String> itemDetailNames = new HashMap<>();

        for (Long itemDetailId : itemDetailIds) {

            ItemDetail itemDetail = itemDetailRepository.findById(itemDetailId)
                    .orElseThrow(()-> new CustomException(ErrorResponseCode.BAD_REQUEST)); // findNameById 메서드는 예시입니다.

            itemDetailNames.put(itemDetailId, itemDetail.getName());
        }

        return itemDetailNames;

    }

    @Override
    @Transactional
    public void deductItemQuantityApi (List<OrderItemDto> orderItemDto, Long workerId) {
        // 본인 확인

        for (OrderItemDto orderItemDtoList : orderItemDto) {
            ItemDetail itemDetail = findByItemDetailIdAndWorkerId(orderItemDtoList.getItemDetailId(), workerId);

            if (itemDetail.getQuantity() >= orderItemDtoList.getQuantity()) {
                itemDetail.deductQuantity(orderItemDtoList.getQuantity());
            } else {
                throw new CustomException(ErrorResponseCode.BAD_DEDUCT_QUANTITY);
            }
        }
    }

    @Override
    @Transactional
    public void restoreItemApi (List<OrderItemDto> orderItemDto, Long workerId){
        //본인확인

        for (OrderItemDto orderItemDtoList : orderItemDto) {

            ItemDetail itemDetail = findByItemDetailIdAndWorkerId(orderItemDtoList.getItemDetailId(), workerId);

            if (orderItemDtoList.getQuantity() > 0){
                itemDetail.restoreQuantity(orderItemDtoList.getQuantity());
            } else {
                throw new CustomException(ErrorResponseCode.BAD_RESTORE_QUANTITY);
            }
        }

    }

    @Override
    @Transactional
    public ItemDetailResponseDto getItemDetailApi (Long itemDetailId, Long workerId) {
        // 본인인증

        ItemDetail itemDetail = findByItemDetailIdAndWorkerId(itemDetailId, workerId);
        Item item = itemRepository.findByItemDetails_id(itemDetail.getId()).orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));

        return ItemDetailResponseDto.builder()
                .name(itemDetail.getName())
                .price(itemDetail.getPrice())
                .imageId(item.getImageId())
                .build();
    }

    private ItemDetail findByItemDetailIdAndWorkerId (Long itemDetailId, Long workerId) {
        return itemDetailRepository.findByIdAndItem_WorkerId(itemDetailId, workerId)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.WRONG_ITEM_DETAIL_ID));
    }

}
