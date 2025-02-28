package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartCalculateRequestDto;
import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartCalculateResponseDto;
import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.cart.entity.Cart;
import com.fashionmall.cart.repository.CartRepository;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.*;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "cartService")
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    @Transactional
    public List<Long> createCart(CartRequestDto cartRequestDto, Long userId) {

        moduleApiUtil.confirmUserInfoApi(userId);

        List<Long> cartIds = new ArrayList<>();

        for (CartRequestDto.CartRequestDtoList cartRequestDtoList : cartRequestDto.getCartRequestDtoList()) {

            Cart checkCart = cartRepository.findByItemDetailIdAndUserId(cartRequestDtoList.getItemDetailId(), userId);
            if (checkCart != null) {
                throw new CustomException(ErrorResponseCode.DUPLICATE_CART_DETAIL_ID);
            }

            ItemDetailResponseDto itemDetail = moduleApiUtil.getItemDetailApi(cartRequestDtoList.getItemDetailId());

            Cart cart = cartRequestDtoList.toEntity(userId, itemDetail.getImageId(), itemDetail.getPrice(), itemDetail.getName());

            cartRepository.save(cart);

            cartIds.add(cart.getId());
        }

        return cartIds;
    }

    @Override
    @Transactional
    public CartUpdateResponseDto updateCart(Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId) {

        moduleApiUtil.confirmUserInfoApi(userId);

        Cart cart = findByIdAndUserId(cartId, userId);

        if (cartUpdateRequestDto.getQuantity() > 0) {
            cart.updateQuantity(cartUpdateRequestDto.getQuantity());
        }

        if (cartUpdateRequestDto.getIsSelected() != null) {
            cart.updateIsSelected(cartUpdateRequestDto.getIsSelected());
        }

        return CartUpdateResponseDto.from(cart);
    }

    @Override
    @Transactional
    public Long deleteCart(Long cartId, Long userId) {

        moduleApiUtil.confirmUserInfoApi(userId);

        Cart cart = findByIdAndUserId(cartId, userId);
        cartRepository.deleteById(cart.getId());

        return cartId;

    }

    public Cart findByIdAndUserId(Long cartId, Long userId) {
        return cartRepository.findByIdAndUserId(cartId, userId).orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_CART_ID));
    }

    @Override
    @Transactional
    public List<ItemDetailDto> getItemDetailFromCartApi(Long userId) {
        return cartRepository.getItemDetailFromCartApi(userId);
    }

    @Override
    @Transactional
    public List<CartResponseDto> getCartList(Long userId) {

        moduleApiUtil.confirmUserInfoApi(userId);

        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

        List<Cart> carts = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_USER_ID));

        if (carts.isEmpty()) {
            return cartResponseDtoList;
        }

        List<Long> itemDetailIds = carts.stream()
                .map(Cart::getItemDetailId)
                .collect(Collectors.toList());

        List<Long> imageIds = carts.stream()
                .map(Cart::getImageId)
                .collect(Collectors.toList());

        List<ItemPriceNameDto> discountPrices = moduleApiUtil.getItemPriceAndNameApi(itemDetailIds);
        Map<Long, ItemDetailInfoDto> itemDetailInfoDtoMap = moduleApiUtil.getItemDetailInfoApi(itemDetailIds)
                .stream()
                .collect(Collectors.toMap(ItemDetailInfoDto::getId, op -> op));

        Map<Long, ImageDataDto> imageMap = moduleApiUtil.getImageApi(imageIds)
                .stream()
                .collect(Collectors.toMap(ImageDataDto::getId, op -> op, (t, t2) -> t2));


        for (Cart cart : carts) {
            ItemDetailInfoDto detailInfo = itemDetailInfoDtoMap.get(cart.getItemDetailId());
            int discountPrice = 0;

            for (ItemPriceNameDto response : discountPrices) {
                if (response.getItemDetailId() == cart.getItemDetailId()) {
                    discountPrice = response.getPrice();
                }
            }

            cartResponseDtoList.add(
                    CartResponseDto.builder()
                            .id(cart.getId())
                            .itemId(detailInfo.getItemId())
                            .itemDetailId(cart.getItemDetailId())
                            .itemName(detailInfo.getItemDetailName())
                            .imageUrl(imageMap.get(cart.getImageId()).getUrl())
                            .quantity(cart.getQuantity())
                            .price(cart.getPrice())
                            .discountPrice(discountPrice)
                            .isSelected(cart.isSelected())
                            .build()
            );
        }

        return cartResponseDtoList;
    }

    @Override
    @Transactional
    public List<CartCalculateResponseDto> calculateCart(CartCalculateRequestDto cartCalculateRequestDto, Long userId) {

        moduleApiUtil.confirmUserInfoApi(userId);

        List<CartCalculateResponseDto> responseDtoList = new ArrayList<>();

        for (CartCalculateRequestDto.CartItem cartItem : cartCalculateRequestDto.getItems()) {

            Cart cart = cartRepository.findByIdAndUserId(cartItem.getId(), userId)
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_CART_ID));

            List<ItemPriceNameDto> discountPrices = moduleApiUtil.getItemPriceAndNameApi(Collections.singletonList(cart.getItemDetailId()));

            int quantity = cart.getQuantity();
            int finalPrice = 0;

            if (!discountPrices.isEmpty()) {
                for (ItemPriceNameDto discountPriceDto : discountPrices) {
                    if (discountPriceDto.getItemDetailId() == cart.getItemDetailId()) {
                        int discountPrice = discountPriceDto.getPrice();
                        finalPrice = discountPrice * quantity;
                        break;
                    }
                }
            }

            if (finalPrice == 0) {
                finalPrice = cart.getPrice() * quantity;
            }

            CartCalculateResponseDto responseDto = CartCalculateResponseDto.of(cart.getId(), finalPrice);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public List<CartItemDto> getIsSelectedItemApi(Long userId) {

        List<Cart> carts = cartRepository.findByUserIdAndIsSelected(userId, true)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_USER_ID));

        List<CartItemDto> cartItemDtoList = new ArrayList<>();

        for (Cart cart : carts) {

            Long id = cart.getItemDetailId();
            int quantity = cart.getQuantity();

            CartItemDto cartItemDto = new CartItemDto(id, quantity);
            cartItemDtoList.add(cartItemDto);
        }

        return cartItemDtoList;
    }

    @Override
    @Transactional
    public void deleteOrderedItemApi(Long userId, List<Long> itemDetailIds) {
        for (Long id : itemDetailIds) {
            Cart cart = cartRepository.findByItemDetailIdAndUserId(id, userId);
            cartRepository.delete(cart);
        }
    }
}
