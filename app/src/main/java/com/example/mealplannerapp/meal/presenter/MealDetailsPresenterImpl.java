package com.example.mealplannerapp.meal.presenter;

import android.util.Log;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.view.HomeView;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private final HomeView _view;
    private final Repository _repo;

    public MealDetailsPresenterImpl(HomeView view, Repository repo) {
        this._view = view;
        this._repo = repo;
    }

    @Override
    public void getMealById(String mealId) {
        _repo.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> _view.showMeal(meal),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }

    @Override
    public void addToFav(Meal meal) {
        _repo.insertMealToFav(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> _view.showErrMsg("Meal added to favorites!"),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }

    @Override
    public void getFavoriteMeals() {
        _repo.getFavoriteMealsFromFirestore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> _view.showMeal(meals),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }

    @Override
    public void addMealToFirestore(Meal meal) {
        _repo.addMealToFirestore(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d("Firestore", "Meal successfully added to Firestore"),
                        error -> Log.e("Firestore", "Error adding meal to Firestore", error)
                );
    }

    @Override
    public void addMealToCalendar(Meal meal, String mealType, String date) {
        ScheduledMeal scheduledMeal = new ScheduledMeal(date, mealType, meal);
        _repo.insertMealToCalendar(scheduledMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> _view.showErrMsg("Meal added to calendar!"),
                        throwable -> _view.showErrMsg("Error: " + throwable.getMessage()));
    }

    @Override
    public void addPlannedMealToFirestore(ScheduledMeal scheduledMeal) {
        _repo.addScheduledMealToFirestore(scheduledMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d("Firestore", "Scheduled meal successfully added to Firestore"),
                        error -> Log.e("Firestore", "Error adding scheduled meal to Firestore", error)
                );
    }
}
