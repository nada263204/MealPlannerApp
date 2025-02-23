package com.example.mealplannerapp.schedule;

import android.util.Log;

import com.example.mealplannerapp.data.repo.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CalendarPresenterImpl implements CalendarPresenter {
    private static final String TAG = "CalendarPresenterImpl";
    private CalendarView view;
    private final Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public CalendarPresenterImpl(Repository repository,CalendarView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void attachView(CalendarView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    @Override
    public void loadMealsForDate(String date) {
        disposables.add(
                repository.getMealsByDate(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    if (meals != null && !meals.isEmpty()) {
                                        Log.d(TAG, "Loaded meals: " + meals.size());
                                        view.showMealsForDate(meals);
                                    } else {
                                        Log.d(TAG, "No meals found for date: " + date);
                                        view.showError("No meals found for this day.");
                                    }
                                },
                                throwable -> {
                                    Log.e(TAG, "Error fetching meals", throwable);
                                    view.showError("Failed to load meals.");
                                }
                        )
        );
    }


    @Override
    public List<ScheduledMeal> filterMealsByType(List<ScheduledMeal> meals, String type, String date) {
        List<ScheduledMeal> filteredMeals = new ArrayList<>();
        for (ScheduledMeal meal : meals) {
            if (meal.getMealType().equalsIgnoreCase(type) && meal.getDate().equals(date)) {
                filteredMeals.add(meal);
            }
        }
        return filteredMeals;
    }

}

