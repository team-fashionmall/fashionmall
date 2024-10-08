package com.fashionmall.order.infra.iamPort.util;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.WebClientUtil;
import com.fashionmall.order.dto.response.BillingKeyResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.dto.TokenRequestDto;
import com.fashionmall.order.infra.iamPort.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IamPortClient {

    private final WebClientUtil webClientUtil;

    @Value("${portOne.api.key}")
    private String key;

    @Value("${portOne.api.secret}")
    private String secret;

    private final String iamPortUrl = "http://api.iamport.kr";

    public String getAccessToken() {
        TokenRequestDto requestDto = new TokenRequestDto();
        requestDto.setImpKey(key);
        requestDto.setImpSecret(secret);

        Map<String, String> headers = Map.of(HttpHeaders.CONTENT_TYPE, "application/json");

        IamPortResponseDto<TokenResponseDto> post = webClientUtil.post(iamPortUrl + "/users/getToken", requestDto, new ParameterizedTypeReference<IamPortResponseDto<TokenResponseDto>>() {
        }, headers);
        return post.getResponse().getAccessToken();
    }

    public IamPortResponseDto<BillingKeyResponseDto> getBillingKey(String customerUid, Map<String, String> headers, Map<String, String> request) {
        return webClientUtil.post(
                iamPortUrl + "/subscribe/customers/" + customerUid,
                request,
                new ParameterizedTypeReference<IamPortResponseDto<BillingKeyResponseDto>>() {
                },
                headers);
    }

    public PageInfoResponseDto<UserBillingKeyResponseDto> getUserBillingKey(Map<String, String> headers, HashMap<String, String> queryParam) {
        return webClientUtil.get(iamPortUrl + "/subscribe/customers",
                new ParameterizedTypeReference<PageInfoResponseDto<UserBillingKeyResponseDto>>() {
                },
                queryParam,
                headers);
    }

    public void deleteBillingKey(String customerUid, Map<String, String> headers) {
        webClientUtil.delete(iamPortUrl + "/subscribe/customers/" + customerUid,
                new ParameterizedTypeReference<IamPortResponseDto<UserBillingKeyResponseDto>>() {
                },
                headers);
    }
}
