package com.fashionmall.item.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.item.dto.request.ItemRequestDto;
import com.fashionmall.item.dto.response.ItemResponseDto;
import com.fashionmall.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
