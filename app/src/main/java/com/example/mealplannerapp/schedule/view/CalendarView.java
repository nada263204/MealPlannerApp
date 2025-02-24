package com.example.mealplannerapp.schedule.view;

import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.List;

public interface CalendarView {
    void showMealsForDate(List<ScheduledMeal> meals);
    void showError(String message);
}
