package com.example.mealplannerapp.search.categories.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.MealByCallback;
import com.example.mealplannerapp.search.categories.view.MealsByCategoryView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealsByCategoryPresenterImpl implements MealsByCategoryPresenter{

    private MealsByCategoryView _view;
    private Repository _repo;

    public MealsByCategoryPresenterImpl(MealsByCategoryView _view, Repository _repo) {
        this._view = _view;
        this._repo = _repo;
    }


    @Override
    public void getMealsByCategory(String category) {
        _repo.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> _view.showMeals(meal),
                        error -> _view.showError(error.getMessage())
                );
    }
}
