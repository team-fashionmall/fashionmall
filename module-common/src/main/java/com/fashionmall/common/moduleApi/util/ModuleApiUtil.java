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

    public List <Long> deleteImageApi (List<Long> imageId) {

        String imageIdParam = imageId.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        CommonResponse <List<Long>> deleteImageApi = webClientUtil.delete(
                imageApi + "/deleteImageApi?imageId=" + imageIdParam,
                new ParameterizedTypeReference<CommonResponse<List<Long>>>() {},
                headers()
        );

        return deleteImageApi.getData();
    }

    private Map<String, String> headers (){
        return Map.of (HttpHeaders.CONTENT_TYPE, "application/json");
    }

}
