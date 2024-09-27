package com.fashionmall.cart.entity;

import com.fashionmall.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table (name = "carts")
@Slf4j(topic = "장바구니")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column (name = "user_id", nullable = false)
//    private Long userId; //  받아오는 사용자 Id

    @Column (nullable = false)
    private Long itemId; //  받아오는 상품 id

    @Column (nullable = false)
    private Long colorId; //  받아오는  Id

    @Column (nullable = false)
    private Long sizeId; //  받아오는 Id

    @Column (nullable = false)
    private String itemName;

//    @Column (nullable = false)
//    private String itemImage;

    @Column (nullable = false)
    private int quantity; // 해당 제품의 선택 수량

    @Enumerated(EnumType.STRING)
    private CartStatusEnum status = CartStatusEnum.INACTIVATED;

    @Builder
    public Cart (Long itemId, Long colorId, Long sizeId,int quantity, CartStatusEnum cartStatus, String itemName) {
        this.itemId = itemId;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.quantity = quantity;
        this.status = cartStatus;
        this.itemName = itemName;
    }
}
