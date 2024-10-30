package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.*;
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

    private final String cartApi = "http://localhost:8000/api/cart";
    private final String userApi = "http://localhost:8000/api/user";
    private final String couponApi = "http://localhost:8000/api/coupon";
    private final String itemApi = "http://localhost:8000/api/item";
    private final String imageApi = "http://localhost:8000/api/image";

    // item
    public List<CouponDto> getUserCouponApi(Long userId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<List<CouponDto>> listCommonResponse = webClientUtil.get(couponApi + "/getCoupon", new ParameterizedTypeReference<CommonResponse<List<CouponDto>>>() {
        }, headers, ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
        return listCommonResponse.getData();
    }


    public ItemDetailResponseDto getItemDetail(Long itemDetailId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");

        CommonResponse<ItemDetailResponseDto> commonResponse = webClientUtil.get(
                itemApi + "/itemDetail/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<ItemDetailResponseDto>>() {
                },
                headers, ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return commonResponse.getData();
    }

    // cart
    //은미님께 요청
    public List<DeliveryAddressDto> getUserDeliveryAddressApi(Long userId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<List<DeliveryAddressDto>> listCommonResponse = webClientUtil.get(userApi + "/DeliveryAddressApi/" + userId, new ParameterizedTypeReference<CommonResponse<List<DeliveryAddressDto>>>() {
        }, headers, ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
        return listCommonResponse.getData();
    }

    /**
     * SELECT itemd.id, itemd.name, itemd.price, itemd.quantity
     * FROM cart c
     * JOIN item_detail itemd ON c.item_detail.id = itemd.id
     * WHERE c.user_id = 'userId' AND c.is_selected = TRUE;
     */
    //은미님께 요청
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {

        CommonResponse<List<ItemDetailDto>> listCommonResponse = webClientUtil.get(
                cartApi + "/itemDetail/" + userId,
                new ParameterizedTypeReference<CommonResponse<List<ItemDetailDto>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return listCommonResponse.getData();
    }

    public Map<Long, Integer> getItemQuantityApi(List<Long> itemDetailId) {

        CommonResponse<Map<Long, Integer>> integerCommonResponse = webClientUtil.get(
                itemApi + itemDetailId + "/quantity",
                new ParameterizedTypeReference<CommonResponse<Map<Long, Integer>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return integerCommonResponse.getData();
    }

    public Map<Long, ItemDetailDto> getItemDetailNameAndImageApi(List<Long> itemDetailIds) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<Map<Long, ItemDetailDto>> post = webClientUtil.post(itemApi + "/ItemDetailNameApi", itemDetailIds, new ParameterizedTypeReference<CommonResponse<Map<Long, ItemDetailDto>>>() {
        }, headers, ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
        return post.getData();
    }

    public void deductItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/deductItem",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {
                },
                headers(), ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
    }

    public void restoreItemQuantityApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/restoreItem",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {
                },
                headers(), ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
    }

    // image
    public Map<Long, String> uploadImageApi(List<ImageUploadDto> imageUploadDto) {
        CommonResponse<Map<Long, String>> uploadImageApi = webClientUtil.post(
                imageApi + "/uploadImage",
                imageUploadDto,
                new ParameterizedTypeReference<CommonResponse<Map<Long, String>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return uploadImageApi.getData();
    }

    public List<ImageDataDto> getImageApi(List<Long> imageId) {
        // referenceIds를 쿼리 파라미터로 변환
        String imageIdParam = imageId.stream()
                .map(id -> "imageId=" + id)
                .collect(Collectors.joining("&"));

        // API 호출
        CommonResponse<List<ImageDataDto>> getImageApi = webClientUtil.get(
                imageApi + "/getImage?" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<ImageDataDto>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return getImageApi.getData();
    }

    public List<Long> deleteImageApi(List<Long> imageId) {

        String imageIdParam = imageId.stream()
                .map(id -> "imageId=" + id)
                .collect(Collectors.joining("&"));

        CommonResponse<List<Long>> deleteImageApi = webClientUtil.delete(
                imageApi + "/deleteImage?" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<Long>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return deleteImageApi.getData();
    }

    public String callExternalApi() {
        String uri = "http://localhost:8000/api/coupon/test";  // 실제 테스트할 API 엔드포인트
        return webClientUtil.get(uri, String.class, headers(), ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
    }

    private Map<String, String> headers() {
        return Map.of(HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
