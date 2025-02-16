package com.example.mealplannerapp.search.countries.models;

import com.example.mealplannerapp.search.ingedients.models.Ingredient;

import java.util.List;

public interface CountriesCallBack {

    public void onSuccessResult(List<Country> countries);

    public void onFailureResult(String errorMsg);
}
