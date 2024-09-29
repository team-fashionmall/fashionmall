package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.entity.Cart;
import com.fashionmall.cart.entity.CartStatusEnum;
import com.fashionmall.cart.repository.CartRepository;
import jakarta.validation.Valid;
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
    public String createCart (CartRequestDto cartRequestDto) {

        // 회원 여부 인증

        Cart cart = Cart.builder()
                .itemDetailId(cartRequestDto.getItemDetailId())
                .quantity(cartRequestDto.getQuantity())
                .build();
        cartRepository.save(cart);

        return "장바구니에 담겼습니다";
    }

}
