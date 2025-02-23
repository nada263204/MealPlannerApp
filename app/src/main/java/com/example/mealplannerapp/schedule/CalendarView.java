package com.example.mealplannerapp.schedule;

import java.util.List;

public interface CalendarView {
    void showMealsForDate(List<ScheduledMeal> meals);
    void showError(String message);
}
