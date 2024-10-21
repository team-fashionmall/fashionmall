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
    public Map<Long,String> uploadImageApi (List<ImageUploadDto> imageUploadDto, Long workerId) {
        // 사용자 검증

        Map<Long,String> response = new HashMap<>();

        for (ImageUploadDto uploadInfo : imageUploadDto ) {

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String uniqueFileName = String.format("%s/%s/profile_%s_%s_%s",
                    uploadInfo.getReferenceType(),
                    uploadInfo.getImageType(),
                    timestamp,
                    uploadInfo.getFileName(),
                    UUID.randomUUID()
            );

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .build();

            PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

            Image image = Image.builder()
                    .url(String.valueOf(presignedPutObjectRequest.url()))
                    .uniqueFileName(uniqueFileName)
                    .build();
            imageRepository.save(image);

            ImageMapping imageMapping = ImageMapping.builder()
                    .image(image)
                    .referenceType(uploadInfo.getReferenceType())
                    .referenceId(uploadInfo.getReferenceId())
                    .imageType(uploadInfo.getImageType())
                    .build();
            imageMappingRepository.save(imageMapping);
            image.getImageMappings().add(imageMapping);

            response.put(image.getId(), image.getUrl());

        }

        return response;

    }

}
