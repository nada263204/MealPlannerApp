package com.example.mealplannerapp.search.countries.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.countries.view.MealByCountryView;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.MealByCallback;

import java.util.List;

public class MealsByCountryPresenterImpl implements MealsByCountryPresenter{
    private MealByCountryView _view;
    private Repository _repo;

    public MealsByCountryPresenterImpl(MealByCountryView view, Repository repository) {
        this._view = view;
        this._repo = repository;
    }
    @Override
    public void getMealsByCountry(String country) {
        _repo.getMealsByCountry(country, new MealByCallback() {

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
