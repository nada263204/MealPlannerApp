package com.example.mealplannerapp.Home.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.Home.presenter.HomePresenter;
import com.example.mealplannerapp.Home.presenter.HomePresenterImpl;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.models.OnScheduledMealClickListener;
import com.example.mealplannerapp.meal.view.MealDetailsFragment;
import com.example.mealplannerapp.meal.view.OnMealClickListener;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;
import com.example.mealplannerapp.search.SearchFragment;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, LocationMealsAdapter.OnMealClickListener, OnScheduledMealClickListener {
    private ImageView mealImage, logoutIcon;
    private TextView mealTitle, mealId, usernameTextView;
    private CardView mealCard;
    private CardView noMealsCard;
    private LottieAnimationView lottieAnimation,lottieLocation;
    private TextView noMealsText;
    private String mealIdValue;
    private HomePresenter homePresenter;
    private RecyclerView scheduledMealsRecyclerView;
    private ScheduledMealsAdapter scheduledMealsAdapter;
    private CardView locationMealsCard;
    private TextView locationMealsText;
    private RecyclerView locationMealsRecyclerView;
    private LocationMealsAdapter locationMealsAdapter;
    private Handler autoScrollHandler = new Handler(Looper.getMainLooper());
    private int currentIndex = 0;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        mealCard = view.findViewById(R.id.mealCard);
        logoutIcon = view.findViewById(R.id.logoutIcon);
        noMealsCard = view.findViewById(R.id.noMealsCard);
        lottieAnimation = view.findViewById(R.id.lottieAnimation);
        noMealsText = view.findViewById(R.id.noMealsText);
        locationMealsCard = view.findViewById(R.id.locationMealsCard);
        lottieLocation = view.findViewById(R.id.lottieLocation);
        locationMealsText = view.findViewById(R.id.locationMealsText);
        locationMealsRecyclerView = view.findViewById(R.id.locationMealsRecyclerView);

        locationMealsAdapter = new LocationMealsAdapter(requireContext(), (List<MealBy>) null, this);
        locationMealsRecyclerView.setAdapter(locationMealsAdapter);
        scheduledMealsRecyclerView = view.findViewById(R.id.scheduledMealsRecyclerView);
        scheduledMealsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        scheduledMealsAdapter = new ScheduledMealsAdapter(this);
        scheduledMealsRecyclerView.setAdapter(scheduledMealsAdapter);

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
        homePresenter.getScheduledMeals();

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

        noMealsCard.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });


        logoutIcon.setOnClickListener(v -> homePresenter.logoutUser());
        locationMealsCard.setOnClickListener(v -> homePresenter.getUserLocation());

        return view;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);
            mealIdValue = meal.getIdMeal();
            mealTitle.setText(meal.getStrMeal());
            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);
        } else {
            showErrMsg("No meal found");
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void startAutoScroll() {
        if (scheduledMealsAdapter.getItemCount() <= 1) {
            return;
        }

        autoScrollHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scheduledMealsAdapter.getItemCount() > 1) {
                    currentIndex++;
                    if (currentIndex >= scheduledMealsAdapter.getItemCount()) {
                        currentIndex = 0;
                    }
                    scheduledMealsRecyclerView.smoothScrollToPosition(currentIndex);
                    autoScrollHandler.postDelayed(this, 2000);
                }
            }
        }, 2000);
    }



    @Override
    public void showScheduledMeals(List<ScheduledMeal> scheduledMeals) {
        if (scheduledMeals.isEmpty()) {
            scheduledMealsRecyclerView.setVisibility(View.GONE);
            noMealsCard.setVisibility(View.VISIBLE);
        } else {
            noMealsCard.setVisibility(View.GONE);
            scheduledMealsRecyclerView.setVisibility(View.VISIBLE);
            scheduledMealsAdapter.setMeals(scheduledMeals);

            if (scheduledMeals.size() > 1) {
                startAutoScroll();
            }
        }
    }

    @Override
    public void showLocationMeals(List<MealBy> meals) {
        if (meals.isEmpty()) {
            showErrMsg("No meals found for your country.");
        } else {
            locationMealsText.setVisibility(View.GONE);
            lottieLocation.setVisibility(View.GONE);
            locationMealsRecyclerView.setVisibility(View.VISIBLE);
            locationMealsAdapter.updateMeals(meals);
        }
    }



    @Override
    public void onMealClick(MealBy meal) {

    }

    @Override
    public void onScheduledMealClick(String mealId) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MEAL_ID", mealId);
        mealDetailsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mealDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
