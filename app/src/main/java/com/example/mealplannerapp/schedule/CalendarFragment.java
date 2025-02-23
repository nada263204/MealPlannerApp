package com.example.mealplannerapp.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment implements CalendarView {

    private RecyclerView breakfastRecycler, lunchRecycler, dinnerRecycler;
    private MealAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private CalendarPresenter presenter;
    private TextView dateTextView;
    private String selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        dateTextView = view.findViewById(R.id.dateTextView);
        breakfastRecycler = view.findViewById(R.id.breakfastRecycler);
        lunchRecycler = view.findViewById(R.id.lunchRecycler);
        dinnerRecycler = view.findViewById(R.id.dinnerRecycler);

        breakfastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        lunchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dinnerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        breakfastAdapter = new MealAdapter();
        lunchAdapter = new MealAdapter();
        dinnerAdapter = new MealAdapter();


        breakfastRecycler.setAdapter(breakfastAdapter);
        lunchRecycler.setAdapter(lunchAdapter);
        dinnerRecycler.setAdapter(dinnerAdapter);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext().getApplicationContext())
        );

        presenter = new CalendarPresenterImpl(repository, this);

        selectedDate = getCurrentDate();
        dateTextView.setText(selectedDate);
        dateTextView.setOnClickListener(v -> openDatePicker());

        presenter.loadMealsForDate(selectedDate);

        return view;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(new Date());
    }

    @Override
    public void showMealsForDate(List<ScheduledMeal> meals) {
        breakfastAdapter.updateData(presenter.filterMealsByType(meals, "Breakfast", selectedDate));
        lunchAdapter.updateData(presenter.filterMealsByType(meals, "Lunch", selectedDate));
        dinnerAdapter.updateData(presenter.filterMealsByType(meals, "Dinner", selectedDate));
    }


    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void openDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate = new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(new Date(selection));
            dateTextView.setText(selectedDate);
            presenter.loadMealsForDate(selectedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

}
