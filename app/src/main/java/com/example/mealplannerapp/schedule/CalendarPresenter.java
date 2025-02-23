package com.example.mealplannerapp.schedule;


import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

public interface CalendarPresenter {
    void attachView(CalendarView view);
    void detachView();
    void loadMealsForDate(String date);
    List<ScheduledMeal> filterMealsByType(List<ScheduledMeal> meals, String type, String date);

}

