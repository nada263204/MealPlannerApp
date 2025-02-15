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
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.MealDetailsFragment;
import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.meal.presenter.MealPresenter;
import com.example.mealplannerapp.meal.presenter.MealPresenterImpl;
import com.example.mealplannerapp.meal.view.MealView;

import java.util.List;

public class HomeFragment extends Fragment implements MealView {
    private static final String TAG = "HomeFragment";

    private ImageView mealImage;
    private TextView mealTitle, mealId, usernameTextView;
    private CardView mealCard;
    private String mealIdValue;
    private MealPresenter mealPresenter;

    public HomeFragment() {}

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

        // Initialize MVP components
        Repository repository = Repository.getInstance(RemoteDataSource.getInstance());
        mealPresenter = new MealPresenterImpl(this, repository);

        mealPresenter.getMeals();

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

    @Override
    public void showMeal(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);

            mealIdValue = meal.getIdMeal();

            mealTitle.setText(meal.getStrMeal());
            mealId.setText("Meal ID: " + mealIdValue);

            Glide.with(requireContext())
                    .load(meal.getStrMealThumb())
                    .into(mealImage);
        } else {
            showErrMsg("No meal found");
        }
    }

    @Override
    public void showErrMsg(String error) {
        Log.e(TAG, "Error: " + error);
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
