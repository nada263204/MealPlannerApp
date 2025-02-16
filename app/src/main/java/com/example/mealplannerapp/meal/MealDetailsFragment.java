package com.example.mealplannerapp.meal;

import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.models.MealByIDService;
import com.example.mealplannerapp.meal.models.MealResponse;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenter;
import com.example.mealplannerapp.meal.presenter.MealDetailsPresenterImpl;
import com.example.mealplannerapp.meal.view.MealView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDetailsFragment extends Fragment implements MealView {
    private MealDetailsPresenter presenter;
    private ImageView mealImage;
    private TextView mealName, mealCategoryArea, mealInstructions, mealIngredients;
    private Button playVideoButton;
    private WebView youtubeWebView;
    private String mealId;

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
        Repository repository = Repository.getInstance(RemoteDataSource.getInstance());

        presenter = new MealDetailsPresenterImpl(this,repository);

        if (getArguments() != null) {
            mealId = getArguments().getString("MEAL_ID");
            presenter.getMealById(mealId);
        } else {
            Toast.makeText(getActivity(), "Meal ID is missing", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (!meals.isEmpty()) {
            Meal meal = meals.get(0);

            mealName.setText(meal.getStrMeal());
            mealCategoryArea.setText(meal.getStrCategory() + " - " + meal.getStrArea());
            mealInstructions.setText(meal.getStrInstructions());

            Glide.with(requireContext()).load(meal.getStrMealThumb()).into(mealImage);

            StringBuilder ingredientsList = new StringBuilder();
            for (int i = 1; i <= 20; i++) {
                String ingredient = meal.getIngredient(i);
                String measure = meal.getMeasure(i);
                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredientsList.append(ingredient).append(" - ").append(measure).append("\n");
                }
            }
            mealIngredients.setText(ingredientsList.toString());

            if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
                playVideoButton.setOnClickListener(v -> {
                    String videoId = meal.getStrYoutube().split("v=")[1];
                    String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
                    youtubeWebView.getSettings().setJavaScriptEnabled(true);
                    youtubeWebView.loadUrl(embedUrl);
                    youtubeWebView.setVisibility(View.VISIBLE);
                });
            } else {
                playVideoButton.setEnabled(false);
                playVideoButton.setText("No Video Available");
            }
        } else {
            Toast.makeText(getActivity(), "No meal found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrMsg(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
