package com.example.mealplannerapp.search.ingedients.presenter;

import com.example.mealplannerapp.search.ingedients.models.IngredientCallback;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;

import java.util.List;

public class IngredientPresenterImpl implements IngredientPresenter , IngredientCallback {
    private IngredientsView _view;
    private Repository _repo;

    public IngredientPresenterImpl(IngredientsView view,Repository repo){
        this._view =view;
        this._repo=repo;
    }

    @Override
    public void onSuccessResult(List<Ingredient> ingredients) {
        _view.showData(ingredients);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }

    @Override
    public void getIngredients() {
        _repo.getAllIngredients(this);
    }
}
