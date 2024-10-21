package com.fashionmall.image.service;


import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.image.dto.request.ImageUploadRequestDto;
import com.fashionmall.image.dto.response.ImageListResponseDto;
import com.fashionmall.image.dto.response.ImageUploadResponseDto;


import java.util.List;
import java.util.Map;

public interface ImageService {

    List<ImageDataDto> getImageApi (List<Long> imageId, Long workerId);

}
