package com.shukldi.ecommerce.product;

import com.shukldi.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequests, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<List<PurchaseResponse>>() {};
        ResponseEntity<List<PurchaseResponse>> responseEntity =
                restTemplate.exchange(
                        productUrl + "/purchase",
                        HttpMethod.POST,
                        requestEntity,
                        responseType
                );
        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("Failed to purchase products" + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
}
