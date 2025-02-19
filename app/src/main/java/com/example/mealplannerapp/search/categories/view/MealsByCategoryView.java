package com.example.mealplannerapp.search.categories.view;

import com.example.mealplannerapp.meal.models.MealBy;

import java.util.List;

public interface MealsByCategoryView {
    void showMeals(List<MealBy> meals);
    void showError(String error);
}
