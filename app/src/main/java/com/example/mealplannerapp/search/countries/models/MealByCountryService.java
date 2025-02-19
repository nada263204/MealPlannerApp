package com.example.mealplannerapp.search.countries.models;

import com.example.mealplannerapp.meal.models.MealByResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealByCountryService {
    @GET("filter.php")
    Call<MealByResponse> getMealsByCountry(@Query("a") String country);
}
