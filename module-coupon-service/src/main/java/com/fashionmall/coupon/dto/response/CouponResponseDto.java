package com.fashionmall.coupon.dto.response;

import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponResponseDto {

    private Long id;
    private String name;
    private DiscountType discountType;
    private int discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;

    public static CouponResponseDto from(Coupon coupon) {
        return new CouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getStartDate(),
                coupon.getEndDate(),
                coupon.getMinPurchasePrice(),
                coupon.getMaxDiscountPrice()
        );
    }

}
