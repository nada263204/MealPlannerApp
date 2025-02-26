package com.example.mealplannerapp.meal.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class MealByResponse {
    @SerializedName("meals")
    private List<MealBy> meals;

    public List<MealBy> getMeals() {
        return meals;
    }

    public void setMeals(List<MealBy> meals) {
        this.meals = meals;
    }
}

