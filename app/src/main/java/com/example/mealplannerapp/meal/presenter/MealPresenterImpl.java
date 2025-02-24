package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.view.MealView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealPresenterImpl implements MealPresenter {
    private MealView _view;
    private Repository _repo;

    public MealPresenterImpl(MealView view,Repository repo){
        this._view =view;
        this._repo=repo;
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
