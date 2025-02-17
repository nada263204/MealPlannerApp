package com.example.mealplannerapp.meal.models;

import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.models.MealByIngredient;

public interface OnMealClickListener {

        void OnMealClick(MealByIngredient meal);

}
