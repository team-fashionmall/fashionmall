package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long> {

}
