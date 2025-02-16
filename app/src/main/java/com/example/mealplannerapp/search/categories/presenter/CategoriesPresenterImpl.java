package com.example.mealplannerapp.search.categories.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.categories.models.CategoriesCallback;
import com.example.mealplannerapp.search.categories.models.Category;
import com.example.mealplannerapp.search.categories.view.CategoriesView;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;

import java.util.List;

public class CategoriesPresenterImpl implements CategoriesPresenter, CategoriesCallback {
    private CategoriesView _view;
    private Repository _repo;

    public CategoriesPresenterImpl(CategoriesView view,Repository repo){
        this._view =view;
        this._repo=repo;
    }
    @Override
    public void onSuccessResult(List<Category> categories) {
        _view.showCategories(categories);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }

    @Override
    public void getCategories() {
        _repo.getAllCategories(this);
    }
}
