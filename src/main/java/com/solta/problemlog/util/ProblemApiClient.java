package com.solta.problemlog.util;

import com.solta.problemlog.dto.response.ProblemResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public ProblemResponseDTO fetchProblemById(int problemId){
        String url = String.format("%s/problem/show?problemId=%d", apiUrl, problemId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("x-solvedac-language", "ko");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProblemResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ProblemResponseDTO.class
        );

         return response.getBody();
    }
}
