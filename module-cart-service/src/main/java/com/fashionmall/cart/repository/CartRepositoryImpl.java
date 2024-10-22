package com.fashionmall.cart.repository;

import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.fashionmall.cart.entity.QCart.cart;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {
        return jpaQueryFactory
                .select(Projections.constructor(ItemDetailDto.class,
                        cart.itemDetailId,
                        cart.itemDetailName,
                        cart.price,
                        cart.quantity
                ))
                .from(cart)
                .where(cart.userId.eq(userId),
                        cart.isSelected.eq(true))
                .orderBy(cart.id.desc())
                .fetch();
        }

    @Override
    public PageInfoResponseDto<CartResponseDto> getCartList (Pageable pageable, Long userId, String imageUrl) {

        List<CartResponseDto> cartList = jpaQueryFactory
                .select(Projections.constructor(CartResponseDto.class,
                        cart.id,
                        cart.itemDetailId,
                        cart.imageId,
                        Expressions.constant(imageUrl),
                        cart.quantity,
                        cart.price,
                        cart.isSelected
                        ))
                .from(cart)
                .where(cart.userId.eq(userId))
                .orderBy(cart.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long fetchOne = jpaQueryFactory
                .select(cart.count())
                .from(cart)
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, cartList, totalCount);
    }
}
