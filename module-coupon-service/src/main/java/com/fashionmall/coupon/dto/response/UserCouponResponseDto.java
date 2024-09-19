package com.fashionmall.coupon.dto.response;

import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.enums.DiscountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCouponResponseDto {

    private String name;
    private DiscountType discountType;
    private int discountValue;
    private int minPurchasePrice;
    private Integer maxDiscountPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public static UserCouponResponseDto from(Coupon coupon) {
        return new UserCouponResponseDto(
                coupon.getName(),
                coupon.getDiscountType(),
                coupon.getDiscountValue(),
                coupon.getMinPurchasePrice(),
                coupon.getMaxDiscountPrice(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }

}
