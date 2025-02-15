package com.example.mealplannerapp.Home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.mealplannerapp.Meal;
import com.example.mealplannerapp.MealDetailsFragment;
import com.example.mealplannerapp.R;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    public static final String url = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "HomeFragment";

    public HomeFragment(){}

    private ImageView mealImage;
    private TextView mealTitle, mealId, usernameTextView;
    private CardView mealCard;
    private String mealIdValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("USERNAME", "User");
            usernameTextView.setText("Hi, " + username + "!");
        }

        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        mealId = view.findViewById(R.id.mealId);
        mealCard = view.findViewById(R.id.mealCard);

        fetchMeal();

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
                Toast.makeText(getActivity(), "Meal ID is not available", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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

                        Glide.with(requireContext())
                                .load(meal.getStrMealThumb())
                                .into(mealImage);
                    } else {
                        Toast.makeText(getActivity(), "No meal found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No meal found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable throwable) {
                Log.e(TAG, "API Error", throwable);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
