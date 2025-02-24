package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

public interface MealDetailsPresenter {
    void getMealById(String mealId);
    void addToFav(Meal meal);
    void addMealToCalendar(Meal meal, String mealType, String date);
    void getFavoriteMeals();
    void addMealToFirestore(Meal meal);
    void addPlannedMealToFirestore(ScheduledMeal scheduledMeal);
}
