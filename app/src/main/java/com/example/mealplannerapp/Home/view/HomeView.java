package com.example.mealplannerapp.Home.view;

import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.List;

public interface HomeView {
    public void showMeal(List<Meal> Meals);
    public void showErrMsg(String error);
    void showScheduledMeals(List<ScheduledMeal> scheduledMeals);
    void showLocationMeals(List<MealBy> meals);
}
