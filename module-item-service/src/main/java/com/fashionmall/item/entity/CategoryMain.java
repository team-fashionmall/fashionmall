package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table (name = "category_main")
@Slf4j (topic = "메인 카테고리 entity")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class CategoryMain extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @OneToMany (mappedBy = "categoryMain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <CategorySub> categorySubs = new ArrayList<>();

    @OneToMany (mappedBy = "categoryMain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemCategoryMapping> itemCategoryMappings = new ArrayList<>();

    @Builder
    public CategoryMain (String mainCategory) {
        this.name = mainCategory;
    }
}
