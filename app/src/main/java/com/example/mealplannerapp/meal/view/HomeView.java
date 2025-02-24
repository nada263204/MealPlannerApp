package com.example.mealplannerapp.meal.view;

import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

public interface HomeView {
    public void showMeal(List<Meal> Meals);
    public void showErrMsg(String error);
}
