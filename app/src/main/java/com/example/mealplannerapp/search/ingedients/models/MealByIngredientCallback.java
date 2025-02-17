package com.example.mealplannerapp.search.ingedients.models;

import java.util.List;

public interface MealByIngredientCallback {
    public void onSuccessResult(List<MealByIngredient> meals);

    public void onFailureResult(String errorMsg);
}
