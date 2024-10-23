package com.fashionmall.cart.repository;

import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.fashionmall.cart.entity.QCart.cart;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom{

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
}
