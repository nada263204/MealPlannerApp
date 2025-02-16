package com.example.mealplannerapp.search.countries.models;

import java.util.List;

public class CountryResponse {
    private List<Country> meals;

    public List<Country> getCountries() {
        return meals;
    }

    public void setMeals(List<Country> countries) {
        this.meals = meals;
    }
}
