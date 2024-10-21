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

    @DeleteMapping ("/deleteImageApi")
    public CommonResponse <List<Long>> deleteImageApi (@RequestParam List<Long> imageId) {
        Long workerId = 1L;
        return ApiResponseUtil.success(imageService.deleteImageApi(imageId, workerId));
    }

}

