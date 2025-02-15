package com.example.mealplannerapp;

import com.example.mealplannerapp.Home.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByIDService {
    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);
}
