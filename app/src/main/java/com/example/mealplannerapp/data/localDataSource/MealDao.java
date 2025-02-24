package com.example.mealplannerapp.data.localDataSource;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface MealDao {

    @Query("SELECT * FROM favorite_meals")
    Flowable<List<Meal>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMealToFav(Meal meal);

    @Delete
    Completable deleteMealFromFav(Meal meal);

    @Query("DELETE FROM favorite_meals")
    Completable deleteAllFavoriteMeals();
}
