package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.security.UserDetailsImpl;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.service.BillingKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingKeyController {

    private final BillingKeyService billingKeyService;

    @PostMapping("/billingKey")
    public CommonResponse<Long> issueBillingKey(@RequestBody BillingKeyRequestDto billingKeyRequestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        billingKeyRequestDto.setUserId(userDetails.getUserid());
        return ApiResponseUtil.success(billingKeyService.issueBillingKey(billingKeyRequestDto));
    }

    @GetMapping("/billingKey")
    public CommonResponse<List<UserBillingKeyResponseDto>> getBillingKey(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(billingKeyService.getUserBillingKeyList(userDetails.getUserid()));
    }

    @DeleteMapping("/billingKey/{billingKeyId}")
    public CommonResponse<Long> deleteBillingKey(@PathVariable(value = "billingKeyId") Long billingKeyId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(billingKeyService.deleteBillingKey(userDetails.getUserid(), billingKeyId));
    }
}
