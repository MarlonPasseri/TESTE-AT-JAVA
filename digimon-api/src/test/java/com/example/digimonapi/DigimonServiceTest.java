package com.example.digimonapi;

import com.example.digimonapi.model.DigimonResponse;
import com.example.digimonapi.service.DigimonService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DigimonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DigimonService digimonService;

    @Test
    void testGetDigimon() {
        DigimonResponse mockDigimon = new DigimonResponse();
        mockDigimon.setName("Agumon");
        mockDigimon.setLevel("Rookie");
        mockDigimon.setImg("agumon.jpg");

        when(restTemplate.getForEntity(anyString(), eq(DigimonResponse.class)))
                .thenReturn(ResponseEntity.ok(mockDigimon));

        ResponseEntity<DigimonResponse> result = digimonService.getDigimon("Agumon");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Agumon", result.getBody().getName());
        assertEquals("Rookie", result.getBody().getLevel());
        assertEquals("agumon.jpg", result.getBody().getImg());
    }

    @Test
    void testGetDigimonError() {
        when(restTemplate.getForEntity(anyString(), eq(DigimonResponse.class)))
                .thenThrow(new RuntimeException("External API Error"));

        ResponseEntity<DigimonResponse> result = digimonService.getDigimon("Agumon");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }


}
