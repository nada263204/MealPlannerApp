package com.example.mealplannerapp.data.localDataSource;

import android.content.Context;
import android.util.Log;

import com.example.mealplannerapp.schedule.model.ScheduledMeal;
import com.example.mealplannerapp.schedule.model.ScheduledMealDao;
import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalDataSource {
    private static final String TAG = "LocalDataSource";
    private static LocalDataSource repository = null;
    private final MealDao mealDao;
    private final ScheduledMealDao scheduledMealDao;

    private LocalDataSource(Context context) {
        MyDatabase db = MyDatabase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
        scheduledMealDao = db.getScheduledMealDao();
    }

    public static synchronized LocalDataSource getInstance(Context context) {
        if (repository == null) {
            repository = new LocalDataSource(context);
        }
        return repository;
    }

    public Flowable<List<Meal>> getFavoriteMeals() {
        return mealDao.getAllFavMeals();
    }

    public Completable insertMealToFav(Meal meal) {
        return mealDao.insertMealToFav(meal);
    }

    public Completable deleteMealFromFav(Meal meal) {
        return mealDao.deleteMealFromFav(meal);
    }

    public Completable insertScheduledMeal(ScheduledMeal meal) {
        Log.d(TAG, "insertScheduledMeal: "+meal);
        return scheduledMealDao.insertScheduledMeal(meal);
    }

    public Flowable<List<ScheduledMeal>> getAllScheduledMeals() {
        return scheduledMealDao.getAllScheduledMeals();
    }

    public Flowable<List<ScheduledMeal>> getMealsByDate(String date) {
        Log.d("LocalDataSource", "Fetching meals from DB for date: " + date);
        return scheduledMealDao.getMealsByDate(date)
                .doOnNext(meals -> Log.d("LocalDataSource", "Fetched " + meals.size() + " meals for date: " + date));
    }


    public Completable deleteScheduledMeal(ScheduledMeal meal) {
        return scheduledMealDao.deleteScheduledMeal(meal);
    }

    public Completable clearAllScheduledMeals() {
        return scheduledMealDao.clearAllScheduledMeals();
    }

    public Completable clearAllFavoriteMeals() {
        return mealDao.deleteAllFavoriteMeals();
    }
}
