package com.example.mealplannerapp.favorite;

import com.example.mealplannerapp.meal.models.Meal;

public interface FavoriteMealPresenter {

    void getFavorite();
    void deleteFavorite(Meal meal);
}
