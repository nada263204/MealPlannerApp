package com.example.mealplannerapp.search.ingedients.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.ingedients.models.MealByIngredient;
import com.example.mealplannerapp.search.ingedients.models.MealByIngredientCallback;
import com.example.mealplannerapp.search.ingedients.view.MealsByIngredientView;

import java.util.List;

public class MealsByIngredientPresenterImpl implements MealByIngredientPresenter {
    private MealsByIngredientView _view;
    private Repository _repo;

    public MealsByIngredientPresenterImpl(MealsByIngredientView view, Repository repository) {
        this._view = view;
        this._repo = repository;
    }

    @Override
    public void getMealsByIngredient(String ingredient) {
        _repo.getMealsByIngredient(ingredient, new MealByIngredientCallback() {

            @Override
            public void onSuccessResult(List<MealByIngredient> meals) {
                _view.showMeals(meals);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                _view.showError(errorMsg);
            }
        });
    }
}

