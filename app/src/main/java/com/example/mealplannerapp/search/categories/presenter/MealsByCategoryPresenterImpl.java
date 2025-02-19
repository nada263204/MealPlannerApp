package com.example.mealplannerapp.search.categories.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.MealByCallback;
import com.example.mealplannerapp.search.categories.view.MealsByCategoryView;

import java.util.List;

public class MealsByCategoryPresenterImpl implements MealsByCategoryPresenter{

    private MealsByCategoryView _view;
    private Repository _repo;

    public MealsByCategoryPresenterImpl(MealsByCategoryView _view, Repository _repo) {
        this._view = _view;
        this._repo = _repo;
    }


    @Override
    public void getMealsByCategory(String category) {
        _repo.getMealsByCategory(category, new MealByCallback() {
            @Override
            public void onSuccessResult(List<MealBy> meals) {
                _view.showMeals(meals);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                _view.showError(errorMsg);
            }
        });
    }
}
