package com.fashionmall.item.repository;

import com.fashionmall.item.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {

    boolean existsByParentIsNull();

}
