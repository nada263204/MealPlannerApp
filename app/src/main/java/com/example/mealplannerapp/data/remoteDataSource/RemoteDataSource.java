package com.example.mealplannerapp.data.remoteDataSource;

import android.util.Log;
import com.example.mealplannerapp.meal.models.MealByIDService;
import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.meal.models.MealService;
import com.example.mealplannerapp.search.categories.models.CategoriesCallback;
import com.example.mealplannerapp.search.categories.models.CategoriesResponse;
import com.example.mealplannerapp.search.categories.models.CategoriesService;
import com.example.mealplannerapp.search.categories.models.MealByCategoryService;
import com.example.mealplannerapp.search.countries.models.CountriesCallBack;
import com.example.mealplannerapp.search.countries.models.CountryResponse;
import com.example.mealplannerapp.search.countries.models.CountryService;
import com.example.mealplannerapp.search.countries.models.MealByCountryService;
import com.example.mealplannerapp.search.ingedients.models.IngredientCallback;
import com.example.mealplannerapp.search.ingedients.models.IngredientResponse;
import com.example.mealplannerapp.search.ingedients.models.IngredientService;
import com.example.mealplannerapp.meal.models.MealByCallback;
import com.example.mealplannerapp.meal.models.MealByResponse;
import com.example.mealplannerapp.search.ingedients.models.MealByIngredientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource {
    private IngredientService ingredientService;
    private CountryService countryService;
    private CategoriesService categoriesService;
    private MealService mealService;
    private MealByIDService mealByIDService;
    private MealByIngredientService mealByIngredientService;
    private MealByCountryService mealByCountryService;
    private MealByCategoryService mealByCategoryService;
    private static RemoteDataSource instance;
    private static final String TAG = "RemoteDataSource";

    public RemoteDataSource() {
        ingredientService = ApiClient.getClient().create(IngredientService.class);
        countryService = ApiClient.getClient().create(CountryService.class);
        categoriesService = ApiClient.getClient().create(CategoriesService.class);
        mealService = ApiClient.getClient().create(MealService.class);
        mealByIDService = ApiClient.getClient().create(MealByIDService.class);
        mealByIngredientService = ApiClient.getClient().create(MealByIngredientService.class);
        mealByCountryService =ApiClient.getClient().create(MealByCountryService.class);
        mealByCategoryService=ApiClient.getClient().create(MealByCategoryService.class);
    }

    public void makeIngredientsNetworkCall(IngredientCallback ingredientCallback) {
        ingredientService.getIngredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ingredientCallback.onSuccessResult(response.body().getIngredients());
                } else {
                    ingredientCallback.onFailureResult("Failed to fetch ingredients");
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
                    categoriesCallback.onFailureResult("Failed to fetch categories");
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
                    Log.d(TAG, "onResponse: " + response.body().getCountries());
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
                    Log.d(TAG, "onResponse: " + response.body().getMeals());
                    mealCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealCallback.onFailureResult("Failed to fetch meals");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                mealCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

    public void makeMealByIngredientNetworkCall(String ingredient, MealByCallback mealByIngredientCallback) {
        mealByIngredientService.getMealsByIngredient(ingredient).enqueue(new Callback<MealByResponse>() {
            @Override
            public void onResponse(Call<MealByResponse> call, Response<MealByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().getMeals());
                    mealByIngredientCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealByIngredientCallback.onFailureResult("Failed to fetch meals by ingredient");
                }
            }

            @Override
            public void onFailure(Call<MealByResponse> call, Throwable throwable) {
                mealByIngredientCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

    public void makeMealByCountryNetworkCall(String country, MealByCallback mealByCountryCallback) {
        mealByCountryService.getMealsByCountry(country).enqueue(new Callback<MealByResponse>() {
            @Override
            public void onResponse(Call<MealByResponse> call, Response<MealByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().getMeals());
                    mealByCountryCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealByCountryCallback.onFailureResult("Failed to fetch meals by ingredient");
                }
            }

            @Override
            public void onFailure(Call<MealByResponse> call, Throwable throwable) {
                mealByCountryCallback.onFailureResult(throwable.getMessage());
            }
        });
    }

    public void makeMealByCategoryNetworkCall(String category, MealByCallback mealByCategoryCallback) {
        mealByCategoryService.getMealsByCategory(category).enqueue(new Callback<MealByResponse>() {
            @Override
            public void onResponse(Call<MealByResponse> call, Response<MealByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().getMeals());
                    mealByCategoryCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealByCategoryCallback.onFailureResult("Failed to fetch meals by country");
                }
            }

            @Override
            public void onFailure(Call<MealByResponse> call, Throwable throwable) {
                mealByCategoryCallback.onFailureResult(throwable.getMessage());
            }
        });
    }


    public void makeMealDetailsNetworkCall(String mealId, MealCallback mealCallback) {
        mealByIDService.getMealById(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().getMeals());
                    mealCallback.onSuccessResult(response.body().getMeals());
                } else {
                    mealCallback.onFailureResult("Failed to fetch meal details");
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
