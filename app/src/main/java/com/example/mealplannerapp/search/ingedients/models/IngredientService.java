package com.example.mealplannerapp.search.ingedients.models;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IngredientService {
    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();
}
