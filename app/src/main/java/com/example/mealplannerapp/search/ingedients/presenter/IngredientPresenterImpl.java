package com.example.mealplannerapp.search.ingedients.presenter;

import com.example.mealplannerapp.search.ingedients.models.IngredientCallback;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IngredientPresenterImpl implements IngredientPresenter {
    private IngredientsView _view;
    private Repository _repo;

    public IngredientPresenterImpl(IngredientsView view,Repository repo){
        this._view =view;
        this._repo=repo;
    }


    @Override
    public void getIngredients()
    {
        _repo.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredient -> _view.showData(ingredient),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }
}
