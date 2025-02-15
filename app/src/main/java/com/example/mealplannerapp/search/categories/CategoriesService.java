package com.example.mealplannerapp.search.categories;

import com.example.mealplannerapp.search.ingedients.IngredientResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesService {
    @GET("categories.php")
    Call<CategoriesResponse> getCategories();
}
