package com.fashionmall.user.repository;

import com.fashionmall.user.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository <DeliveryAddress, Long> {
}
