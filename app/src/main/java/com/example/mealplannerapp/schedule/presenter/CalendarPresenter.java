package com.example.mealplannerapp.schedule.presenter;


import com.example.mealplannerapp.schedule.view.CalendarView;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.List;

public interface CalendarPresenter {
    void attachView(CalendarView view);
    void detachView();
    void loadMealsForDate(String date);
    void loadMealsFromFirestore(String date);
    void deleteScheduledMeal(ScheduledMeal meal);
    List<ScheduledMeal> filterMealsByType(List<ScheduledMeal> meals, String type, String date);

}

