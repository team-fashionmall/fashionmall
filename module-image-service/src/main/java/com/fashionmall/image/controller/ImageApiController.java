package com.fashionmall.image.controller;

import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageApiController {

    private final ImageService imageService;
    private final ModuleApiUtil moduleApiUtil;

    @PostMapping("/uploadImageApi")
    public CommonResponse<Map<Long,String>> uploadImageApi (@RequestBody List<ImageUploadDto> imageUploadDto) {
        Long workerId = 1L;
        return ApiResponseUtil.success(imageService.uploadImageApi(imageUploadDto, workerId));
    }

}

