package com.fashionmall.item.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.item.dto.response.AdminItemDetailResponseDto;
import com.fashionmall.item.dto.response.AdminItemResponseDto;
import com.fashionmall.item.dto.response.ItemDetailListResponseDto;
import com.fashionmall.item.dto.response.ItemListResponseDto;
import com.fashionmall.item.entity.QItemCategoryMapping;
import com.fashionmall.item.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;


import static com.fashionmall.item.entity.QItem.item;
import static com.fashionmall.item.entity.QItemCategoryMapping.itemCategoryMapping;
import static com.fashionmall.item.entity.QItemDetail.itemDetail;
import static com.fashionmall.item.entity.QItemDiscount.itemDiscount;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageInfoResponseDto <ItemListResponseDto> itemListPageNation (Pageable pageable, String itemName, Long category1, Long category2) {

        List<ItemListResponseDto> itemList = queryFactory
                .select(Projections.constructor(ItemListResponseDto.class,
                        Projections.constructor(ItemListResponseDto.ItemInfo.class,
                                item.id,
                                item.name,
                                item.workerId,
                                item.imageId,
                                item.imageUrl
                        ),
                        Projections.constructor(ItemListResponseDto.ItemDetailInfo.class,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice")
                        )
                ))
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemCategoryMappings, itemCategoryMapping)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .where(getFilter(itemName, category1, category2))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long fetchOne = queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();

        int totalCount = fetchOne.intValue();

        return PageInfoResponseDto.of(pageable, itemList, totalCount);

    }

    @Override
    public PageInfoResponseDto <ItemDetailListResponseDto> itemDetailListPageNation (Long itemId, Pageable pageable) {

        List<ItemDetailListResponseDto> itemDetailList = queryFactory
                .select(Projections.constructor(ItemDetailListResponseDto.class,
                        Projections.constructor(ItemDetailListResponseDto.ItemInfo.class,
                                item.id,
                                item.name
                        ),
                        Projections.constructor(ItemDetailListResponseDto.ItemDetailInfo.class,
                                itemDetail.itemColor.color,
                                itemDetail.itemSize.size,
                                itemDetail.name,
                                itemDetail.price,
                                ExpressionUtils.as(calculateDiscount(itemDetail.price, itemDiscount.status, itemDiscount.type, itemDiscount.value),
                                        "discountPrice"),
                                itemDetail.quantity,
                                itemDetail.imageId,
                                itemDetail.imageUrl
                        ),
                        Projections.constructor(ItemDetailListResponseDto.ItemDiscountInfo.class,
                                itemDiscount.type,
                                itemDiscount.value
                        ),
                        Projections.constructor(ItemDetailListResponseDto.ItemCategoryInfo.class,
                                itemCategoryMapping.category1.id,
                                itemCategoryMapping.category2Id
                        )
                ))
                .from(item)
                .innerJoin(item.itemDetails, itemDetail)
                .innerJoin(item.itemDiscounts, itemDiscount)
                .innerJoin(item.itemCategoryMappings, itemCategoryMapping)
                .where(item.id.eq(itemId))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countList = queryFactory
                .select(item.count())
                .from(item)
                .where(item.id.eq(itemId))
                .fetchOne();

        int totalCount = countList.intValue();

        return PageInfoResponseDto.of(pageable, itemDetailList, totalCount);
    }


    private BooleanExpression getFilter (String itemName, Long category1, Long category2) {

        BooleanExpression filters = item.isNotNull();

        if (itemName != null && !itemName.isEmpty()) {
            filters = filters.and(item.name.containsIgnoreCase(itemName));
        }

        if (category1 != null) {
            filters = filters.and(itemCategoryMapping.category1.id.eq(category1));
        }

        if (category2 != null) {
            filters = filters.and(itemCategoryMapping.category2Id.eq(category2));
        }

        return filters;
    }

    private NumberExpression<Integer> calculateDiscount (NumberExpression<Integer> price, EnumPath<StatusEnum> status, EnumPath <ItemDiscountTypeEnum> type, NumberExpression<Integer> value) {
        return new CaseBuilder()
                .when(status.eq(StatusEnum.ACTIVATED).and(type.eq(ItemDiscountTypeEnum.RATE)))
                .then(price.subtract(price.multiply(value).divide(100)))
                .when(status.eq(StatusEnum.ACTIVATED).and(type.eq(ItemDiscountTypeEnum.AMOUNT)))
                .then(price.subtract(value))
                .otherwise(0);
    }
}
