package com.example.mealplannerapp.favorite.presenter;

import android.util.Log;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.favorite.view.FavoriteMealView;
import com.example.mealplannerapp.meal.models.Meal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteMealPresenterImpl implements FavoriteMealPresenter {
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
                        meals -> {
                            if (meals.isEmpty()) {
                                getFavoritesFromFirestore();
                            } else {
                                _view.showFavoriteMeals(meals);
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            _view.showErrMsg(error.getMessage());
                        }
                );
    }

    private void getFavoritesFromFirestore() {
        _repo.getFavoriteMealsFromFirestore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (!meals.isEmpty()) {
                                _view.showFavoriteMeals(meals);
                                for (Meal meal : meals) {
                                    _repo.insertMealToFav(meal)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> Log.d(TAG, "Inserted meal into favorites"),
                                                    error -> Log.e(TAG, "Error inserting meal into favorites", error)
                                            );
                                }
                            } else {
                                _view.showErrMsg("No favorites found");
                            }
                        },
                        error -> {
                            error.printStackTrace();
                            _view.showErrMsg(error.getMessage());
                        }
                );
    }


    @Override
    public void deleteFavorite(Meal meal) {
        _repo.deleteMealFromFavorite(meal)                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d(TAG, "Deleted from favorites"),
                        error -> Log.e(TAG, "Error while deleting from favorites", error)
                );
    }
}
