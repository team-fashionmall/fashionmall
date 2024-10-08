package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Item;
import com.fashionmall.item.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository <ItemDetail, Long> {
    ItemDetail findTopByItemOrderByCreatedAtDesc(Item item);
}
