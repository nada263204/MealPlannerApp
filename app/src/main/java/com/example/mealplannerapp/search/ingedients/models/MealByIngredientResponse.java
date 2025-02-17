package com.example.mealplannerapp.search.ingedients.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class MealByIngredientResponse {
    @SerializedName("meals")
    private List<MealByIngredient> meals;

    public List<MealByIngredient> getMeals() {
        return meals;
    }

    public void setMeals(List<MealByIngredient> meals) {
        this.meals = meals;
    }
}

