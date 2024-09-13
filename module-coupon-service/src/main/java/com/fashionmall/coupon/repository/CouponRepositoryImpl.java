package com.fashionmall.coupon.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.CouponResponseDto;
import com.fashionmall.coupon.entity.QUserCoupon;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.CouponType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.coupon.entity.QCoupon.coupon;


@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageInfoResponseDto<CouponResponseDto> couponListPaged(int pageNo) {
        int size = 10;
        int offset = (pageNo - 1) * size;
        List<CouponResponseDto> content = queryFactory
                .select(Projections.bean(CouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice))
                .from(coupon)
                .orderBy(coupon.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(coupon.count())
                .from(coupon)
                .fetchOne();

        int totalCount = (fetchOne != null) ? fetchOne.intValue() : 0; //NullPointException 방지

        return PageInfoResponseDto.of(pageNo, size, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<CouponResponseDto> findUserCouponByUserId(Long userId, int pageNo) {
        int size = 10;
        int offset = (pageNo - 1) * size;
        List<CouponResponseDto> content = queryFactory
                .select(Projections.bean(CouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice))
                .from(QUserCoupon.userCoupon)
                .join(QUserCoupon.userCoupon.coupon, coupon)
                .where(QUserCoupon.userCoupon.userId.eq(userId),
                        QUserCoupon.userCoupon.isUsed.eq(false),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .orderBy(coupon.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(coupon.count())
                .from(coupon)
                .join(QUserCoupon.userCoupon)
                .where(QUserCoupon.userCoupon.userId.eq(userId),
                        QUserCoupon.userCoupon.isUsed.eq(false),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .fetchOne();

        int totalCount = (fetchOne != null) ? fetchOne.intValue() : 0;

        return PageInfoResponseDto.of(pageNo, size, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<CouponResponseDto> findDownloadableCoupon(Long userId, int pageNo) {
        int size = 10;
        int offset = (pageNo - 1) * size;
        List<CouponResponseDto> content = queryFactory
                .select(Projections.bean(CouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice))
                .from(coupon)
                .leftJoin(QUserCoupon.userCoupon)
                .on(QUserCoupon.userCoupon.coupon.eq(coupon)
                        .and(QUserCoupon.userCoupon.userId.eq(userId)))
                .where(coupon.couponType.eq(CouponType.DOWNLOAD),
                        coupon.status.eq(CouponStatus.ACTIVATED),
                        QUserCoupon.userCoupon.id.isNull())
                .orderBy(coupon.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = queryFactory
                .select(coupon.count())
                .from(coupon)
                .leftJoin(QUserCoupon.userCoupon)
                .on(QUserCoupon.userCoupon.coupon.eq(coupon)
                        .and(QUserCoupon.userCoupon.userId.eq(userId)))
                .where(coupon.couponType.eq(CouponType.DOWNLOAD),
                        coupon.status.eq(CouponStatus.ACTIVATED),
                        QUserCoupon.userCoupon.id.isNull())
                .fetchOne();

        int totalCount = (fetchOne != null) ? fetchOne.intValue() : 0;

        return PageInfoResponseDto.of(pageNo, size, content, totalCount);
    }
}
