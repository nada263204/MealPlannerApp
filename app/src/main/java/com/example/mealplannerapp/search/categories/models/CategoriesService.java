package com.example.mealplannerapp.search.categories.models;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesService {
    @GET("categories.php")
    Call<CategoriesResponse> getCategories();
}
