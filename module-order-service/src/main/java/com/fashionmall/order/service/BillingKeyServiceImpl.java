package com.fashionmall.order.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.WebClientUtil;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.BillingKeyResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.BillingKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BillingKeyServiceImpl implements BillingKeyService {

    private final WebClientUtil webClientUtil;
    private final BillingKeyRepository billingKeyRepository;
    private final IamPortClient iamPortClient;

    private final String iamPortUrl = "http://api.iamport.kr";

    @Transactional
    @Override
    public Long issueBillingKey(BillingKeyRequestDto billingKeyRequestDto) {

        String customerUid = UUID.randomUUID().toString();
        String accessToken = iamPortClient.getAccessToken();

        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json",
                HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        Map<String, String> request = Map.of("card_number", billingKeyRequestDto.getCardNumber(),
                "expiry", billingKeyRequestDto.getExpiry(),
                "pwd_2digit", billingKeyRequestDto.getPwd2digit());

        IamPortResponseDto<BillingKeyResponseDto> IamPortResponse = webClientUtil.post(
                iamPortUrl + "/subscribe/customers/" + customerUid,
                request,
                new ParameterizedTypeReference<IamPortResponseDto<BillingKeyResponseDto>>() {
                },
                headers);

        String cardName = IamPortResponse.getResponse().getCardName();
        String cardType = IamPortResponse.getResponse().getCardType();

        BillingKey billingKey = billingKeyRequestDto.toEntity(cardName, cardType);
        billingKeyRepository.save(billingKey);

        return billingKey.getId();
    }

    @Override
    public PageInfoResponseDto<UserBillingKeyResponseDto> getUserBillingKeyList(Long userId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);

        String accessToken = iamPortClient.getAccessToken();

        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json",
                HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        //DB에 저장된 billingKey의 id, cardNickname, customerUid 값 받아오기
        PageInfoResponseDto<UserBillingKeyResponseDto> dbBillingKey = billingKeyRepository.findBillingKeyDb(userId, pageRequest);

        //customer_uid 추출
        List<String> customerUids = dbBillingKey.getContent()
                .stream()
                .map(UserBillingKeyResponseDto::getCustomerUid)
                .toList();

        //queryParam에 "customer_uid[]" + customer_uid
        HashMap<String, String> queryParam = new HashMap<>();
        if (!customerUids.isEmpty()) {
            for (String customerUid : customerUids) {
                queryParam.put("customer_uid[]", customerUid);
            }
        }

        PageInfoResponseDto<UserBillingKeyResponseDto> iamPortResponse = webClientUtil.get(iamPortUrl + "/subscribe/customers",
                new ParameterizedTypeReference<PageInfoResponseDto<UserBillingKeyResponseDto>>() {
                },
                queryParam,
                headers);

        //api응답값에 id, cardNickname 삽입
        for (UserBillingKeyResponseDto apiResponse : iamPortResponse.getContent()) {
            dbBillingKey.getContent()
                    .stream()
                    .filter(db -> db.getCustomerUid().equals(apiResponse.getCustomerUid()))
                    .findFirst()
                    .ifPresent(db -> {
                        apiResponse.setId(db.getId());
                        apiResponse.setCardNickname(db.getCardNickname());
                    });
        }

        return iamPortResponse;
    }

    @Transactional
    @Override
    public Long deleteBillingKey(Long billingKeyId) {
        String accessToken = iamPortClient.getAccessToken();

        Map<String, String> headers = Map.of(
                HttpHeaders.CONTENT_TYPE, "application/json",
                HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        String customerUid = billingKeyRepository.findCustomerUidById(billingKeyId);

        webClientUtil.delete(iamPortUrl + "/subscribe/customers/" + customerUid,
                new ParameterizedTypeReference<IamPortResponseDto<UserBillingKeyResponseDto>>() {
                },
                headers);

        billingKeyRepository.deleteById(billingKeyId);

        return billingKeyId;
    }
}
