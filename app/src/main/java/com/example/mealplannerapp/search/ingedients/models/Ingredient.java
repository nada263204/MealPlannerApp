package com.example.mealplannerapp.search.ingedients.models;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public String getImageUrl() {
        return "https://www.themealdb.com/images/ingredients/" + strIngredient + ".png";
    }
}
