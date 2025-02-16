package com.example.mealplannerapp.search.countries.view;

import com.example.mealplannerapp.search.countries.models.Country;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;

import java.util.List;

public interface CountriesView {
    public void showCountries(List<Country> countries);
    public void showErrMsg(String error);
}
