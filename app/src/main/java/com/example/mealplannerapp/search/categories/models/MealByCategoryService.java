package com.example.mealplannerapp.search.categories.models;

import com.example.mealplannerapp.meal.models.MealByResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByCategoryService {
    @GET("filter.php")
    Call<MealByResponse> getMealsByCategory(@Query("c") String category);
}
