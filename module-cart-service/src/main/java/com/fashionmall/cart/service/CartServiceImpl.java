package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
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
            throw new CustomException(ErrorResponseCode.BAD_REQUEST);
        }

        // price는 Msa로 가져올 예정
        Cart cart = cartRequestDto.toEntity(userId, price);

        cartRepository.save(cart);

        return "cartId : " + cart.getId();
    }

}
