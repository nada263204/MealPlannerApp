package com.example.mealplannerapp.search.ingedients.view;

import com.example.mealplannerapp.meal.models.MealBy;

import java.util.List;

public interface MealsByIngredientView {
    void showMeals(List<MealBy> meals);
    void showError(String error);
}
