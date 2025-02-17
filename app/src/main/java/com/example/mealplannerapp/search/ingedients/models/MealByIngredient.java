package com.example.mealplannerapp.search.ingedients.models;

import com.google.gson.annotations.SerializedName;

public class MealByIngredient {
    @SerializedName("idMeal")
    private String id;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strMealThumb")
    private String thumbnail;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getThumbnail() { return thumbnail; }
}
