package com.example.mealplannerapp.meal.models;

import java.util.ArrayList;
import java.util.List;

public class MealResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals != null ? meals : new ArrayList<>();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "MealResponse{" +
                "meals=" + meals +
                '}';
    }
}
