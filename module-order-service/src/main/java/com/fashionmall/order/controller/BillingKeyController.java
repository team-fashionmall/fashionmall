package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.service.IamPortService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BillingKeyController {

    private final IamPortService iamPortService;

    @PostMapping("/billingKey")
    public CommonResponse<Long> issueBillingKey(@RequestBody BillingKeyRequestDto billingKeyRequestDto) {

        return ApiResponseUtil.success(iamPortService.issueBillingKey(billingKeyRequestDto));
    }

    @GetMapping("/billingKey")
    public CommonResponse<PageInfoResponseDto<UserBillingKeyResponseDto>> getBillingKey(@RequestParam(defaultValue = "1") int pageNo,
                                                                                        @RequestParam(defaultValue = "10") int size) {
        Long userId = 1L;
        return ApiResponseUtil.success(iamPortService.getUserBillingKeyList(userId, pageNo, size));
    }

    @DeleteMapping("/billingKey/{billingKeyId}")
    public CommonResponse<Long> deleteBillingKey(@PathVariable(value = "billingKeyId") Long billingKeyId) {
        return ApiResponseUtil.success(iamPortService.deleteBillingKey(billingKeyId));
    }
}
