package com.example.mealplannerapp.Home.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.mealplannerapp.Authentication.LoginActivity;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.Home.view.HomeView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
    private final HomeView homeView;
    private final Repository repository;
    private final Context context;
    private final LocationManager locationManager;

    public HomePresenterImpl(HomeView homeView, Repository homeRepository, Context context) {
        this.homeView = homeView;
        this.repository = homeRepository;
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void getMeals() {
        repository.getRandomMeal()
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
                repository.clearAllScheduledMeals(),
                repository.clearAllFavoriteMeals()
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

    @Override
    public void getScheduledMeals() {
        repository.getAllScheduledMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeView::showScheduledMeals, throwable -> homeView.showErrMsg("No scheduled meals available"));
    }

    @Override
    public void getUserLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            homeView.showErrMsg("Location permission required.");
            return;
        }

        Location location = null;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (location != null) {
            getCountryFromLocation(location.getLatitude(), location.getLongitude());
        } else {
            homeView.showErrMsg("Unable to get location. Requesting update...");
            requestLocationUpdates();
        }
    }


    private void requestLocationUpdates() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            homeView.showErrMsg("Location permission required.");
            return;
        }

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getCountryFromLocation(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, android.os.Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {
                homeView.showErrMsg("Location provider disabled.");
            }
        }, null);
    }


    private void getCountryFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses == null || addresses.isEmpty()) {
                homeView.showErrMsg("Unable to retrieve country. Try again later.");
                return;
            }

            String country = addresses.get(0).getCountryName();
            if ("Egypt".equalsIgnoreCase(country)) {
                fetchEgyptianMeals();
            } else {
                homeView.showErrMsg("No meals available for your country.");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error retrieving country", e);
            homeView.showErrMsg("Error retrieving country.");
        }
    }

    private void fetchEgyptianMeals() {
        repository.getMealsByCountry("Egyptian")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> homeView.showLocationMeals(meals),
                        throwable -> {
                            Log.e(TAG, "Error fetching meals for location", throwable);
                            homeView.showErrMsg("Failed to fetch meals.");
                        }
                );
    }
}
