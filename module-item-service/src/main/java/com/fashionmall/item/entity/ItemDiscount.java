package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.item.enums.ItemDiscountTypeEnum;
import com.fashionmall.item.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_discount")
@Slf4j (topic = "상품 할인 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemDiscount extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn (name = "item_id", nullable = false)
    private Item item;

    @Enumerated (EnumType.STRING) @Column (nullable = false)
    private ItemDiscountTypeEnum type;

    @Column (nullable = false)
    private int value; // '할인 값(정률: %, 정액: 원)'

    @Enumerated (EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVATED;

}
