package com.fashionmall.order.repository;

import com.fashionmall.order.entity.BillingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long>, BillingKeyRepositoryCustom {

    String findCustomerUidById(Long id);
}
