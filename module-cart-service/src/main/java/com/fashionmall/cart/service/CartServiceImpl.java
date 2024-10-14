package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.cart.entity.Cart;
import com.fashionmall.cart.repository.CartRepository;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "cartService")
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public String createCart (CartRequestDto cartRequestDto, Long userId, int price) {

        // 회원 여부 인증
        Cart checkCart = cartRepository.findByItemDetailIdAndUserId(cartRequestDto.getItemDetailId(), userId);
        if (checkCart != null) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_CART_DETAIL_ID);
        }

        // price는 Msa로 가져올 예정
        Cart cart = cartRequestDto.toEntity(userId, price);

        cartRepository.save(cart);

        return "cartId : " + cart.getId();
    }

    @Override
    @Transactional
    public CartUpdateResponseDto updateCart (Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId) {

        // 회원 여부
        Cart cart = cartRepository.findByIdAndUserId(cartId, userId)
                .orElseThrow(()-> new CustomException (ErrorResponseCode.WRONG_CART_ID));


        if (cartUpdateRequestDto.getQuantity() > 0) {
            cart.updateQuantity(cartUpdateRequestDto.getQuantity());
        }

        if (cartUpdateRequestDto.getIsSelected() != null) {
            cart.updateIsSelected(cartUpdateRequestDto.getIsSelected());
        }

        return CartUpdateResponseDto.from(cart);
    }

}
