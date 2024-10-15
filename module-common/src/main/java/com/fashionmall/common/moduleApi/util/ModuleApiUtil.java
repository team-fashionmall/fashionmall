package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    //은미님께 요청
    public List<DeliveryAddressDto> getUserDeliveryAddressApi(Long userId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        return webClientUtil.get(userApi + "/DeliveryAddressApi/" + userId, new ParameterizedTypeReference<List<DeliveryAddressDto>>() {
        }, null, headers);
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
        return webClientUtil.get(cartApi + "/ItemDetailApi/" + userId, new ParameterizedTypeReference<List<ItemDetailDto>>() {
        }, null, headers);
    }

    //은미님께 요청
    public int getItemQuantityApi(Long itemId) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        return webClientUtil.get(itemApi + "/ItemQuantityApi", new ParameterizedTypeReference<Integer>() {
        }, null, headers);
    }

    //은미님께 요청
    public void deductItemQuantityApi(List<OrderItemDto> orderItemDto) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        webClientUtil.post(itemApi + "Deduct Item Api", orderItemDto, new ParameterizedTypeReference<Void>() {
        }, headers);
    }

    //은미님께 요청
    public void restoreItemQuantityApi(List<OrderItemDto> orderItemDto) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        webClientUtil.post(itemApi + "restore Item Api", orderItemDto, new ParameterizedTypeReference<Void>() {
        }, headers);
    }

    //은미님께 요청
    public Map<Long, String> getItemDetailNameApi(List<Long> itemDetailIds) {
        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json");
        List<String> ids = itemDetailIds.stream()
                .map(String::valueOf)
                .toList();
        Map<String, String> params = new HashMap<>();
        params.put("itemDetailIds", String.join(",", ids));
        CommonResponse<Map<Long, String>> mapCommonResponse = webClientUtil.get(itemApi + "/ItemDetailNameApi", new ParameterizedTypeReference<CommonResponse<Map<Long, String>>>() {
        }, params, headers);
        return mapCommonResponse.getData();
    }
}
