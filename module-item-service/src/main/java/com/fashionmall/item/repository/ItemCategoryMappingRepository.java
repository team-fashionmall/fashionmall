package com.fashionmall.item.repository;

import com.fashionmall.item.entity.ItemCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryMappingRepository extends JpaRepository <ItemCategoryMapping, Long> {
}
