package com.example.mealplannerapp.search.ingedients.view;

import com.example.mealplannerapp.search.ingedients.models.Ingredient;

import java.util.List;

public interface IngredientsView {

    public void showData(List<Ingredient> ingredients);
    public void showErrMsg(String error);
}
