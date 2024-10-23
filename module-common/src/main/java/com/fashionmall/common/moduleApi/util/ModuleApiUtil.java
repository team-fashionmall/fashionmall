package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ModuleApiUtil {

    private final WebClientUtil webClientUtil;

    private final String cartApi = "http://localhost:8000/api/cart";
    private final String itemApi = "http://localhost:8000/api/item";
    private final String imageApi = "http://localhost:8000/api/image";

    // item
    public ItemDetailResponseDto getItemDetail(Long itemDetailId) {

        CommonResponse<ItemDetailResponseDto> commonResponse = webClientUtil.get(
                itemApi + "/itemDetail/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<ItemDetailResponseDto>>() {
                },
                null,
                headers()
        );

        return commonResponse.getData();
    }

    // cart
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {

        CommonResponse<List<ItemDetailDto>> listCommonResponse = webClientUtil.get(
                cartApi + "/ItemDetailApi/" + userId,
                new ParameterizedTypeReference<CommonResponse<List<ItemDetailDto>>>() {
                },
                null,
                headers()
        );

        return listCommonResponse.getData();

    }

    // image
    public List<Long> deleteImageApi (List < Long > imageId) {

        String imageIdParam = imageId.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        CommonResponse<List<Long>> deleteImageApi = webClientUtil.delete(
                imageApi + "/deleteImageApi?imageId=" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<Long>>>() {
                },
                headers()
        );

        return deleteImageApi.getData();
    }

    private Map<String, String> headers () {
        return Map.of(HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
