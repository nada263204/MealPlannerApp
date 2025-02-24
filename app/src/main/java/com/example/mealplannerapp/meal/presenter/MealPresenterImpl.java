package com.example.mealplannerapp.meal.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.view.HomeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MealPresenterImpl implements MealPresenter {
    private HomeView _view;
    private Repository _repo;
    private Disposable disposable;

    public MealPresenterImpl(HomeView view, Repository repo) {
        this._view = view;
        this._repo = repo;
    }


    public void getMeals() {
        disposable = _repo.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    if (_view != null) _view.showMeal(meals);
                }, throwable -> {
                    if (_view != null) _view.showErrMsg(throwable.getMessage());
                });
    }

    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
