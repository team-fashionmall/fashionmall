package com.fashionmall.item.repository;

import com.fashionmall.item.entity.CategoryMain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryMainRepository extends JpaRepository <CategoryMain, Long> {
    Optional <CategoryMain> findByName (String mainCategory);
}
