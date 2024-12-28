package com.solta.problemlog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProblemApiClient {
    private final RestTemplate restTemplate;

    @Value("${spring.solved.api.url}")
    private String apiUrl;

    public ProblemApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
