package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.meal.view.MealView;

import java.util.List;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, MealCallback {
    private MealView _view;
    private Repository _repo;

    public MealDetailsPresenterImpl(MealView view, Repository repo) {
        this._view = view;
        this._repo = repo;
    }

    @Override
    public void getMealById(String mealId) {
        _repo.getMealById(mealId, this);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        _view.showMeal(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }
}
