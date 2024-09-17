package com.fashionmall.coupon.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import com.fashionmall.coupon.entity.QUserCoupon;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.CouponType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.fashionmall.coupon.entity.QCoupon.coupon;


@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageInfoResponseDto<AdminCouponResponseDto> couponListPaged(Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<AdminCouponResponseDto> content = queryFactory
                .select(Projections.constructor(AdminCouponResponseDto.class,
                        coupon.id,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate,
                        coupon.createdAt,
                        coupon.updatedAt))
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

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<UserCouponResponseDto> findUserCouponByUserId(Long userId, Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<UserCouponResponseDto> content = queryFactory
                .select(Projections.constructor(UserCouponResponseDto.class,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate))
                .from(QUserCoupon.userCoupon)
                .join(QUserCoupon.userCoupon.coupon, coupon)
                .where(QUserCoupon.userCoupon.userId.eq(userId),
                        QUserCoupon.userCoupon.isUsed.eq(false),
                        coupon.endDate.after(LocalDateTime.now()),
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
                        coupon.endDate.after(LocalDateTime.now()),
                        coupon.status.eq(CouponStatus.ACTIVATED))
                .fetchOne();

        int totalCount = (fetchOne != null) ? fetchOne.intValue() : 0;

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }

    @Override
    public PageInfoResponseDto<UserCouponResponseDto> findDownloadableCoupon(Long userId, Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<UserCouponResponseDto> content = queryFactory
                .select(Projections.constructor(UserCouponResponseDto.class,
                        coupon.name,
                        coupon.discountType,
                        coupon.discountValue,
                        coupon.minPurchasePrice,
                        coupon.maxDiscountPrice,
                        coupon.startDate,
                        coupon.endDate))
                .from(coupon)
                .leftJoin(QUserCoupon.userCoupon)
                .on(QUserCoupon.userCoupon.coupon.eq(coupon)
                        .and(QUserCoupon.userCoupon.userId.eq(userId)))
                .where(coupon.couponType.eq(CouponType.DOWNLOAD),
                        coupon.status.eq(CouponStatus.ACTIVATED),
                        coupon.endDate.after(LocalDateTime.now()),
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
                        coupon.endDate.after(LocalDateTime.now()),
                        QUserCoupon.userCoupon.id.isNull())
                .fetchOne();

        int totalCount = (fetchOne != null) ? fetchOne.intValue() : 0;

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }
}
