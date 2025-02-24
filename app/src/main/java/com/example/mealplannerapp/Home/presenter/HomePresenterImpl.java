package com.example.mealplannerapp.Home.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.mealplannerapp.Authentication.LoginActivity;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.view.HomeView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
    private final HomeView homeView;
    private final Repository homeRepository;
    private final Context context;

    public HomePresenterImpl(HomeView homeView, Repository homeRepository, Context context) {
        this.homeView = homeView;
        this.homeRepository = homeRepository;
        this.context = context;
    }

    @Override
    public void getMeals() {
        homeRepository.getRandomMeal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    if (!meals.isEmpty()) {
                        homeView.showMeal(meals);
                    } else {
                        homeView.showErrMsg("No meals found");
                    }
                }, throwable -> {
                    Log.e(TAG, "Error fetching meals", throwable);
                    homeView.showErrMsg("Failed to fetch meals: " + throwable.getMessage());
                });
    }

    @Override
    public void logoutUser() {
        Completable clearMeals = Completable.mergeArray(
                homeRepository.clearAllScheduledMeals(),
                homeRepository.clearAllFavoriteMeals()
        );

        clearMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    FirebaseAuth.getInstance().signOut();

                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }, throwable -> {
                    homeView.showErrMsg("Failed to clear meals: " + throwable.getMessage());
                });
    }
}
