package com.example.mealplannerapp.search.categories.view;

import android.os.Bundle;

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
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.view.OnMealClickListener;
import com.example.mealplannerapp.search.categories.presenter.MealsByCategoryPresenter;
import com.example.mealplannerapp.search.categories.presenter.MealsByCategoryPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class MealsByCategoryFragment extends Fragment implements MealsByCategoryView , OnMealClickListener {
    private RecyclerView recyclerView;
    private MealsByCategoryAdapter adapter;
    private MealsByCategoryPresenter presenter;
    private String categoryName;

    public MealsByCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            categoryName = getArguments().getString("category_name", "");
        }

        if (categoryName == null || categoryName.isEmpty()) {
            showToast("No ingredient name found");
            return;
        }

        Repository repository = Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(getContext()));
        presenter = new MealsByCategoryPresenterImpl(this, repository);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meals_by_category, container, false);

        recyclerView = view.findViewById(R.id.recycler_meals_by_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MealsByCategoryAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        if (categoryName != null && !categoryName.isEmpty()) {
            presenter.getMealsByCategory(categoryName);
        }

        return view;
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

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMeals(List<MealBy> meals) {
        if (meals == null || meals.isEmpty()) {
            showToast("No meals found for this ingredient");
            return;
        }
        adapter.updateMeals(meals);
    }

    @Override
    public void showError(String error) {
        showToast("Error: " + error);
    }
}


