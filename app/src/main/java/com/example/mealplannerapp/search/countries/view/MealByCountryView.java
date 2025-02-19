package com.example.mealplannerapp.search.countries.view;

import com.example.mealplannerapp.meal.models.MealBy;

import java.util.List;

public interface MealByCountryView {
    void showMeals(List<MealBy> meals);
    void showError(String error);
}
