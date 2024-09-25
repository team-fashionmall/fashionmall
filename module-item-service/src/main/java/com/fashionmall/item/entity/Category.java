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
@Table (name = "category")
@Slf4j (topic = "카테고리 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "parent_id")
    private Category parent; // 상위 카테고리

    @Enumerated (EnumType.STRING)
    @Column (name = "main_category", nullable = false)
    private MainCategoryEnum mainCategory;

    @OneToMany (mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Category> subCategories = new ArrayList<>(); // 하위 카테고리

    @Column (name = "sub_category", nullable = false)
    private String subCategory;

    /*@OneToMany (mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Category> categories = new ArrayList<>();
*/

    public Long getParentId() {
        return parent != null ? parent.getId() : null;
    }

    @Builder
    public Category (MainCategoryEnum mainCategory, String subCategory, Category parent) {

        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.parent = parent;

    }
}
