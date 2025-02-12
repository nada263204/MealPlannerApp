package com.example.mealplannerapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlagService {
    @GET("v3.1/name/{country}")
    Call<List<FlagResponse>> getFlag(@Path("country") String country);
}
