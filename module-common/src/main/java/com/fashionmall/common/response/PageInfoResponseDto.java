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

    public static <T> PageInfoResponseDto<T> of(int page, int size, List<T> content) {
        int numberOfElements = Math.min(content.size(), size);
        int totalPages = (int) Math.ceil((double) content.size() / size);
        PageResponseDto pageResponseDto = new PageResponseDto(
                page,
                size,
                numberOfElements,
                content.size(),
                totalPages
        );
        List<T> subList = content.subList(0, numberOfElements);
        return new PageInfoResponseDto<>(subList, pageResponseDto);
    }
}
