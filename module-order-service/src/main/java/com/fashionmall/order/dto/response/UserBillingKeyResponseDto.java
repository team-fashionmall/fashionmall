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

    @JsonProperty("customer_uid")
    private String customerUid;

    @JsonProperty("pg_provider")
    private String pgProvider;

    @JsonProperty("pg_id")
    private String pgId;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("inserted")
    private int inserted;

    @JsonProperty("updated")
    private int updated;

    public UserBillingKeyResponseDto(Long id, String cardNickname, String customerUid) {
        this.id = id;
        this.cardNickname = cardNickname;
        this.customerUid = customerUid;
    }

}
