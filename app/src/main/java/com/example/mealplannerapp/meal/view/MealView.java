package com.example.mealplannerapp.meal.view;

import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;

import java.util.List;

public interface MealView {
    public void showMeal(List<Meal> Meals);
    public void showErrMsg(String error);
}
