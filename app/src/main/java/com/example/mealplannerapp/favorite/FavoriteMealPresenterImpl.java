package com.example.mealplannerapp.favorite;

import android.util.Log;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteMealPresenterImpl implements  FavoriteMealPresenter{
    private static final String TAG = "FavoriteMealPresenterIm";
    private final Repository _repo;

    private final FavoriteMealView _view;

    public FavoriteMealPresenterImpl(FavoriteMealView view,Repository repo){
        this._view=view;
        this._repo=repo;
    }
    @Override
    public void getFavorite() {
        _repo.getFavoriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> _view.showFavoriteMeals(meals),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }

    @Override
    public void deleteFavorite(Meal meal) {
        _repo.deleteMealFromFavorite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "deletedFavorite"),
                        error -> Log.d(TAG, "error while deleteing From Favorite")
                );
    }


}
