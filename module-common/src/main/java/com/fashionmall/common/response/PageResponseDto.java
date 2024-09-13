package com.fashionmall.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto {

    private int page; //페이지
    private int size; //화면에 표시할 콘텐츠 개수 제한
    private int numberOfElements; //화면에 표시된 콘텐츠 개수
    private int totalCount; //전체 콘텐츠 개수
    private int totalPage; //전체 페이지 수

}
