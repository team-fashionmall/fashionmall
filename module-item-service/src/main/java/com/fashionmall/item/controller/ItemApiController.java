package com.fashionmall.item.controller;

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
        return ApiResponseUtil.success(itemService.getItemQuantityApi(itemDetailId));
    }

}
