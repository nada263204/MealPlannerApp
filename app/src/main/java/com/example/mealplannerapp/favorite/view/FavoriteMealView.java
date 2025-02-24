package com.example.mealplannerapp.favorite.view;

import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

public interface FavoriteMealView {
    public void showFavoriteMeals(List<Meal> meals);
    public void showErrMsg(String error);
}
