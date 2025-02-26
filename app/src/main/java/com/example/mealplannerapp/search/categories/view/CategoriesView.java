package com.example.mealplannerapp.search.categories.view;

import com.example.mealplannerapp.search.categories.models.Category;

import java.util.List;

public interface CategoriesView {
    public void showCategories(List<Category> categories);
    public void showErrMsg(String error);
}
