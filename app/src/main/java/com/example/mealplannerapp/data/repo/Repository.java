package com.example.mealplannerapp.data.repo;

import com.example.mealplannerapp.meal.models.MealCallback;
import com.example.mealplannerapp.search.categories.models.CategoriesCallback;
import com.example.mealplannerapp.search.countries.models.CountriesCallBack;
import com.example.mealplannerapp.search.ingedients.models.IngredientCallback;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.meal.models.MealByCallback;

public class Repository {
    private RemoteDataSource remoteDataSource;
    //private LocalDataSource localDataSource;

    private static Repository repo =null;

    public static Repository getInstance(RemoteDataSource remote){
        if(repo == null){
            repo = new Repository(remote);
        }
        return repo;
    }

    private Repository(RemoteDataSource remote){
        this.remoteDataSource = remote;
        //this.localDataSource = local;
    }

    public void getAllIngredients(IngredientCallback ingredientCallback){
        remoteDataSource.makeIngredientsNetworkCall(ingredientCallback);
    }

    public void getAllCountries(CountriesCallBack countriesCallBack){
        remoteDataSource.makeCountriesNetworkCall(countriesCallBack);
    }

    public void getAllCategories(CategoriesCallback categoriesCallback){
        remoteDataSource.makeCategoriesNetworkCall(categoriesCallback);
    }

    public void getRandomMeal(MealCallback mealCallback){
        remoteDataSource.makeMealNetworkCall(mealCallback);
    }

    public void getMealById(String mealId, MealCallback callback) {
        remoteDataSource.makeMealDetailsNetworkCall(mealId, callback);
    }

    public void getMealsByIngredient(String ingredient, MealByCallback callback) {
        remoteDataSource.makeMealByIngredientNetworkCall(ingredient, callback);
    }

    public void getMealsByCountry(String country, MealByCallback callback) {
        remoteDataSource.makeMealByCountryNetworkCall(country, callback);
    }

    public void getMealsByCategory(String category, MealByCallback callback) {
        remoteDataSource.makeMealByCategoryNetworkCall(category, callback);
    }


}
