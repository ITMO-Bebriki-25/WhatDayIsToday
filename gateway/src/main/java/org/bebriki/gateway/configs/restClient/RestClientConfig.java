package org.bebriki.gateway.configs.restClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${events.url}")
    private String eventsUrl;

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl(eventsUrl)
                .build();
    }
}