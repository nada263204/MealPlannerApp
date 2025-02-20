package com.example.mealplannerapp.search.ingedients.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.MealByCallback;
import com.example.mealplannerapp.search.ingedients.view.MealsByIngredientView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MealsByIngredientPresenterImpl implements MealByIngredientPresenter {
    private MealsByIngredientView _view;
    private Repository _repo;

    public MealsByIngredientPresenterImpl(MealsByIngredientView view, Repository repository) {
        this._view = view;
        this._repo = repository;
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        _repo.getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> _view.showMeals(meal),
                        error -> _view.showError(error.getMessage())
                );
    }
}

