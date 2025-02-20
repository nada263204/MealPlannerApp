package com.example.mealplannerapp.data.remoteDataSource;

import com.example.mealplannerapp.meal.models.MealByResponse;
import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.search.categories.models.CategoriesResponse;
import com.example.mealplannerapp.search.countries.models.CountryResponse;
import com.example.mealplannerapp.search.ingedients.models.IngredientResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("random.php")
    Observable<MealResponse> getRandomMeal();

    @GET("list.php?i=list")
    Observable<IngredientResponse> getIngredients();

    @GET("lookup.php")
    Observable<MealResponse> getMealById(@Query("i") String mealId);

    @GET("filter.php")
    Observable<MealByResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("list.php?a=list")
    Observable<CountryResponse> getCountries();

    @GET("filter.php")
    Observable<MealByResponse> getMealsByCountry(@Query("a") String country);

    @GET("categories.php")
    Observable<CategoriesResponse> getCategories();

    @GET("filter.php")
    Observable<MealByResponse> getMealsByCategory(@Query("c") String category);
}
