package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.*;
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
    private final String userApi = "http://localhost:8000/api/user";
    private final String couponApi = "http://localhost:8000/api/coupon";
    private final String itemApi = "http://localhost:8000/api/item";

    public List<CouponDto> getUserCouponApi(Long userId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<List<CouponDto>> listCommonResponse = webClientUtil.get(couponApi + "/getCoupon/" + userId, new ParameterizedTypeReference<CommonResponse<List<CouponDto>>>() {
        }, null, headers);
        return listCommonResponse.getData();
    }

    public ItemDetailResponseDto getItemDetail(Long itemDetailId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<ItemDetailResponseDto> commonResponse = webClientUtil.get(
                itemApi + "/itemDetail/" + itemDetailId,
                new ParameterizedTypeReference<CommonResponse<ItemDetailResponseDto>>() {
                },
                null,
                headers
        );

        return commonResponse.getData();
    }

    //은미님께 요청
    public List<DeliveryAddressDto> getUserDeliveryAddressApi(Long userId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<List<DeliveryAddressDto>> listCommonResponse = webClientUtil.get(userApi + "/DeliveryAddressApi/" + userId, new ParameterizedTypeReference<CommonResponse<List<DeliveryAddressDto>>>() {
        }, null, headers);
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
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<List<ItemDetailDto>> listCommonResponse = webClientUtil.get(cartApi + "/ItemDetailApi/" + userId, new ParameterizedTypeReference<CommonResponse<List<ItemDetailDto>>>() {
        }, null, headers);
        return listCommonResponse.getData();
    }

    //은미님께 요청
    public Map<Long, Integer> getItemQuantityApi(List<Long> itemDetailId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<Map<Long, Integer>> mapCommonResponse = webClientUtil.post(itemApi + "/ItemQuantityApi", itemDetailId, new ParameterizedTypeReference<CommonResponse<Map<Long, Integer>>>() {
        }, headers);
        return mapCommonResponse.getData();
    }

    //은미님께 요청
    public void deductItemQuantityApi(List<OrderItemDto> orderItemDto) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        webClientUtil.patch(itemApi + "Deduct Item Api", orderItemDto, new ParameterizedTypeReference<Void>() {
        }, headers);
    }

    //은미님께 요청
    public void restoreItemQuantityApi(List<OrderItemDto> orderItemDto) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        webClientUtil.patch(itemApi + "restore Item Api", orderItemDto, new ParameterizedTypeReference<Void>() {
        }, headers);
    }

    //은미님께 요청
    public Map<Long, ItemDetailDto> getItemDetailNameAndImageApi(List<Long> itemDetailIds) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        CommonResponse<Map<Long, ItemDetailDto>> post = webClientUtil.post(itemApi + "/ItemDetailNameApi", itemDetailIds, new ParameterizedTypeReference<CommonResponse<Map<Long, ItemDetailDto>>>() {
        }, headers);
        return post.getData();
    }
}
