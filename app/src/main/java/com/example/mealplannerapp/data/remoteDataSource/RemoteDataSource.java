package com.example.mealplannerapp.data.remoteDataSource;

import android.util.Log;

import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.meal.models.MealService;
import com.example.mealplannerapp.search.categories.models.CategoriesCallback;
import com.example.mealplannerapp.search.categories.models.CategoriesResponse;
import com.example.mealplannerapp.search.categories.models.CategoriesService;
import com.example.mealplannerapp.search.countries.models.CountriesCallBack;
import com.example.mealplannerapp.search.countries.models.CountryResponse;
import com.example.mealplannerapp.search.countries.models.CountryService;
import com.example.mealplannerapp.search.ingedients.models.IngredientCallback;
import com.example.mealplannerapp.search.ingedients.models.IngredientResponse;
import com.example.mealplannerapp.search.ingedients.models.IngredientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {
    private IngredientService ingredientService;
    private CountryService countryService;
    private CategoriesService categoriesService;
    private MealService mealService;
    private static RemoteDataSource instance;

    private static final String TAG = "RemoteDataSource";

    public RemoteDataSource() {
        ingredientService = ApiClient.getClient().create(IngredientService.class);
        countryService = ApiClient.getClient().create(CountryService.class);
        categoriesService =ApiClient.getClient().create(CategoriesService.class);
        mealService =ApiClient.getClient().create(MealService.class);

    }

    public void makeIngredientsNetworkCall(IngredientCallback ingredientCallback) {
        ingredientService.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ingredientCallback.onSuccessResult(response.body().getIngredients());
                } else {
                    ingredientCallback.onFailureResult("Failed to fetch products");
                }

            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable throwable) {
                ingredientCallback.onFailureResult(throwable.getMessage());
            }
        });

    }

    public void makeCategoriesNetworkCall(CategoriesCallback categoriesCallback) {
        categoriesService.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoriesCallback.onSuccessResult(response.body().getCategories());
                } else {
                    categoriesCallback.onFailureResult("Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                categoriesCallback.onFailureResult(throwable.getMessage());
            }
        });

    }

    public void makeCountriesNetworkCall(CountriesCallBack countryCallback) {
        countryService.getCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: "+response.body().getCountries() );
                    countryCallback.onSuccessResult(response.body().getCountries());
                } else {
                    countryCallback.onFailureResult("Failed to fetch countries");
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable throwable) {
                countryCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

    public void makeMealNetworkCall(MealCallback mealCallback) {
        mealService.getMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: "+response.body().getMeals());
                    mealCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealCallback.onFailureResult("Failed to fetch countries");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                mealCallback.onFailureResult(throwable.getMessage());
            }

        });
    }



    public static synchronized RemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteDataSource();
        }
        return instance;
    }
}
