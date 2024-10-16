package com.fashionmall.order.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.order.entity.QBillingKey.billingKey;

@Repository
@RequiredArgsConstructor
public class BillingKeyRepositoryImpl implements BillingKeyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    public PageInfoResponseDto<UserBillingKeyResponseDto> findBillingKeyByUserId(Long userId, Pageable pageable) {

        int offset = (int) pageable.getOffset();
        int size = pageable.getPageSize();

        List<UserBillingKeyResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(UserBillingKeyResponseDto.class,
                        billingKey.id,
                        billingKey.cardNickname,
                        billingKey.customerUid))
                .from(billingKey)
                .where(billingKey.userId.eq(userId))
                .orderBy(billingKey.id.asc())
                .offset(offset)
                .limit(size)
                .fetch();

        Long fetchOne = jpaQueryFactory
                .select(billingKey.count())
                .from(billingKey)
                .where(billingKey.userId.eq(userId))
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, content, totalCount);
    }
}
