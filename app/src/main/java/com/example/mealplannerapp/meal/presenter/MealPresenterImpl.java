package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.meal.view.MealView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealPresenterImpl implements MealPresenter, MealCallback {
    private MealView _view;
    private Repository _repo;

    public MealPresenterImpl(MealView view,Repository repo){
        this._view =view;
        this._repo=repo;
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        _view.showMeal(meals);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }

    @Override
    public void getMeals() {
        _repo.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> _view.showMeal(meals),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }
}
