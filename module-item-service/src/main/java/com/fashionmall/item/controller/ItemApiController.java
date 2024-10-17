package com.fashionmall.item.controller;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/item")
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping ("/ItemQuantityApi/{itemDetailId}")
    public CommonResponse <Integer> getItemQuantityApi (@PathVariable Long itemDetailId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(itemService.getItemQuantityApi(itemDetailId, workerId));
    }

    @PatchMapping ("/DeductItemApi")
    public void deductItemQuantityApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.deductItemQuantityApi(orderItemDto, workerId);
    }

    @PatchMapping ("/RestoreItemApi")
    public void restoreItemApi (@RequestBody List<OrderItemDto> orderItemDto) {
        Long workerId = 1L;
        itemService.restoreItemApi(orderItemDto, workerId);
    }
}
