package com.fashionmall.item.dto.request;

import com.fashionmall.item.entity.MainCategoryEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @NotBlank (message = "상품명을 입력해주세요")
    private String itemName; // 상품명
    @NotBlank (message = "상품 활성화 여부를 입력해주세요(enum)")
    private String itemState; // 상품 재고 현황

    @NotBlank (message = "상품의 색을 입력해주세요")
    private String color; // 색
    @NotBlank (message = "상품의 사이즈를 입력해주세요")
    private String size; // 사이즈

    @NotBlank (message = "상품 상세명을 입력해주세요")
    private String itemDetailName; // 상품 상세명
    @Positive (message = "상품 가격을 입력해주세요. 상품 가격은 0보다 커야합니다")
    private int itemPrice; // 가격
    @Min (value = 0, message = "재고 수량을 입력해주세요")
    private int quantity; // 재고 현황
    @NotBlank (message = "상품 전시 상태 여부를 입력해주세요(enum)")
    private String itemDetailState; // 상품 전시 상태

    @NotNull (message = "상위 카테고리를 입력해주세요(enum)")
    private MainCategoryEnum mainCategory; // 상위 카테고리
    @NotBlank (message = "하위 카테고리를 입력해주세요(enum)")
    private String subCategory; // 하위 카테고리
    @Positive (message = "상위 카테고리 ID를 입력해주세요(DB확인)")
    private Long parentId; // 상위 카테고리 id

    /*// presigned url 후 연결
    private String mainImage;
    private String detailImage;*/

}
