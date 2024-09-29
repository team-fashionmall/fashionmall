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
@Table (name = "item_category_mapping")
@Slf4j (topic = "상품 카테고리 매핑 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemCategoryMapping extends BaseEntity {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category1 category1;

    @Column (nullable = false)
    private Long category2Id;

    @Builder
    public ItemCategoryMapping (Item item, Category1 category1, Long category2Id) {
        this.item = item;
        this.category1 = category1;
        this.category2Id = category2Id;
    }

}
