package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<Long, String> getItemDetailNameApi(List<Long> itemDetailIds) {

        String ids = itemDetailIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        CommonResponse<Map<Long, String>> mapCommonResponse = webClientUtil.get(
                itemApi + "/ItemDetailNameApi?itemDetailIds=" + ids,
                new ParameterizedTypeReference<CommonResponse<Map<Long, String>>>() {},
                null,
                headers()
        );

        return mapCommonResponse.getData();
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
