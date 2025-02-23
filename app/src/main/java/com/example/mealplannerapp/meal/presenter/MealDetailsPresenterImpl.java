package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.meal.view.MealView;
import com.example.mealplannerapp.schedule.ScheduledMeal;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, MealCallback {
    private final MealView _view;
    private final Repository _repo;

    public MealDetailsPresenterImpl(MealView view, Repository repo) {
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
    public void addMealToCalendar(Meal meal, String mealType, String date) {
        ScheduledMeal scheduledMeal = new ScheduledMeal(date, mealType, meal);
        _repo.insertMealToCalendar(scheduledMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> _view.showErrMsg("Meal added to calendar!"),
                        throwable -> _view.showErrMsg("Error: " + throwable.getMessage()));
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        if (!meals.isEmpty()) {
            _view.showMeal(meals);
        } else {
            _view.showErrMsg("No meal found.");
        }
    }



    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }
}
