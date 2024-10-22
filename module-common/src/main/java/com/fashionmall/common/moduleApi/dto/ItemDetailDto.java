package com.fashionmall.common.moduleApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.security.Principal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;

}
