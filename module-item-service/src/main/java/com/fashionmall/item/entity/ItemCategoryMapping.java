package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "item_category_mapping")
@Slf4j (topic = "상품 카테고리 매핑 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemCategoryMapping extends BaseEntity {

    @EmbeddedId
    private ItemCategoryMappingId itemCategoryMappingId;

    // ItemCategoryMapping 클래스에 item 속성 추가
    @ManyToOne(fetch = FetchType.LAZY) @MapsId("itemId") // ItemCategoryMappingId의 itemId를 참조
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("categoryId") // ItemCategoryMappingId의 categoryId를 참조
    @JoinColumn(name = "category_id")
    private Category category;

}
