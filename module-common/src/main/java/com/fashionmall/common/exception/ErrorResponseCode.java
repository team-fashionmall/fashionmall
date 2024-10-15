package com.fashionmall.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode {

    //공통 코드
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "필수 값을 입력해 주세요"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),
    OUT_OF_STOCK(HttpStatus.CONFLICT, "재고가 부족합니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"해당 요청을 찾을 수 없습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    //커스텀 코드
    ORDER_NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "유효하지 않은 쿠폰입니다"),
    ORDER_NOT_FOUND_BILLING_KEY(HttpStatus.NOT_FOUND, "유효하지 않은 결제 수단 입니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임 입니다."),
    DUPLICATE_COUPON_NAME(HttpStatus.CONFLICT, "이미 존재하는 쿠폰명 입니다."),
    JWT_NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "유효하지 않은 토큰입니다."),

    // Item
    WRONG_ITEM_ID(HttpStatus.BAD_REQUEST, "상품 관련 ID를 다시 확인해주세요"),
    WRONG_CATEGORY_ID(HttpStatus.BAD_REQUEST, "카테고리 관련 ID를 다시 확인해주세요"),
    WRONG_RATE(HttpStatus.BAD_REQUEST, "정률(%)에 맞는 값을 입력해주세요."),
    WRONG_AMOUNT(HttpStatus.BAD_REQUEST, "정액(원)에 맞는 값을 입력해주세요.");


    private final HttpStatus status;
    private final String message;
}
