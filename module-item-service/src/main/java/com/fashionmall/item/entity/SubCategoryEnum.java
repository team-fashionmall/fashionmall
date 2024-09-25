package com.fashionmall.item.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubCategoryEnum {

    SHIRT("셔츠", MainCategoryEnum.TOP),
    SWEATER("스웨터", MainCategoryEnum.TOP),
    HOOD("후드", MainCategoryEnum.TOP),

    CARDIGAN("가디건", MainCategoryEnum.OUTER),
    JACKET("재킷", MainCategoryEnum.OUTER),
    FRANCH_COAT("트렌치 코트", MainCategoryEnum.OUTER),

    SHORTS_PANTS("반바지", MainCategoryEnum.PANTS),
    SLACKS("슬랙스", MainCategoryEnum.PANTS),
    STRAIGHT_PANTS("일자 바지", MainCategoryEnum.PANTS),

    MINI_SKIRT("미니 스커트", MainCategoryEnum.SKIRT),
    MIDI_SKIRT("미디 스커트", MainCategoryEnum.SKIRT),
    LONG_SKIRT("롱 스커트", MainCategoryEnum.SKIRT),

    MINI_ONEPIECE("미니 원피스", MainCategoryEnum.ONEPIECE),
    MIDI_ONEPIECE("미디 원피스", MainCategoryEnum.ONEPIECE),
    LONG_ONEPIECE("롱 원피스", MainCategoryEnum.ONEPIECE);

    private final String subCategory;
    private final MainCategoryEnum mainCategoryEnum;
}
