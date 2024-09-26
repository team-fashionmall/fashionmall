package com.fashionmall.item.repository;

import com.fashionmall.item.entity.CategoryMain;
import com.fashionmall.item.entity.CategorySub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorySubRepository extends JpaRepository <CategorySub, Long> {
    Optional <CategorySub> findByName (String subCategory);
}
