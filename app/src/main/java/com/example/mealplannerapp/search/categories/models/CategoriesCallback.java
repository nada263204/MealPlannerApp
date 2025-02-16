package com.example.mealplannerapp.search.categories.models;

import com.example.mealplannerapp.search.ingedients.models.Ingredient;

import java.util.List;

public interface CategoriesCallback {
    public void onSuccessResult(List<Category> categories);
    public void onFailureResult(String errorMsg);
}
