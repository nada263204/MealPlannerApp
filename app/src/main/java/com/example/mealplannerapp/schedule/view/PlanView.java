package com.example.mealplannerapp.schedule.view;

import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.List;

public interface PlanView {
    void showMealsForDate(List<ScheduledMeal> meals);
    void showError(String message);
}
