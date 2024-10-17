package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "cartService")
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Override
    @Transactional
    public List<String> createCart (CartRequestDto cartRequestDto, Long userId) {

        // 회원 여부 인증
        //카트에 들어있는지 확인
        List<String> cartIds = new ArrayList<>();

        for (CartRequestDto.CartRequestDtoList cartRequestDtoList : cartRequestDto.getCartRequestDtoList()) {
            Cart checkCart = cartRepository.findByItemDetailIdAndUserId(cartRequestDtoList.getItemDetailId(), userId);
            if (checkCart != null) {
                throw new CustomException(ErrorResponseCode.DUPLICATE_CART_DETAIL_ID);
            }

            ItemDetailResponseDto itemDetail = moduleApiUtil.getItemDetail(cartRequestDtoList.getItemDetailId());

            Cart cart = cartRequestDtoList.toEntity(userId, itemDetail.getPrice(), itemDetail.getName());

            cartRepository.save(cart);

            cartIds.add("cartId : " + cart.getId());
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
    public String deleteCart(Long cartId, Long userId) {

        // 회원 인증
        Cart cart = findByIdAndUserId(cartId, userId);
        cartRepository.deleteById(cart.getId());

        return "해당 상품이 삭제되었습니다. cartId : " + cartId;

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

}
