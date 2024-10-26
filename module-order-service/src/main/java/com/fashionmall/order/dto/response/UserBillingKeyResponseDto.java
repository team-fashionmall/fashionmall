package com.fashionmall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBillingKeyResponseDto {

    private Long id;

    @JsonProperty("card_nickname")
    private String cardNickname;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("card_type")
    private String cardType;

    public UserBillingKeyResponseDto(Long id, String cardNickname, String cardName, String cardType) {
        this.id = id;
        this.cardNickname = cardNickname;
        this.cardName = cardName;
        this.cardType = cardType;
    }
}
