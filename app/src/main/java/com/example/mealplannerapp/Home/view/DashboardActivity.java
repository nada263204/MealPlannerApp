package com.example.mealplannerapp.Home.view;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.mealplannerapp.network.NetworkUtils;
import com.example.mealplannerapp.schedule.view.CalendarFragment;
import com.example.mealplannerapp.favorite.view.FavoritesFragment;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ConstraintLayout layout = findViewById(R.id.main_layout);
        layout.setBackgroundResource(R.drawable.background);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        String username = getIntent().getStringExtra("USERNAME");

        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            boolean isConnected = NetworkUtils.isNetworkAvailable(this);

            if (itemId == R.id.nav_home) {
                if (isConnected) {
                    selectedFragment = new HomeFragment();
                    selectedFragment.setArguments(bundle);
                } else {
                    showNetworkRestrictedMessage();
                }
            } else if (itemId == R.id.nav_search) {
                if (isConnected) {
                    selectedFragment = new SearchFragment();
                } else {
                    showNetworkRestrictedMessage();
                }
            } else if (itemId == R.id.nav_calendar) {
                selectedFragment = new CalendarFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

    private void showNetworkRestrictedMessage() {
        Toast.makeText(this, "No internet connection. Only Calendar & Favorites are available.", Toast.LENGTH_SHORT).show();
    }
}
