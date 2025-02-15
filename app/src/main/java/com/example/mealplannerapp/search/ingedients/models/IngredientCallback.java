package com.example.mealplannerapp.search.ingedients.models;



import java.util.List;

//no.1 on retrofit
public interface IngredientCallback {
    //no.2 retrofit
    public void onSuccessResult(List<Ingredient> ingredients);
    public void onFailureResult(String errorMsg);
}
