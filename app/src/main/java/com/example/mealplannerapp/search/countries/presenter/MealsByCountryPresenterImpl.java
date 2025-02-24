package com.example.mealplannerapp.search.countries.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.countries.view.MealByCountryView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealsByCountryPresenterImpl implements MealsByCountryPresenter{
    private MealByCountryView _view;
    private Repository _repo;

    public MealsByCountryPresenterImpl(MealByCountryView view, Repository repository) {
        this._view = view;
        this._repo = repository;
    }
    @Override
    public void getMealsByCountry(String country) {
        _repo.getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> _view.showMeals(meal),
                        error -> _view.showError(error.getMessage())
                );
    }
}
