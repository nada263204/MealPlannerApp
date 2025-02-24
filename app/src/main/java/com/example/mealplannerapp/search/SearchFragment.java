package com.example.mealplannerapp.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.categories.presenter.CategoriesPresenter;
import com.example.mealplannerapp.search.categories.presenter.CategoriesPresenterImpl;
import com.example.mealplannerapp.search.categories.view.CategoriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.categories.models.Category;
import com.example.mealplannerapp.search.categories.view.CategoriesView;
import com.example.mealplannerapp.search.categories.view.MealsByCategoryFragment;
import com.example.mealplannerapp.search.categories.view.OnCategoryClickListener;
import com.example.mealplannerapp.search.countries.presenter.CountriesPresenter;
import com.example.mealplannerapp.search.countries.presenter.CountriesPresenterImpl;
import com.example.mealplannerapp.search.countries.view.CountriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.countries.models.Country;
import com.example.mealplannerapp.search.countries.view.CountriesView;
import com.example.mealplannerapp.search.countries.view.MealsByCountryFragment;
import com.example.mealplannerapp.search.countries.view.OnCountryClickListener;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.presenter.IngredientPresenter;
import com.example.mealplannerapp.search.ingedients.presenter.IngredientPresenterImpl;
import com.example.mealplannerapp.search.ingedients.view.IngredientsRecyclerViewAdapter;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;
import com.example.mealplannerapp.search.ingedients.view.MealsByIngredientFragment;
import com.example.mealplannerapp.search.ingedients.view.OnIngredientClickListener;

import java.util.List;

public class SearchFragment extends Fragment implements  OnIngredientClickListener, OnCategoryClickListener, OnCountryClickListener,IngredientsView, CountriesView, CategoriesView {
    private RecyclerView ingredientsRecyclerView, countriesRecyclerView, categoriesRecyclerView;
    private IngredientsRecyclerViewAdapter ingredientsAdapter;
    private CountriesRecyclerViewAdapter countriesAdapter;
    private CategoriesRecyclerViewAdapter categoriesAdapter;
    private IngredientPresenter ingredientPresenter;
    private CountriesPresenter countriesPresenter;
    private CategoriesPresenter categoriesPresenter;

    private ImageView btnExpandIngredients, btnExpandCountries, btnExpandCategories;
    private boolean isIngredientsExpanded = true;
    private boolean isCountriesExpanded = false;
    private boolean isCategoriesExpanded = false;

    public SearchFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        btnExpandIngredients = view.findViewById(R.id.btnExpandIngredients);
        countriesRecyclerView = view.findViewById(R.id.countriesRecyclerView);
        btnExpandCountries = view.findViewById(R.id.btnExpandCountries);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        btnExpandCategories = view.findViewById(R.id.btnExpandCategories);

        ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
        countriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        Repository repository = Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(getContext()), new FirestoreDataSource());
        ingredientPresenter = new IngredientPresenterImpl(this, repository);
        countriesPresenter = new CountriesPresenterImpl(this, repository);
        categoriesPresenter = new CategoriesPresenterImpl(this, repository);

        ingredientPresenter.getIngredients();
        countriesPresenter.getCountries();
        categoriesPresenter.getCategories();

        btnExpandIngredients.setOnClickListener(v -> toggleRecyclerView("ingredients"));
        btnExpandCountries.setOnClickListener(v -> toggleRecyclerView("countries"));
        btnExpandCategories.setOnClickListener(v -> toggleRecyclerView("categories"));

        return view;
    }

    private void toggleRecyclerView(String section) {
        switch (section) {
            case "ingredients":
                if (!isIngredientsExpanded) {
                    ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
                    btnExpandIngredients.setImageResource(R.drawable.ic_collapse);
                    isIngredientsExpanded = true;

                    collapseRecyclerView("countries");
                    collapseRecyclerView("categories");
                } else {
                    collapseRecyclerView("ingredients");
                }
                break;

            case "countries":
                if (!isCountriesExpanded) {
                    countriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
                    btnExpandCountries.setImageResource(R.drawable.ic_collapse);
                    isCountriesExpanded = true;

                    collapseRecyclerView("ingredients");
                    collapseRecyclerView("categories");
                } else {
                    collapseRecyclerView("countries");
                }
                break;

            case "categories":
                if (!isCategoriesExpanded) {
                    categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
                    btnExpandCategories.setImageResource(R.drawable.ic_collapse);
                    isCategoriesExpanded = true;

                    collapseRecyclerView("ingredients");
                    collapseRecyclerView("countries");
                } else {
                    collapseRecyclerView("categories");
                }
                break;
        }
    }

    private void collapseRecyclerView(String section) {
        switch (section) {
            case "ingredients":
                ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
                btnExpandIngredients.setImageResource(R.drawable.ic_expand);
                isIngredientsExpanded = false;
                break;

            case "countries":
                countriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
                btnExpandCountries.setImageResource(R.drawable.ic_expand);
                isCountriesExpanded = false;
                break;

            case "categories":
                categoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
                btnExpandCategories.setImageResource(R.drawable.ic_expand);
                isCategoriesExpanded = false;
                break;
        }
    }


    @Override
    public void showData(List<Ingredient> ingredients) {
        if (ingredients != null && !ingredients.isEmpty()) {
            ingredientsAdapter = new IngredientsRecyclerViewAdapter(ingredients,this);
            ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        } else {
            showErrMsg("No ingredients available");
        }
    }


    @Override
    public void showCategories(List<Category> categories) {
        if (categories != null && !categories.isEmpty()) {
            categoriesAdapter = new CategoriesRecyclerViewAdapter(categories,this::onCategoryClick);
            categoriesRecyclerView.setAdapter(categoriesAdapter);
        } else {
            showErrMsg("No categories available");
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientClick(Ingredient ingredient) {
        if (ingredient == null || ingredient.getStrIngredient() == null) {
            Toast.makeText(getContext(), "Invalid ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("ingredient_name", ingredient.getStrIngredient());

        MealsByIngredientFragment fragment = new MealsByIngredientFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showCountries(List<Country> countries) {
        if (countries != null && !countries.isEmpty()) {
            countriesAdapter = new CountriesRecyclerViewAdapter(countries,this);
            countriesRecyclerView.setAdapter(countriesAdapter);
        } else {
            showErrMsg("No countries available");
        }
    }

    @Override
    public void onCountryClick(Country country) {
        if (country == null || country.getStrArea() == null) {
            Toast.makeText(getContext(), "Invalid country", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Fetching meals for: " + country.getStrArea(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("country_name", country.getStrArea());

        MealsByCountryFragment fragment = new MealsByCountryFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }




    @Override
    public void onCategoryClick(Category category) {
        if (category == null || category.getStrCategory() == null) {
            Toast.makeText(getContext(), "Invalid country", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "Fetching meals for: " + category.getStrCategory(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("category_name", category.getStrCategory());

        MealsByCategoryFragment fragment = new MealsByCategoryFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}