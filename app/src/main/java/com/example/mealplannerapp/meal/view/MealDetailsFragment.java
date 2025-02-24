package com.example.mealplannerapp.meal.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenter;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenterImpl;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements HomeView, OnFavMealClickListener, MealTypeDialogFragment.OnMealTypeSelectedListener {
    private MealDetailsPresenter presenter;
    private ImageView mealImage, btnAddToFav;
    private TextView mealName, mealCategoryArea, mealInstructions, mealIngredients;
    private Button playVideoButton, btnAddToCalendar;
    private WebView youtubeWebView;
    private String mealId;
    private Meal currentMeal;
    private boolean isGuest;

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
                LocalDataSource.getInstance(requireContext()),
                new FirestoreDataSource()
        );

        presenter = new MealDetailsPresenterImpl(this, repository);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        isGuest = sharedPreferences.getBoolean("isGuest", false);

        if (getArguments() != null && getArguments().containsKey("MEAL_ID")) {
            mealId = getArguments().getString("MEAL_ID");
            presenter.getMealById(mealId);
        } else {
            Toast.makeText(requireContext(), "Meal ID is missing", Toast.LENGTH_SHORT).show();
        }

        btnAddToFav.setOnClickListener(v -> {
            if (isGuest) {
                Toast.makeText(requireContext(), "Guest mode: Cannot add to favorites", Toast.LENGTH_SHORT).show();
            } else if (currentMeal != null) {
                onFavMealClick(currentMeal);
            } else {
                Toast.makeText(requireContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddToCalendar.setOnClickListener(v -> {
            if (isGuest) {
                Toast.makeText(requireContext(), "Guest mode: Cannot add to calendar", Toast.LENGTH_SHORT).show();
            } else if (currentMeal != null) {
                openMealTypeDialog();
            } else {
                Toast.makeText(requireContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
            }
        });

        playVideoButton.setOnClickListener(v -> {
            if (currentMeal != null && currentMeal.getStrYoutube() != null && !currentMeal.getStrYoutube().isEmpty()) {
                String videoId = extractYouTubeVideoId(currentMeal.getStrYoutube());
                if (videoId != null) {
                    String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&rel=0&showinfo=0";
                    youtubeWebView.getSettings().setJavaScriptEnabled(true);
                    youtubeWebView.getSettings().setDomStorageEnabled(true);
                    youtubeWebView.setWebViewClient(new WebViewClient());
                    youtubeWebView.loadUrl(embedUrl);
                    youtubeWebView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "No video available for this meal", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String extractYouTubeVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null) {
            String regex = "v=([^&]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group(1);
            }
        }
        return videoId;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (!meals.isEmpty()) {
            Meal meal = meals.get(0);
            currentMeal = meal;

            mealName.setText(meal.getStrMeal());
            mealCategoryArea.setText(meal.getStrCategory() + " - " + meal.getStrArea());
            mealInstructions.setText(meal.getStrInstructions());

            Glide.with(requireContext())
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.background)
                    .into(mealImage);
        } else {
            Toast.makeText(requireContext(), "No meal found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealTypeSelected(String mealType, String date) {
        if (currentMeal != null) {
            ScheduledMeal scheduledMeal = new ScheduledMeal(date, mealType, currentMeal);

            presenter.addMealToCalendar(currentMeal, mealType, date);
            presenter.addPlannedMealToFirestore(scheduledMeal);

            Toast.makeText(requireContext(), "Meal added to " + mealType + " on " + date, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Meal data is missing", Toast.LENGTH_SHORT).show();
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
        presenter.addMealToFirestore(meal);
        Toast.makeText(requireContext(), "Meal added to favorite", Toast.LENGTH_SHORT).show();
    }
}
