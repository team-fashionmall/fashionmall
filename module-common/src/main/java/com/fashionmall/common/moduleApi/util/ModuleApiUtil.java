package com.fashionmall.common.moduleApi.util;


import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.*;
import com.fashionmall.common.moduleApi.dto.*;
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

    public List<CartItemDto> getIsSelectedItemApi() {

        CommonResponse<List<CartItemDto>> commonResponse = webClientUtil.get(
                cartApi + "/getIsSelectedItem",
                new ParameterizedTypeReference<CommonResponse<List<CartItemDto>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return commonResponse.getData();
    }

    public ItemDetailResponseDto getItemDetailApi(Long itemDetailId) {
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

    public List<ItemDetailInfoDto> getItemDetailInfoApi (List<Long> itemDetailId) {

        CommonResponse<List<ItemDetailInfoDto>> commonResponse = webClientUtil.get(
            itemApi + "/getItemDetail/" + itemDetailId,
            new ParameterizedTypeReference<CommonResponse<List<ItemDetailInfoDto>>>(){},
            headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return commonResponse.getData();
    }

    public Map<Long, Integer> getItemStockApi(List<Long> itemDetailId) {

        CommonResponse<Map<Long, Integer>> integerCommonResponse = webClientUtil.get(
                itemApi + "/getStock/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<Map<Long, Integer>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return integerCommonResponse.getData();
    }

    public String getItemNameApi(Long itemId) {

        CommonResponse<String> commonResponse = webClientUtil.get(
                itemApi + "/getItemName/" + itemId,
                new ParameterizedTypeReference<CommonResponse<String>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return commonResponse.getData();
    }
    public Map<Long, ItemDetailDto> getItemDetailNameAndImageApi (List<Long> itemDetailIds) {
        return null; // 오류나서 만들어뒀습니다. 수정 후 지워주세요!
    }

    public void deductItemStockApi(List<OrderItemDto> orderItemDto) {

        webClientUtil.patch(
                itemApi + "/deductItem",
                orderItemDto,
                new ParameterizedTypeReference<Void>() {
                },
                headers(), ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE);
    }

    public void restoreItemStockApi(List<OrderItemDto> orderItemDto) {

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

    public List<ImageDataDto> getImageApi (List<Long> imageId) {

        String imageIdParam = imageId.stream()
                .map(id -> "imageId=" + id)
                .collect(Collectors.joining("&"));

        CommonResponse<List<ImageDataDto>> getImageApi = webClientUtil.get(
                imageApi + "/getImage?" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<ImageDataDto>>>() {
                },
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return getImageApi.getData();
    }

    public List<ItemPriceNameDto> getItemPriceAndNameApi (List<Long> itemDetailIds) {

        String itemDetailId= itemDetailIds.stream()
                .map(id -> "itemDetailId=" + id)
                .collect(Collectors.joining("&"));

        CommonResponse <List<ItemPriceNameDto>> integerCommonResponse = webClientUtil.get(
                itemApi + "/itemPriceAndName?" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<List<ItemPriceNameDto>>>() {},
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return integerCommonResponse.getData();
    }

    public Long deleteImageApi (Long imageId) {

        CommonResponse<Long> deleteImageApi = webClientUtil.delete(
                imageApi + "/deleteImage/" + imageId,
                new ParameterizedTypeReference<CommonResponse<Long>>() {},
                headers(),
                ErrorResponseCode.CLIENT_ERROR, ErrorResponseCode.SERVER_ERROR_FROM_SERVICE
        );

        return deleteImageApi.getData();
    }

    private Map<String, String> headers() {
        return Map.of(HttpHeaders.CONTENT_TYPE, "application/json");
    }
}
