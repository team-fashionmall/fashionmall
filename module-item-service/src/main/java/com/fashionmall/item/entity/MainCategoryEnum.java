package com.fashionmall.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum MainCategoryEnum {

    TOP("상의"),
    OUTER("외투"),
    PANTS("바지"),
    SKIRT("치마"),
    ONEPIECE("원피스");

    private final String mainCategory;

}
