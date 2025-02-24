package com.example.mealplannerapp.Home.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.Home.presenter.HomePresenter;
import com.example.mealplannerapp.Home.presenter.HomePresenterImpl;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.view.HomeView;
import com.example.mealplannerapp.meal.view.MealDetailsFragment;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private ImageView mealImage, logoutIcon;
    private TextView mealTitle, mealId, usernameTextView;
    private CardView mealCard;
    private String mealIdValue;
    private HomePresenter homePresenter;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        mealId = view.findViewById(R.id.mealId);
        mealCard = view.findViewById(R.id.mealCard);
        logoutIcon = view.findViewById(R.id.logoutIcon);

        if (getArguments() != null) {
            String username = getArguments().getString("USERNAME", "User");
            usernameTextView.setText("Hi, " + username + "!");
        }

        Repository homeRepository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext()),
                new FirestoreDataSource()
        );

        homePresenter = new HomePresenterImpl(this, homeRepository, requireContext());
        homePresenter.getMeals();

        mealCard.setOnClickListener(v -> {
            if (mealIdValue != null) {
                MealDetailsFragment mealDetailsFragment = new MealDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MEAL_ID", mealIdValue);
                mealDetailsFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, mealDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(requireContext(), "Meal ID is not available", Toast.LENGTH_SHORT).show();
            }
        });

        logoutIcon.setOnClickListener(v -> homePresenter.logoutUser());

        return view;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);
            mealIdValue = meal.getIdMeal();
            mealTitle.setText(meal.getStrMeal());
            mealId.setText("Meal ID: " + mealIdValue);
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);
        } else {
            showErrMsg("No meal found");
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }
}
