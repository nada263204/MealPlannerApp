package com.example.mealplannerapp.meal.models;

import java.util.List;

public interface MealByCallback {
    public void onSuccessResult(List<MealBy> meals);

    public void onFailureResult(String errorMsg);
}
