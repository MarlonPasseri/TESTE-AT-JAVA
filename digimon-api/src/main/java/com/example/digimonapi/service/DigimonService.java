package com.example.digimonapi.service;

import com.example.digimonapi.model.DigimonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DigimonService {

    @Value("${digimon.api.url}")
    private String digimonApiUrl;

    private final RestTemplate restTemplate;

    public DigimonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<DigimonResponse> getDigimon(String name) {
        try {
            String apiUrl = String.format("%s/%s", digimonApiUrl, name);
            ResponseEntity<DigimonResponse> response =
                    restTemplate.getForEntity(apiUrl, DigimonResponse.class);
            log.info("External API Status Code: {}", response.getStatusCodeValue());
            return response;
        } catch (Exception e) {
            log.error("Error while fetching Digimon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
