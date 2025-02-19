package com.example.mealplannerapp.search.ingedients.models;

import com.example.mealplannerapp.meal.models.MealByResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByIngredientService {
    @GET("filter.php")
    Call<MealByResponse> getMealsByIngredient(@Query("i") String ingredient);
}
