package com.fashionmall.image.service;


import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;

import java.util.List;
import java.util.Map;

public interface ImageService {

    Map<Long,String> uploadImageApi (List<ImageUploadDto> imageUploadDto, Long workerId);

    List<ImageDataDto> getImageApi (List<Long> imageId, Long workerId);

    List<Long> deleteImageApi (List<Long> imageId, Long workerId);
}
