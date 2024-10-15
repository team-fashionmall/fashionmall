package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.dto.request.ItemDiscountRequestDto;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemDiscountResponseDto;
import com.fashionmall.item.dto.request.ItemUpdateRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.dto.response.ItemUpdateResponseDto;
import com.fashionmall.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "item_admin_controller")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ItemAdminController {

    private final ItemService itemService;

    @PostMapping("/item")
    public CommonResponse<ItemResponseDto> createItem (@Valid @RequestBody ItemRequestDto itemRequestDto) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.createItem(itemRequestDto, workerId));
    }

    @PostMapping ("/itemDiscount")
    public CommonResponse<ItemDiscountResponseDto> createItemDiscount (@Valid @RequestBody ItemDiscountRequestDto itemDiscountRequestDto) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.createItemDiscount(itemDiscountRequestDto, workerId));
    }

    @PatchMapping("/item/{itemId}")
    public CommonResponse <ItemUpdateResponseDto> updateItem (@PathVariable Long itemId, @Valid @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.updateItem(itemId, itemUpdateRequestDto, workerId));
    }

}
