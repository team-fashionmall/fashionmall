package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ModuleApiUtil {

    private final WebClientUtil webClientUtil;

    private final String cartApi = "http://localhost:8000/api/cart";
    private final String itemApi = "http://localhost:8000/api/item";

    public ItemDetailResponseDto getItemDetail (Long itemDetailId) {

        CommonResponse<ItemDetailResponseDto> commonResponse = webClientUtil.get(
                itemApi + "/itemDetail/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<ItemDetailResponseDto>>() {},
                null,
                headers()
        );

        return commonResponse.getData();
    }

    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {

        CommonResponse<List<ItemDetailDto>> listCommonResponse = webClientUtil.get(
                cartApi + "/ItemDetailApi/" + userId,
                new ParameterizedTypeReference<CommonResponse<List<ItemDetailDto>>>() {},
                null,
                headers()
        );

        return listCommonResponse.getData();
    }

    private Map<String, String> headers (){
        return Map.of (HttpHeaders.CONTENT_TYPE, "application/json");
    }

}
