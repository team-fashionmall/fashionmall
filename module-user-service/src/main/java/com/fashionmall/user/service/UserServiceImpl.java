package com.fashionmall.user.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.response.DeliveryAddressResponseDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.entity.DeliveryAddress;
import com.fashionmall.user.repository.DeliveryAddressRepository;
import com.fashionmall.user.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fashionmall.user.entity.Favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "favoriteService")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final FavoriteRepository favoriteRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final ModuleApiUtil moduleApiUtil;


    // DeliveryAddress
    @Override
    @Transactional
    public Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId) {
        // 회원 인증

        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .userId(userId)
                .zipCode(deliveryAddressRequestDto.getZipcode())
                .roadAddress(deliveryAddressRequestDto.getRoadAddress())
                .build();
        deliveryAddressRepository.save(deliveryAddress);

        return deliveryAddress.getId();
    }

    @Override
    @Transactional
    public List<DeliveryAddressResponseDto> getDeliveryAddress (Long userId) {
        // 회원 인증

        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByUserId(userId);

        // DeliveryAddress 객체들을 DeliveryAddressResponseDto로 변환하여 리스트로 저장
        List<DeliveryAddressResponseDto> deliveryAddressResponseDtos = deliveryAddresses.stream()
                .map(deliveryAddress -> DeliveryAddressResponseDto.builder()
                        .id(deliveryAddress.getId())
                        .zipCode(deliveryAddress.getZipCode())
                        .roadAddress(deliveryAddress.getRoadAddress())
                        .build())
                .collect(Collectors.toList());

        return deliveryAddressResponseDtos;
    }

    // Favorite
    @Override
    @Transactional
    public FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId) {

        // 회원 인증

        List<LikeItemListResponseDto> itemInfos = moduleApiUtil.itemInfo(itemId, userId);
        LikeItemListResponseDto itemInfo = itemInfos.stream()
                .filter(info -> info.getItemInfo().getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_ITEM_ID));

        Favorite existingFavorite = favoriteRepository.findByItemId(itemId);

        if (existingFavorite != null) {  // DB에 itemId가 존재하는 경우
            if (favoriteRequestDto.isSelected()) {
                throw new CustomException(ErrorResponseCode.DUPLICATE_TRUE);
            } else {
                favoriteRepository.delete(existingFavorite);
                return FavoriteResponseDto.from(buildFavorite(itemInfo.getItemInfo().getId(), favoriteRequestDto.isSelected(), userId));
            }
        } else {  // DB에 itemId가 존재하지 않는 경우
            if (favoriteRequestDto.isSelected()) {
                Favorite newFavorite = buildFavorite(itemInfo.getItemInfo().getId(), favoriteRequestDto.isSelected(), userId);
                favoriteRepository.save(newFavorite);
                return FavoriteResponseDto.from(newFavorite);
            } else {
                throw new CustomException(ErrorResponseCode.DUPLICATE_FALSE);
            }
        }
    }

    @Override
    @Transactional
    public PageInfoResponseDto <LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId) {

        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        int totalCount = favoriteRepository.countByUserId(userId);

        List<LikeItemListResponseDto> itemInfo = moduleApiUtil.itemInfo(itemId, userId);

        return PageInfoResponseDto.of(pageRequest, itemInfo, totalCount);

    }

    private Favorite buildFavorite(Long itemId, boolean isSelected, Long userId) {
        return Favorite.builder()
                .itemId(itemId)
                .isSelected(isSelected)
                .userId(userId)
                .build();
    }
}
