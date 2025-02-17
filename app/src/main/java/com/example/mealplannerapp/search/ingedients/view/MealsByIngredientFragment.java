package com.example.mealplannerapp.search.ingedients.view;

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
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.MealDetailsFragment;
import com.example.mealplannerapp.meal.models.OnMealClickListener;
import com.example.mealplannerapp.search.ingedients.models.MealByIngredient;
import com.example.mealplannerapp.search.ingedients.presenter.MealByIngredientPresenter;
import com.example.mealplannerapp.search.ingedients.presenter.MealsByIngredientPresenterImpl;
import java.util.ArrayList;
import java.util.List;

public class MealsByIngredientFragment extends Fragment implements MealsByIngredientView, OnMealClickListener {
    private RecyclerView recyclerView;
    private MealsByIngredientAdapter adapter;
    private MealByIngredientPresenter presenter;
    private String ingredientName;

    public MealsByIngredientFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ingredientName = getArguments().getString("ingredient_name", "");
        }

        if (ingredientName == null || ingredientName.isEmpty()) {
            showToast("No ingredient name found");
            return;
        }

        Repository repository = Repository.getInstance(RemoteDataSource.getInstance());
        presenter = new MealsByIngredientPresenterImpl(this, repository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals_by_ingredient, container, false);

        recyclerView = view.findViewById(R.id.recycler_meals_by_ingredient);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MealsByIngredientAdapter(new ArrayList<>(), this);  // Pass this as the listener
        recyclerView.setAdapter(adapter);

        if (ingredientName != null && !ingredientName.isEmpty()) {
            presenter.getMealsByIngredient(ingredientName);
        }

        return view;
    }

    @Override
    public void showMeals(List<MealByIngredient> meals) {
        if (meals == null || meals.isEmpty()) {
            showToast("No meals found for this ingredient");
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
    public void OnMealClick(MealByIngredient meal) {
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
