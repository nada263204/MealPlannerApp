package com.example.mealplannerapp.schedule.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ScheduledMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertScheduledMeal(ScheduledMeal meal);

    @Query("SELECT * FROM scheduled_meals")
    Flowable<List<ScheduledMeal>> getAllScheduledMeals();

    @Query("SELECT * FROM scheduled_meals WHERE date = :date")
    Flowable<List<ScheduledMeal>> getMealsByDate(String date);

    @Delete
    Completable deleteScheduledMeal(ScheduledMeal meal);

    @Query("DELETE FROM scheduled_meals")
    Completable clearAllScheduledMeals();
}
