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
@Table (name = "item_size")
@Slf4j (topic = "의류 사이즈 테이블")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ItemSize extends BaseEntity {

    @Id @Column(name = "item_size_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "item_size_name", nullable = false)
    private String size;

    @OneToMany (mappedBy = "itemSize", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <ItemDetail> itemDetails = new ArrayList<>();

    @Builder
    public ItemSize (String size) {

        this.size = size;

    }
}
