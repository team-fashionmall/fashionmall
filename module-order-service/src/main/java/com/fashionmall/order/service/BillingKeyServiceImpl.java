package com.fashionmall.order.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.BillingKeyResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.BillingKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BillingKeyServiceImpl implements BillingKeyService {

    private final BillingKeyRepository billingKeyRepository;
    private final IamPortClient iamPortClient;

    @Transactional
    @Override
    public Long issueBillingKey(BillingKeyRequestDto billingKeyRequestDto) {

        String customerUid = UUID.randomUUID().toString();

        Map<String, String> request = Map.of(
                "pg", "nice",
                "card_number", billingKeyRequestDto.getCardNumber(),
                "expiry", billingKeyRequestDto.getExpiry(),
                "birth", billingKeyRequestDto.getBirth(),
                "pwd_2digit", billingKeyRequestDto.getPwd2digit());

        IamPortResponseDto<BillingKeyResponseDto> IamPortResponse = iamPortClient.getBillingKey(customerUid, request);

        String cardName = IamPortResponse.getResponse().getCardName();
        String cardType = IamPortResponse.getResponse().getCardType();

        if (cardType.equals("0")) {
            cardType = "신용카드";
        } else if (cardType.equals("1")) {
            cardType = "체크카드";
        }


        BillingKey billingKey = billingKeyRequestDto.toEntity(cardName, cardType, customerUid);
        billingKeyRepository.save(billingKey);

        return billingKey.getId();
    }

    @Override
    public PageInfoResponseDto<UserBillingKeyResponseDto> getUserBillingKeyList(Long userId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);

        return billingKeyRepository.findBillingKeyByUserId(userId, pageRequest);
    }

    @Transactional
    @Override
    public Long deleteBillingKey(Long billingKeyId) {

        String customerUid = billingKeyRepository.findCustomerUidById(billingKeyId);

        iamPortClient.deleteBillingKey(customerUid);

        billingKeyRepository.deleteById(billingKeyId);

        return billingKeyId;
    }
}
