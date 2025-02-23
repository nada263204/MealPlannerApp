package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.meal.models.Meal;

public interface MealDetailsPresenter {
    void getMealById(String mealId);
    public void addToFav(Meal meal);

    void addMealToCalendar(Meal meal, String mealType, String date);


}
