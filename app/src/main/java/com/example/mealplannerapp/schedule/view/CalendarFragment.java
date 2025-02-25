package com.example.mealplannerapp.schedule.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.schedule.model.OnMealDeleteClickListener;
import com.example.mealplannerapp.schedule.presenter.CalendarPresenter;
import com.example.mealplannerapp.schedule.presenter.CalendarPresenterImpl;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment implements PlanView, OnMealDeleteClickListener {
    private static final String TAG = "CalendarFragment";

    private RecyclerView breakfastRecycler, lunchRecycler, dinnerRecycler;
    private MealAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private CalendarPresenter presenter;
    private TextView dateTextView;
    private CalendarView calendarView;
    private String selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        dateTextView = view.findViewById(R.id.selectedDateText);
        calendarView = view.findViewById(R.id.calendarView);
        breakfastRecycler = view.findViewById(R.id.breakfastRecycler);
        lunchRecycler = view.findViewById(R.id.lunchRecycler);
        dinnerRecycler = view.findViewById(R.id.dinnerRecycler);

        breakfastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        lunchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dinnerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        breakfastAdapter = new MealAdapter(this);
        lunchAdapter = new MealAdapter(this);
        dinnerAdapter = new MealAdapter(this);

        breakfastRecycler.setAdapter(breakfastAdapter);
        lunchRecycler.setAdapter(lunchAdapter);
        dinnerRecycler.setAdapter(dinnerAdapter);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext().getApplicationContext()),
                new FirestoreDataSource()
        );

        presenter = new CalendarPresenterImpl(repository, this);

        selectedDate = getCurrentDate();
        dateTextView.setText(formatDateForDisplay(selectedDate));
        loadMeals(selectedDate);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            dateTextView.setText(formatDateForDisplay(selectedDate));
            loadMeals(selectedDate);
        });

        return view;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(new Date());
    }

    private String formatDateForDisplay(String date) {
        return "Selected Date: " + date;
    }

    private void loadMeals(String date) {
        presenter.loadMealsForDate(date);
    }

    @Override
    public void showMealsForDate(List<ScheduledMeal> meals) {
        if (meals == null || meals.isEmpty()) {
            Log.d(TAG, "No meals found in Room, checking Firestore...");
            presenter.loadMealsFromFirestore(selectedDate);
        } else {
            Log.d(TAG, "Meals loaded successfully from Room: " + meals.size());
            updateMealAdapters(meals);
        }
    }

    public void updateMealAdapters(List<ScheduledMeal> meals) {
        breakfastAdapter.updateData(presenter.filterMealsByType(meals, "Breakfast", selectedDate));
        lunchAdapter.updateData(presenter.filterMealsByType(meals, "Lunch", selectedDate));
        dinnerAdapter.updateData(presenter.filterMealsByType(meals, "Dinner", selectedDate));
    }

    @Override
    public void showError(String error) {
        try {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            Log.e(TAG, "Fragment not attached to context. Error: " + error);
        }
    }

    @Override
    public void onMealDelete(ScheduledMeal meal) {
        presenter.deleteScheduledMeal(meal);

        breakfastAdapter.removeMeal(meal);
        lunchAdapter.removeMeal(meal);
        dinnerAdapter.removeMeal(meal);
    }
}
