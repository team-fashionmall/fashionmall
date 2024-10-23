package com.fashionmall.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClient webClient;

    public <T> T get(String uri, Class<T> responseType, Map<String, String> queryParam, Map<String, String> headers) {
        return webClient.get()
                .uri(uri, headers != null ? headers : Collections.emptyMap())
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T get(String uri, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> queryParam, Map<String, String> headers) {
        return webClient.get()
                .uri(uri, headers != null ? headers : Collections.emptyMap())
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T post(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.post()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T post(String uri, Object requestBody, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers) {
        return webClient.post()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T put(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.put()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T patch(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.patch()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> void patch(String uri, Object requestBody, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers) {
        webClient.patch()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T delete(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.delete()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T delete(String uri, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers) {
        return webClient.delete()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .bodyToMono(elementTypeRef)
                .block();
    }

    private Consumer<HttpHeaders> getHttpHeadersConsumer(Map<String, String> headers) {
        return httpHeaders -> {
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpHeaders.add(
                            entry.getKey(),
                            entry.getValue()
                    );

                }
            }
        };
    }
}
