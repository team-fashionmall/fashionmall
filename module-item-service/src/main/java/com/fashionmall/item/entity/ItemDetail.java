package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_detail")
@Slf4j (topic = "상품 상세 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemDetail extends BaseEntity {

    @Id @Column(name = "item_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn (name = "size_id", nullable = false)
    private ItemSize itemSize;

    @ManyToOne
    @JoinColumn (name = "item_color", nullable = false)
    private ItemColor itemColor;

    @Column (name = "item_detail_name", nullable = false)
    private String name;

    @Enumerated (EnumType.STRING)
    @Column (name = "item_detail_status")
    private ItemStatusEnum status = ItemStatusEnum.ACTIVATED; // 품절 여부

    @Column (nullable = false)
    private int price;

    @Column (nullable = false)
    private int quantity; // 재고수량

    @Builder
    public ItemDetail (Item item, ItemSize itemSize, ItemColor itemColor, String name, ItemStatusEnum status, int price, int quantity) {

        this.item = item;
        this.itemSize = itemSize;
        this.itemColor = itemColor;
        this.name = name;
        this.status = status;
        this.price = price;
        this.quantity = quantity;

    }

}
