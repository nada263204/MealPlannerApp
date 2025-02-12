package com.example.mealplannerapp;

import java.util.Map;

public class FlagResponse {
    private Map<String, String> flags;

    public String getFlagUrl() {
        return flags != null ? flags.get("png") : null;
    }
}
