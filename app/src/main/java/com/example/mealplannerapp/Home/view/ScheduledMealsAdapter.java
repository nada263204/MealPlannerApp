package com.example.mealplannerapp.Home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.ArrayList;
import java.util.List;

public class ScheduledMealsAdapter extends RecyclerView.Adapter<ScheduledMealsAdapter.ViewHolder> {
    private List<ScheduledMeal> scheduledMeals = new ArrayList<>();

    public void setMeals(List<ScheduledMeal> meals) {
        this.scheduledMeals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheduled_meal, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = parent.getWidth();
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduledMeal scheduledMeal = scheduledMeals.get(position);

        if (scheduledMeal.getMeal() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(scheduledMeal.getMeal().getStrMealThumb())
                    .into(holder.scheduledMealImage);

            holder.scheduledMealTitle.setText(scheduledMeal.getMeal().getStrMeal());
        }
    }


    @Override
    public int getItemCount() {
        return scheduledMeals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView scheduledMealImage;
        TextView scheduledMealTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            scheduledMealImage = itemView.findViewById(R.id.scheduledMealImage);
            scheduledMealTitle = itemView.findViewById(R.id.scheduledMealTitle);
        }
    }

}
