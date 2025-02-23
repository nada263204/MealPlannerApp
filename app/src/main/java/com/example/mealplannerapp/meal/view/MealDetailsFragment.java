package com.example.mealplannerapp.meal.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenter;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenterImpl;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealView, OnFavMealClickListener, MealTypeDialogFragment.OnMealTypeSelectedListener {
    private MealDetailsPresenter presenter;
    private ImageView mealImage, btnAddToFav;
    private TextView mealName, mealCategoryArea, mealInstructions, mealIngredients;
    private Button playVideoButton, btnAddToCalendar;
    private WebView youtubeWebView;
    private String mealId;
    private Meal currentMeal;

    public MealDetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealName);
        mealCategoryArea = view.findViewById(R.id.mealCategoryArea);
        mealInstructions = view.findViewById(R.id.mealInstructions);
        mealIngredients = view.findViewById(R.id.mealIngredients);
        playVideoButton = view.findViewById(R.id.playVideoButton);
        youtubeWebView = view.findViewById(R.id.youtubeWebView);
        btnAddToFav = view.findViewById(R.id.btn_addToFav);
        btnAddToCalendar = view.findViewById(R.id.btn_addToCalendar);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext()));

        presenter = new MealDetailsPresenterImpl(this, repository);

        if (getArguments() != null && getArguments().containsKey("MEAL_ID")) {
            mealId = getArguments().getString("MEAL_ID");
            presenter.getMealById(mealId);
        } else {
            Toast.makeText(requireContext(), "Meal ID is missing", Toast.LENGTH_SHORT).show();
        }

        btnAddToFav.setOnClickListener(v -> {
            if (currentMeal != null) {
                onFavMealClick(currentMeal);
            }
        });

        btnAddToCalendar.setOnClickListener(v -> {
            if (currentMeal != null) {
                openMealTypeDialog();
            }
        });

        return view;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (!meals.isEmpty()) {
            Meal meal = meals.get(0);
            currentMeal = meal;

            mealName.setText(meal.getStrMeal());
            mealCategoryArea.setText(meal.getStrCategory() + " - " + meal.getStrArea());
            mealInstructions.setText(meal.getStrInstructions());
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);
        }
    }

    @Override
    public void showErrMsg(String error) {
    }

    @Override
    public void onMealTypeSelected(String mealType, String date) {
        if (currentMeal != null) {
            presenter.addMealToCalendar(currentMeal, mealType, date);
            Toast.makeText(requireContext(), "Meal added to " + mealType + " on " + date, Toast.LENGTH_SHORT).show();
        }
    }

    private void openMealTypeDialog() {
        MealTypeDialogFragment dialogFragment = new MealTypeDialogFragment();
        dialogFragment.setTargetFragment(this, 0);
        FragmentManager fragmentManager = getParentFragmentManager();
        dialogFragment.show(fragmentManager, "MealTypeDialogFragment");
    }

    @Override
    public void onFavMealClick(Meal meal) {
        presenter.addToFav(meal);
        Toast.makeText(requireContext(), "Meal added to favorite", Toast.LENGTH_SHORT).show();
    }

}
