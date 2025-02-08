package com.example.mealplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    public static final String url = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "DashboardActivity";

    private ImageView mealImage;
    private TextView mealTitle, mealId;
    private CardView mealCard;
    private String mealIdValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String username = getIntent().getStringExtra("USERNAME");
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        if (username != null) {
            usernameTextView.setText("Hi, " + username);
        }

        mealImage = findViewById(R.id.mealImage);
        mealTitle = findViewById(R.id.mealTitle);
        mealId = findViewById(R.id.mealId);
        mealCard = findViewById(R.id.mealCard);

        fetchMeal();

        mealCard.setOnClickListener(view -> {
            if (mealIdValue != null) {
                Intent intent = new Intent(DashboardActivity.this, MealDetailActivity.class);
                intent.putExtra("MEAL_ID", mealIdValue);
                startActivity(intent);
            } else {
                Toast.makeText(DashboardActivity.this, "Meal ID is not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMeal() {
        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MealService mealService = retrofitInstance.create(MealService.class);
        Call<MealResponse> call = mealService.getMeal();

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    List<Meal> meals = response.body().getMeals();
                    if (!meals.isEmpty()) {
                        Meal meal = meals.get(0);

                        mealIdValue = meal.getIdMeal();

                        mealTitle.setText(meal.getStrMeal());
                        mealId.setText("Meal ID: " + mealIdValue);

                        Glide.with(DashboardActivity.this)
                                .load(meal.getStrMealThumb())
                                .into(mealImage);
                    } else {
                        Toast.makeText(DashboardActivity.this, "No meal found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "No meal found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.e(TAG, "API Error", throwable);
                Toast.makeText(DashboardActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
