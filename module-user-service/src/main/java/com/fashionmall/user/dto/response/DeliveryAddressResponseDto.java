package com.fashionmall.user.dto.response;

import com.fashionmall.user.entity.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressResponseDto {

    private Long id;
    private String zipCode;
    private String roadAddress;

    public static DeliveryAddressResponseDto from (DeliveryAddress deliveryAddress) {
        return DeliveryAddressResponseDto.builder()
                .id(deliveryAddress.getId())
                .zipCode(deliveryAddress.getZipCode())
                .roadAddress(deliveryAddress.getRoadAddress())
                .build();
    }
}
