package com.example.mealplannerapp.schedule.presenter;

import android.util.Log;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.schedule.view.PlanView;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CalendarPresenterImpl implements CalendarPresenter {
    private static final String TAG = "CalendarPresenterImpl";
    private PlanView view;
    private final Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public CalendarPresenterImpl(Repository repository, PlanView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void attachView(PlanView view) {
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
                                        Log.d(TAG, "Loaded meals from Room: " + meals.size());
                                        view.showMealsForDate(meals);
                                    } else {
                                        Log.d(TAG, "No meals in Room, checking Firestore...");
                                        loadMealsFromFirestore(date);
                                    }
                                },
                                throwable -> {
                                    Log.e(TAG, "Error fetching meals from Room", throwable);
                                    view.showError("Failed to load meals.");
                                }
                        )
        );
    }

    public void loadMealsFromFirestore(String date) {
        disposables.add(
                repository.getScheduledMealsByDateFromFirestore(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                firestoreMeals -> {
                                    if (firestoreMeals != null && !firestoreMeals.isEmpty()) {
                                        Log.d(TAG, "Loaded meals from Firestore: " + firestoreMeals.size());
                                        view.showMealsForDate(firestoreMeals);
                                    } else {
                                        Log.d(TAG, "No meals found in Firestore for date: " + date);
                                        view.showError("No meals found for this day.");
                                    }
                                },
                                throwable -> {
                                    Log.e(TAG, "Error fetching meals from Firestore", throwable);
                                    view.showError("Failed to load meals from Firestore.");
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

    @Override
    public void deleteScheduledMeal(ScheduledMeal meal) {
        disposables.add(
                repository.deleteScheduledMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d(TAG, "Meal deleted successfully"),
                                throwable -> Log.e(TAG, "Error deleting meal", throwable)
                        )
        );
    }

}
