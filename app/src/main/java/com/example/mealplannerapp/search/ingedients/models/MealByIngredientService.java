package com.example.mealplannerapp.search.ingedients.models;

import com.example.mealplannerapp.meal.models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByIngredientService {
    @GET("filter.php")
    Call<MealByIngredientResponse> getMealsByIngredient(@Query("i") String ingredient);
}
