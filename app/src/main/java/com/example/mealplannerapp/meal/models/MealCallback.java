package com.example.mealplannerapp.meal.models;


import java.util.List;

public interface MealCallback {

    public void onSuccessResult(List<Meal> meals);

    public void onFailureResult(String errorMsg);
}
