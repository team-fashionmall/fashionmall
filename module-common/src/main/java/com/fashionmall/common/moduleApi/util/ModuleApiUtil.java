package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ModuleApiUtil {

    private final WebClientUtil webClientUtil;

    private final String itemApi = "http://localhost:8000/api/item";

    public int getItemQuantityApi(Long itemDetailId) {

        CommonResponse<Integer> integerCommonResponse = webClientUtil.get(
                itemApi + "/ItemQuantityApi/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<Integer>>() {},
                null,
                headers()
        );

        return integerCommonResponse.getData();
    }

    public void deductItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/DeductItemApi",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {},
                headers());
    }

    public void restoreItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/RestoreItemApi",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {},
                headers());
    }

    private Map<String, String> headers (){
        return Map.of (HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
