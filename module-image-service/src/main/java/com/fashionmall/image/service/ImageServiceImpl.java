package com.fashionmall.image.service;

import com.amazonaws.SdkClientException;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;

import com.fashionmall.common.moduleApi.dto.ImageDataDto;
import com.fashionmall.common.moduleApi.dto.ImageUploadDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.image.dto.request.ImageUploadRequestDto;
import com.fashionmall.image.dto.response.ImageListResponseDto;
import com.fashionmall.image.dto.response.ImageUploadResponseDto;
import com.fashionmall.image.entity.Image;
import com.fashionmall.image.entity.ImageMapping;
import com.fashionmall.image.repository.ImageMappingRepository;
import com.fashionmall.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ImageService")
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final ImageRepository imageRepository;
    private final ImageMappingRepository imageMappingRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    @Transactional
    public List<ImageDataDto> getImageApi (List<Long> imageId, Long workerId) {
        // 본인 인증

        List<ImageDataDto> imageDataDtoList = new ArrayList<>();

        for (Long imageIds : imageId) {

            Image image = imageRepository.findById(imageIds)
                    .orElseThrow(()-> new CustomException(ErrorResponseCode.BAD_REQUEST));

            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            List <String> s3ImageKey = s3Client.listObjectsV2(listObjectsRequest)
                    .contents()
                    .stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());

            if (!s3ImageKey.contains(image.getUniqueFileName())) {
                throw new CustomException(ErrorResponseCode.NOT_FOUND_S3);
            }

            ImageDataDto imageDataDto = ImageDataDto.builder()
                    .id(image.getId())
                    .url(image.getUrl())
                    .build();

            imageDataDtoList.add(imageDataDto);
        }

        return imageDataDtoList;
    }

}
