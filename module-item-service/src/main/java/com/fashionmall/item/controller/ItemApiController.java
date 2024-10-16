package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping ("/itemDetail/{itemDetailId}")
    public CommonResponse <ItemDetailResponseDto> getItemDetail (@PathVariable Long itemDetailId) {
        return ApiResponseUtil.success(itemService.getItemDetail(itemDetailId));
    }

}
