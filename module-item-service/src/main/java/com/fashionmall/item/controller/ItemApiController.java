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

    @GetMapping ("/{itemDetailId}/quantity")
    public CommonResponse <Integer> getItemQuantityApi (@PathVariable Long itemDetailId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.getItemQuantityApi(itemDetailId, workerId));
    }

    @GetMapping ("/itemDetailName")
    public CommonResponse <Map<Long, String>> getItemDetailNameApi (@RequestParam List<Long> itemDetailIds) {
        return ApiResponseUtil.success(itemService.getItemDetailNameApi(itemDetailIds));
    }

    @PatchMapping ("/deductItem")
    public void deductItemQuantityApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.deductItemQuantityApi(orderItemDto, workerId);
    }

    @PatchMapping ("/restoreItem")
    public void restoreItemApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.restoreItemApi(orderItemDto, workerId);
    }
}
