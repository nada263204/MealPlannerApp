package com.example.mealplannerapp.meal.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mealplannerapp.R;

import java.util.Calendar;

public class MealTypeDialogFragment extends DialogFragment {

    private Spinner mealTypeSpinner;
    private ImageView btnSelectDate;
    private TextView selectedDateTextView;
    private OnMealTypeSelectedListener listener;
    private String selectedDate = "";

    public interface OnMealTypeSelectedListener {
        void onMealTypeSelected(String mealType, String date);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnMealTypeSelectedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new RuntimeException("Must implement OnMealTypeSelectedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_meal_type_dialog, null);

        mealTypeSpinner = view.findViewById(R.id.mealTypeSpinner);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        String[] mealTypes = {"Breakfast", "Lunch", "Dinner"};
        mealTypeSpinner.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, mealTypes));

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> {
            String selectedMealType = mealTypeSpinner.getSelectedItem().toString();

            if (selectedDate.isEmpty()) {
                Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (listener != null) {
                listener.onMealTypeSelected(selectedMealType, selectedDate);
            }
            dismiss();
        });

        btnCancel.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    selectedDateTextView.setText(selectedDate);

                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
