package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.service.BillingKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BillingKeyController {

    private final BillingKeyService billingKeyService;

    @PostMapping("/billingKey")
    public CommonResponse<Long> issueBillingKey(@RequestBody BillingKeyRequestDto billingKeyRequestDto) {

        return ApiResponseUtil.success(billingKeyService.issueBillingKey(billingKeyRequestDto));
    }

    @GetMapping("/billingKey")
    public CommonResponse<PageInfoResponseDto<UserBillingKeyResponseDto>> getBillingKey(@RequestParam(defaultValue = "1") int pageNo,
                                                                                        @RequestParam(defaultValue = "10") int size) {
        Long userId = 1L;
        return ApiResponseUtil.success(billingKeyService.getUserBillingKeyList(userId, pageNo, size));
    }

    @DeleteMapping("/billingKey/{billingKeyId}")
    public CommonResponse<Long> deleteBillingKey(@PathVariable(value = "billingKeyId") Long billingKeyId) {
        return ApiResponseUtil.success(billingKeyService.deleteBillingKey(billingKeyId));
    }
}
