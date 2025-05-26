package org.bebriki.gateway.application.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProxyRestService {
    public <T> ResponseEntity<T> proxyRequest(
            Supplier<RestClient.ResponseSpec> requestSpec,
            Class<T> responseType) {
        try {
            return requestSpec.get().toEntity(responseType);
        } catch (RestClientResponseException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(null);
        }
    }

    public <T> ResponseEntity<T> proxyRequest(
            Supplier<RestClient.ResponseSpec> requestSpec,
            ParameterizedTypeReference<T> responseType) {
        try {
            return requestSpec.get().toEntity(responseType);
        } catch (RestClientResponseException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(null);
        }
    }
}