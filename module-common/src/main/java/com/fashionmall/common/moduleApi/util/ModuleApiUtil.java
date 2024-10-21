package com.fashionmall.common.moduleApi.util;

import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleApiUtil {

    private final WebClientUtil webClientUtil;

    private final String imageApi = "http://localhost:8000/api/image";

    public List<ImageDataDto> getImageApi (List<Long> imageId) {
        // referenceIds를 쿼리 파라미터로 변환
        String imageIdParam = imageId.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // API 호출
        CommonResponse<List<ImageDataDto>> getImageApi = webClientUtil.get(
                imageApi + "/getImageApi?imageId=" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<ImageDataDto>>>() {},
                null, // 쿼리 파라미터는 URL에 포함되므로 null
                headers()
        );

        return getImageApi.getData();
    }

    private Map<String, String> headers (){
        return Map.of (HttpHeaders.CONTENT_TYPE, "application/json");
    }

}
