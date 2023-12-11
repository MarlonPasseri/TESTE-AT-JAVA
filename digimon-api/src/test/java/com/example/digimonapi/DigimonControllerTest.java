package com.example.digimonapi;

import com.example.digimonapi.model.DigimonRequest;
import com.example.digimonapi.model.DigimonResponse;
import com.example.digimonapi.service.DigimonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DigimonController.class)
class DigimonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DigimonService digimonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateDigimon() throws Exception {
        DigimonRequest digimonRequest = new DigimonRequest();
        digimonRequest.setName("Agumon");
        digimonRequest.setLevel(5);
        digimonRequest.setArrayData(new Object[]{"data1", "data2"});

        mockMvc.perform(post("/api/digimon/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(digimonRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Digimon created successfully!"));
    }

    @Test
    void testDeleteDigimon() throws Exception {
        mockMvc.perform(delete("/api/digimon/delete/Agumon"))
                .andExpect(status().isOk())
                .andExpect(content().string("Digimon deleted successfully!"));
    }

    @Test
    void testUpdateDigimon() throws Exception {
        DigimonRequest digimonRequest = new DigimonRequest();
        digimonRequest.setName("Agumon");
        digimonRequest.setLevel(6);
        digimonRequest.setArrayData(new Object[]{"data1", "data2"});

        mockMvc.perform(put("/api/digimon/update/Agumon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(digimonRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Digimon updated successfully!"));
    }

    @Test
    void testGetDigimon() throws Exception {
        DigimonResponse digimonResponse = new DigimonResponse();
        digimonResponse.setName("Agumon");
        digimonResponse.setLevel("Rookie");
        digimonResponse.setImg("agumon.jpg");

        when(digimonService.getDigimon(anyString())).thenReturn(ResponseEntity.ok(digimonResponse));

        mockMvc.perform(get("/api/digimon/get")
                        .param("name", "Agumon")
                        .param("param1", "value1")
                        .param("param2", "value2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Agumon"))
                .andExpect(jsonPath("$.level").value("Rookie"))
                .andExpect(jsonPath("$.img").value("agumon.jpg"));
    }

    // Outros testes conforme necessário...
}
