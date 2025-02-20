package com.example.mealplannerapp.data.repo;

import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.MealByResponse;
import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.search.categories.models.CategoriesResponse;
import com.example.mealplannerapp.search.categories.models.Category;
import com.example.mealplannerapp.search.countries.models.Country;
import com.example.mealplannerapp.search.countries.models.CountryResponse;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.search.ingedients.models.IngredientResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Repository {
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    private static Repository repo =null;

    public static Repository getInstance(RemoteDataSource remote,LocalDataSource local){
        if(repo == null){
            repo = new Repository(remote,local);
        }
        return repo;
    }

    private Repository(RemoteDataSource remote,LocalDataSource local){
        this.remoteDataSource = remote;
        this.localDataSource = local;
    }

    public Observable<List<Ingredient>> getAllIngredients(){
        return remoteDataSource.makeIngredientsNetworkCall()
                .map(IngredientResponse::getIngredients);
    }

    public Observable<List<Country>> getAllCountries(){
        return remoteDataSource.makeCountriesNetworkCall()
                .map(CountryResponse::getCountries);
    }

    public Observable<List<Category>> getAllCategories(){
        return remoteDataSource.makeCategoriesNetworkCall()
                .map(CategoriesResponse::getCategories);
    }

    public Observable<List<Meal>> getRandomMeal(){
        return remoteDataSource.makeMealNetworkCall()
                .map(MealResponse::getMeals);
    }

    public Observable<List<Meal>> getMealById(String mealId) {
        return remoteDataSource.makeMealDetailsNetworkCall(mealId)
                .map(MealResponse::getMeals);
    }

    public Observable<List<MealBy>> getMealsByIngredient(String ingredientId) {
        return remoteDataSource.makeMealByIngredientNetworkCall(ingredientId)
                .map(MealByResponse::getMeals);
    }

    public Observable<List<MealBy>> getMealsByCountry(String country) {
        return remoteDataSource.makeMealByCountryNetworkCall(country)
                .map(MealByResponse::getMeals);
    }

    public Observable<List<MealBy>> getMealsByCategory(String category) {
        return remoteDataSource.makeMealByCategoryNetworkCall(category)
                .map(MealByResponse::getMeals);
    }

    public Flowable<List<Meal>> getMealProducts() {
        return localDataSource.getFavoriteMeals();
    }

    public Completable insertMealToFav(Meal meal){
        return localDataSource.insertMealToFav(meal);
    }

    public Completable deleteMealFromFavorite(Meal meal){
        return localDataSource.deleteMealFromFav(meal);
    }

}
