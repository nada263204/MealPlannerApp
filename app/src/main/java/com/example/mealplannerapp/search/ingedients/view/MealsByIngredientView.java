package com.example.mealplannerapp.search.ingedients.view;

import com.example.mealplannerapp.search.ingedients.models.MealByIngredient;

import java.util.List;

public interface MealsByIngredientView {
    void showMeals(List<MealByIngredient> meals);
    void showError(String error);
}
