package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import com.fashionmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j (topic = "itemQueryController")
@RestController
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemService itemService;

    @GetMapping ("/item")
    public CommonResponse<PageInfoResponseDto<ItemListResponseDto>> getItemList (@RequestParam(defaultValue = "1") int pageNo,
                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                 @RequestParam(required = false) String itemName,
                                                                                 @RequestParam(required = false) Long category1,
                                                                                 @RequestParam(required = false) Long category2) {
        return ApiResponseUtil.success(itemService.getItemList(pageNo, size, itemName, category1, category2));
    }

    @GetMapping("/item/{itemId}")
    public CommonResponse <PageInfoResponseDto<ItemDetailListResponseDto>> getItemDetailList (@PathVariable Long itemId,
                                                                                              @RequestParam(defaultValue = "1") int pageNo,
                                                                                              @RequestParam(defaultValue = "10") int size) {
        return ApiResponseUtil.success(itemService.getItemDetailList(itemId, pageNo, size));
    }

    @GetMapping ("/admin/item")
    public CommonResponse <PageInfoResponseDto<AdminItemResponseDto>> getAdminItemList (@RequestParam(defaultValue = "1") int pageNo,
                                                                                        @RequestParam(defaultValue = "10") int size,
                                                                                        @RequestParam(required = false) String itemName,
                                                                                        @RequestParam(required = false) Long category1,
                                                                                        @RequestParam(required = false) Long category2) {
        return ApiResponseUtil.success(itemService.getAdminItemList(pageNo, size, itemName, category1, category2));
    }
}
