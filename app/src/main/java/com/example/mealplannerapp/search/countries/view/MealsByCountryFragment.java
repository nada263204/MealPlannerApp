package com.example.mealplannerapp.search.countries.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.view.MealDetailsFragment;
import com.example.mealplannerapp.meal.view.OnMealClickListener;
import com.example.mealplannerapp.search.countries.presenter.MealsByCountryPresenter;
import com.example.mealplannerapp.search.countries.presenter.MealsByCountryPresenterImpl;
import com.example.mealplannerapp.meal.models.MealBy;

import java.util.ArrayList;
import java.util.List;

public class MealsByCountryFragment extends Fragment implements MealByCountryView, OnMealClickListener {
    private RecyclerView recyclerView;
    private MealByCountryRecyclerViewAdapter adapter;
    private MealsByCountryPresenter presenter;
    private String countryName;

    public MealsByCountryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            countryName = getArguments().getString("country_name", "");
        }

        if (countryName == null || countryName.isEmpty()) {
            showToast("No ingredient name found");
            return;
        }

        Repository repository = Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(getContext()));
        presenter = new MealsByCountryPresenterImpl(this, repository);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals_by_country, container, false);

        recyclerView = view.findViewById(R.id.recycler_meals_by_country);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MealByCountryRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        if (countryName != null && !countryName.isEmpty()) {
            presenter.getMealsByCountry(countryName);
        }

        return view;
    }

    @Override
    public void showMeals(List<MealBy> meals) {
        if (meals == null || meals.isEmpty()) {
            showToast("No meals found for this country");
            return;
        }
        adapter.updateMeals(meals);
    }

    @Override
    public void showError(String message) {
        showToast("Error: " + message);
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnMealClick(MealBy meal) {
        if (meal == null || meal.getId() == null) {
            showToast("Invalid meal selected");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("MEAL_ID", meal.getId());

        MealDetailsFragment fragment = new MealDetailsFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
