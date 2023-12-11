package com.example.digimonapi.model;

import lombok.Data;

@Data
public class DigimonRequest {
    private String name;
    private int level;
    private Object[] arrayData;
}
