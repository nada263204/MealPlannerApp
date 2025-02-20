package com.example.mealplannerapp.data.remoteDataSource;

import android.util.Log;

import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.search.categories.models.CategoriesCallback;
import com.example.mealplannerapp.search.categories.models.CategoriesResponse;
import com.example.mealplannerapp.search.countries.models.CountriesCallBack;
import com.example.mealplannerapp.search.countries.models.CountryResponse;
import com.example.mealplannerapp.search.ingedients.models.IngredientResponse;
import com.example.mealplannerapp.meal.models.MealByCallback;
import com.example.mealplannerapp.meal.models.MealByResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {
    private ApiServices apiServices;
    private static RemoteDataSource instance;
    private static final String TAG = "RemoteDataSource";

    public RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.getClient().baseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiServices =  retrofit.create(ApiServices.class);
    }

    public Observable<IngredientResponse> makeIngredientsNetworkCall() {
        return apiServices.getIngredients()
                .subscribeOn(Schedulers.io());
    }

    public Observable<CategoriesResponse> makeCategoriesNetworkCall() {
        return apiServices.getCategories()
                .subscribeOn(Schedulers.io());
    }

    public Observable<CountryResponse> makeCountriesNetworkCall() {
        return apiServices.getCountries()
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealResponse> makeMealNetworkCall() {
        return apiServices.getRandomMeal()
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealByResponse> makeMealByIngredientNetworkCall(String ingredientId) {
        return apiServices.getMealsByIngredient(ingredientId)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealByResponse> makeMealByCountryNetworkCall(String country) {
        return apiServices.getMealsByCountry(country)
                .subscribeOn(Schedulers.io());
    }

    public Observable<MealByResponse> makeMealByCategoryNetworkCall(String category) {
        return apiServices.getMealsByCategory(category)
                .subscribeOn(Schedulers.io());
    }


    public Observable<MealResponse> makeMealDetailsNetworkCall(String mealId) {
        return apiServices.getMealById(mealId)
                .subscribeOn(Schedulers.io());
    }

    public static synchronized RemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteDataSource();
        }
        return instance;
    }
}
