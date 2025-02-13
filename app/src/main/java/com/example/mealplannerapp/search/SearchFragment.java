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
import com.example.mealplannerapp.ApiClient;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.search.categories.CategoriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.categories.Category;
import com.example.mealplannerapp.search.categories.CategoriesResponse;
import com.example.mealplannerapp.search.categories.CategoriesService;
import com.example.mealplannerapp.search.countries.CountriesRecyclerViewAdapter;
import com.example.mealplannerapp.search.countries.Country;
import com.example.mealplannerapp.search.countries.CountryRespsonse;
import com.example.mealplannerapp.search.countries.CountryService;
import com.example.mealplannerapp.search.ingedients.Ingredient;
import com.example.mealplannerapp.search.ingedients.IngredientResponse;
import com.example.mealplannerapp.search.ingedients.IngredientService;
import com.example.mealplannerapp.search.ingedients.IngredientsRecyclerViewAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private RecyclerView ingredientsRecyclerView, countriesRecyclerView, categoriesRecyclerView;
    private IngredientsRecyclerViewAdapter ingredientsAdapter;
    private CountriesRecyclerViewAdapter countriesAdapter;
    private CategoriesRecyclerViewAdapter categoriesAdapter;

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

        fetchIngredients();
        fetchCountries();
        fetchCategories();

        return view;
    }

    private void fetchIngredients() {
        IngredientService ingredientService = ApiClient.getClient().create(IngredientService.class);
        Call<IngredientResponse> call = ingredientService.getIngredients();

        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredient> ingredientsList = response.body().getMeals();
                    ingredientsAdapter = new IngredientsRecyclerViewAdapter(ingredientsList);
                    ingredientsRecyclerView.setAdapter(ingredientsAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to get ingredients", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCountries() {
        CountryService countryService = ApiClient.getClient().create(CountryService.class);
        Call<CountryRespsonse> call = countryService.getCountries();

        call.enqueue(new Callback<CountryRespsonse>() {
            @Override
            public void onResponse(Call<CountryRespsonse> call, Response<CountryRespsonse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> countriesList = response.body().getMeals();
                    countriesAdapter = new CountriesRecyclerViewAdapter(countriesList);
                    countriesRecyclerView.setAdapter(countriesAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to get countries", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CountryRespsonse> call, Throwable t) {
                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCategories() {
        CategoriesService categoriesService = ApiClient.getClient().create(CategoriesService.class);
        Call<CategoriesResponse> call = categoriesService.getCategories();

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categoriesList = response.body().getCategories();
                    categoriesAdapter = new CategoriesRecyclerViewAdapter(categoriesList);
                    categoriesRecyclerView.setAdapter(categoriesAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to get categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
