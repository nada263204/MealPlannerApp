package com.example.mealplannerapp.data.localDataSource;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealplannerapp.schedule.ScheduledMeal;
import com.example.mealplannerapp.schedule.ScheduledMealDao;
import com.example.mealplannerapp.meal.models.Meal;

@Database(entities = {Meal.class, ScheduledMeal.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static volatile MyDatabase instance;

    public abstract MealDao getMealDao();
    public abstract ScheduledMealDao getScheduledMealDao();

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "meal_planner_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
