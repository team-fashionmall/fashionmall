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
@Table (name = "category_sub")
@Slf4j (topic = "서브 카테고리")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class CategorySub extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @ManyToOne @JoinColumn (nullable = false)
    private CategoryMain categoryMain;

    @Builder
    public CategorySub (String subCategory) {
        this.name = subCategory;
    }

}
