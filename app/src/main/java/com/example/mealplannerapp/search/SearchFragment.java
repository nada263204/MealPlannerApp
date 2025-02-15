package com.example.mealplannerapp.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.categories.presenter.CategoriesPresenter;
import com.example.mealplannerapp.search.categories.presenter.CategoriesPresenterImpl;
import com.example.mealplannerapp.search.categories.view.CategoriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.categories.models.Category;
import com.example.mealplannerapp.search.categories.view.CategoriesView;
import com.example.mealplannerapp.search.countries.presenter.CountriesPresenter;
import com.example.mealplannerapp.search.countries.presenter.CountriesPresenterImpl;
import com.example.mealplannerapp.search.countries.view.CountriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.countries.models.Country;
import com.example.mealplannerapp.search.countries.view.CountriesView;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.presenter.IngredientPresenter;
import com.example.mealplannerapp.search.ingedients.presenter.IngredientPresenterImpl;
import com.example.mealplannerapp.search.ingedients.view.IngredientsRecyclerViewAdapter;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;
import com.example.mealplannerapp.search.ingedients.view.OnIngredientClickListener;

import java.util.List;

public class SearchFragment extends Fragment implements IngredientsView, OnIngredientClickListener, CountriesView , CategoriesView {
    private RecyclerView ingredientsRecyclerView, countriesRecyclerView, categoriesRecyclerView;
    private IngredientsRecyclerViewAdapter ingredientsAdapter;
    private CountriesRecyclerViewAdapter countriesAdapter;
    private CategoriesRecyclerViewAdapter categoriesAdapter;
    private IngredientPresenter ingredientPresenter;
    private CountriesPresenter countriesPresenter;
    private CategoriesPresenter categoriesPresenter;

    public SearchFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        countriesRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Repository repository = Repository.getInstance(RemoteDataSource.getInstance());

        ingredientPresenter = new IngredientPresenterImpl(this,repository);
        countriesPresenter = new CountriesPresenterImpl(this,repository);
        categoriesPresenter = new CategoriesPresenterImpl(this,repository);

        // Fetch data
        ingredientPresenter.getIngredients();
        countriesPresenter.getCountries();
        categoriesPresenter.getCategories();

        return view;
    }

    @Override
    public void showData(List<Ingredient> ingredients) {
        ingredientsAdapter = new IngredientsRecyclerViewAdapter(ingredients);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showCountries(List<Country> countries) {
        countriesAdapter = new CountriesRecyclerViewAdapter(countries);
        countriesRecyclerView.setAdapter(countriesAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter = new CategoriesRecyclerViewAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        Toast.makeText(getContext(), "Clicked: " + ingredient.getStrIngredient(), Toast.LENGTH_SHORT).show();
    }

}
