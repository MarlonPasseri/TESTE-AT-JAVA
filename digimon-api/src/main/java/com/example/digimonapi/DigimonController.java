package com.example.digimonapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.digimonapi.model.DigimonRequest;
import com.example.digimonapi.model.DigimonResponse;
import com.example.digimonapi.service.DigimonService;

@Slf4j
@RestController
@RequestMapping("/api/digimon")
public class DigimonController {

    @Autowired
    private DigimonService digimonService;



    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Digimon API is up and running!");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDigimon(@RequestBody DigimonRequest digimonRequest) {
        log.info("Received POST request: {}", digimonRequest);
        // Lógica de processamento...
        return ResponseEntity.ok("Digimon created successfully!");
    }



    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteDigimon(@PathVariable String name) {
        log.info("Received DELETE request for Digimon: {}", name);
        // Lógica de remoção...
        return ResponseEntity.ok("Digimon deleted successfully!");
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateDigimon(
            @PathVariable String name, @RequestBody DigimonRequest digimonRequest) {
        log.info("Received PUT request for Digimon {}: {}", name, digimonRequest);
        // Lógica de atualização...
        return ResponseEntity.ok("Digimon updated successfully!");
    }

    @GetMapping("/get")
    public ResponseEntity<DigimonResponse> getDigimon(
            @RequestParam("name") String name,
            @RequestParam(value = "param1", required = false) String param1,
            @RequestParam(value = "param2", required = false) String param2) {
        try {
            ResponseEntity<DigimonResponse> response = digimonService.getDigimon(name);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (Exception e) {
            log.error("Error while fetching Digimon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    }