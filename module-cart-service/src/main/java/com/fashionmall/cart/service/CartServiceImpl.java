package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartCalculateRequestDto;
import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.cart.dto.response.CartCalculateResponseDto;
import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.entity.Cart;
import com.fashionmall.cart.repository.CartRepository;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailResponseDto;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "cartService")
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    @Transactional
    public List<Long> createCart (CartRequestDto cartRequestDto, Long userId) {

        // 회원 여부 인증

        //카트에 들어있는지 확인
        List<Long> cartIds = new ArrayList<>();

        for (CartRequestDto.CartRequestDtoList cartRequestDtoList : cartRequestDto.getCartRequestDtoList()) {

            Cart checkCart = cartRepository.findByItemDetailIdAndUserId(cartRequestDtoList.getItemDetailId(), userId);
            if (checkCart != null) {
                throw new CustomException(ErrorResponseCode.DUPLICATE_CART_DETAIL_ID);
            }

            ItemDetailResponseDto itemDetail = moduleApiUtil.getItemDetail(cartRequestDtoList.getItemDetailId());

            Cart cart = cartRequestDtoList.toEntity(userId, itemDetail.getImageId(), itemDetail.getPrice(), itemDetail.getName());

            cartRepository.save(cart);

            cartIds.add(cart.getId());
        }

        return cartIds;
    }

    @Override
    @Transactional
    public CartUpdateResponseDto updateCart (Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId) {

        // 회원 여부
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

        // 회원 인증
        Cart cart = findByIdAndUserId(cartId, userId);
        cartRepository.deleteById(cart.getId());

        return cartId;

    }

    public Cart findByIdAndUserId (Long cartId, Long userId) {
        return cartRepository.findByIdAndUserId(cartId, userId).orElseThrow(()-> new CustomException (ErrorResponseCode.WRONG_CART_ID));
    }

    @Override
    @Transactional
    public List <ItemDetailDto> getItemDetailFromCartApi (Long userId) {
        // 유저 검증
        return cartRepository.getItemDetailFromCartApi(userId);
    }

    @Override
    @Transactional
    public List<CartResponseDto> getCartList(Long userId) {

        // 회원 여부 인증

        List<Cart> carts = cartRepository.findByUserId(userId);
        Cart cart = carts.get(0);

        // gateway로 연결예정
//        String imageUrl = moduleApiUtil.getImageApi(cart.getImageId());
        String imageUrl = "이미지 Id / Url 중 뭐가 필요할까?";

        return cartRepository.getCartList(userId, imageUrl);
    }

    @Override
    @Transactional
    public List<CartCalculateResponseDto> calculateCart(CartCalculateRequestDto cartCalculateRequestDto, Long userId) {

        // 회원여부 인증

        List<CartCalculateResponseDto> responseDtoList = new ArrayList<>();

        for (CartCalculateRequestDto.CartItem cartItem : cartCalculateRequestDto.getItems()) {

            Cart cart = cartRepository.findByIdAndUserId(cartItem.getId(), userId)
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.WRONG_CART_ID));

            CartCalculateResponseDto responseDto = CartCalculateResponseDto.of(cart.getId(), cart.getPrice() * cart.getQuantity());
            responseDtoList.add(responseDto);

        }

        return responseDtoList;

    }

}
