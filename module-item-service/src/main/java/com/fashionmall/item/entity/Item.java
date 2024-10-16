package com.fashionmall.item.entity;

import com.fashionmall.common.entity.BaseEntity;
import com.fashionmall.item.enums.StatusEnum;
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
@Table (name = "item")
@Slf4j (topic = "판매상품 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "worker_userId", nullable = false)
    private Long workerId; // msa 에서 받아오는 사용자 Id

    @Column (name = "item_name", nullable = false)
    private String name;

    @Enumerated (EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVATED;

    @OneToMany (mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemDetail> itemDetails = new ArrayList<>();

    @OneToMany (mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemCategoryMapping> itemCategoryMappings = new ArrayList<>();

    @OneToMany (mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemDiscount> itemDiscounts = new ArrayList<>();

    @Builder
    public Item (Long workerId, String name, StatusEnum status) {
        this.workerId = workerId;
        this.name = name;
        this.status = status;
    }
}
