package com.fashionmall.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResponseDto<T> {

    private List<T> content;
    private PageResponseDto pageInfo;

    public static <T> PageInfoResponseDto<T> of(int pageNo, int size, List<T> content, int totalCount) {
        int numberOfElements = Math.min(content.size(), size);
        int totalPage = (int) Math.ceil((double) totalCount / size);
        PageResponseDto pageResponseDto = new PageResponseDto(
                pageNo,
                size,
                numberOfElements,
                totalCount,
                totalPage
        );
        List<T> subList = content.subList(0, numberOfElements);
        return new PageInfoResponseDto<>(subList, pageResponseDto);
    }
}
