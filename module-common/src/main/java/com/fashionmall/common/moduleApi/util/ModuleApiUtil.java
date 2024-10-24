package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
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
import java.util.stream.Collectors;

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
                new ParameterizedTypeReference<CommonResponse<ItemDetailResponseDto>>() {},
                null,
                headers()
        );

        return commonResponse.getData();
    }

    // cart
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {

        CommonResponse<List<ItemDetailDto>> listCommonResponse = webClientUtil.get(
                cartApi + "/itemDetail/" + userId,
                new ParameterizedTypeReference<CommonResponse<List<ItemDetailDto>>>() {},
                null,
                headers()
        );

        return listCommonResponse.getData();

    }

    public int getItemQuantityApi(Long itemDetailId) {

        CommonResponse<Integer> integerCommonResponse = webClientUtil.get(
                itemApi + itemDetailId + "/quantity",
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
                itemApi + "/itemDetailName?itemDetailIds=" + ids,
                new ParameterizedTypeReference<CommonResponse<Map<Long, String>>>() {},
                null,
                headers()
        );

        return mapCommonResponse.getData();
    }

    public void deductItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/deductItem",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {},
                headers());
    }

    public void restoreItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/restoreItem",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {},
                headers());
    }

    // image
    public Map <Long, String> uploadImageApi(List<ImageUploadDto> imageUploadDto) {
        CommonResponse<Map<Long, String>> uploadImageApi = webClientUtil.post(
                imageApi + "/uploadImage",
                imageUploadDto,
                new ParameterizedTypeReference<CommonResponse<Map<Long,String>>>() {},
                headers()
        );

        return uploadImageApi.getData();
    }

    public List<ImageDataDto> getImageApi (List<Long> imageId) {
        // referenceIds를 쿼리 파라미터로 변환
        String imageIdParam = imageId.stream()
                .map(id -> "imageId=" + id)
                .collect(Collectors.joining("&"));

        // API 호출
        CommonResponse<List<ImageDataDto>> getImageApi = webClientUtil.get(
                imageApi + "/getImage?" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<ImageDataDto>>>() {},
                null, // 쿼리 파라미터는 URL에 포함되므로 null
                headers()
        );

        return getImageApi.getData();
    }

    public List <Long> deleteImageApi (List<Long> imageId) {

        String imageIdParam = imageId.stream()
                .map(id -> "imageId=" + id)
                .collect(Collectors.joining("&"));

        CommonResponse<List<Long>> deleteImageApi = webClientUtil.delete(
                imageApi + "/deleteImage?" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<Long>>>() {},
                headers()
        );

        return deleteImageApi.getData();
    }

    private Map<String, String> headers (){
        return Map.of (HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
