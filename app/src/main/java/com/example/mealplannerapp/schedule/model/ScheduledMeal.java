package com.example.mealplannerapp.schedule.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mealplannerapp.meal.models.Meal;

@Entity(tableName = "scheduled_meals")
public class ScheduledMeal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setDate(String date) {
        this.date = date;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    private String date;
    private String mealType;

    @Embedded
    private Meal meal;

    public ScheduledMeal() {
    }

    public ScheduledMeal(String date, String mealType, Meal meal) {
        this.date = date;
        this.mealType = mealType;
        this.meal = meal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public String getMealType() { return mealType; }
    public Meal getMeal() { return meal; }
}

