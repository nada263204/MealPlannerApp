package com.example.mealplannerapp.data.localDataSource;

import android.content.Context;

import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalDataSource {

    private static LocalDataSource repository = null;
    private final MealDao mealDao;

    private LocalDataSource(Context context){
        MyDatabase db = MyDatabase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
    }

    public static synchronized LocalDataSource getInstance(Context context){
        if(repository == null){
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

    public Completable deleteMealFromFav(Meal meal){
        return mealDao.deleteMealFromFav(meal);
    }
}
