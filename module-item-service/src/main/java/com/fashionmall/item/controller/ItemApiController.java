package com.fashionmall.item.controller;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/item")
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping("/itemDetail/{itemDetailId}")
    public CommonResponse <ItemDetailResponseDto> getItemDetailApi (@PathVariable Long itemDetailId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.getItemDetailApi(itemDetailId, workerId));
    }

    @GetMapping ("/getStock/{itemDetailId}")
    public CommonResponse <Map<Long, Integer>> getItemStockApi (@PathVariable Long itemDetailId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.getItemStockApi(itemDetailId, workerId));
    }

    @GetMapping ("/getItemName/{itemId}")
    public CommonResponse<String> getItemNameApi (@PathVariable Long itemId) {
        return ApiResponseUtil.success(itemService.getItemNameApi(itemId));
    }

    @GetMapping ("/getItemDetail/{itemDetailId}")
    public CommonResponse<List<ItemDetailInfoDto>> getItemDetailInfoApi (@PathVariable List<Long> itemDetailId) {
        return ApiResponseUtil.success(itemService.getItemDetailInfoApi(itemDetailId));
    }

    @PatchMapping ("/deductItem")
    public void deductItemStockApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.deductItemStockApi(orderItemDto, workerId);
    }

    @PatchMapping ("/restoreItem")
    public void restoreItemStockApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.restoreItemStockApi(orderItemDto, workerId);
    }

    // 카트
    @GetMapping("/itemPriceAndName")
    public CommonResponse<List<ItemPriceNameDto>> getItemPriceAneNameApi (@RequestParam List<Long> itemDetailId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.getItemPriceAndNameApi(itemDetailId, workerId));
    }
}
