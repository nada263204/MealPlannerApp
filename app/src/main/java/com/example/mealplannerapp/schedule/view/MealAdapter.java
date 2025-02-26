package com.example.mealplannerapp.schedule.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.meal.models.OnScheduledMealClickListener;
import com.example.mealplannerapp.schedule.model.OnMealDeleteClickListener;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<ScheduledMeal> meals;
    private OnMealDeleteClickListener deleteClickListener;
    private final OnScheduledMealClickListener listener;

    public MealAdapter(OnMealDeleteClickListener deleteClickListener, OnScheduledMealClickListener listener) {
        this.listener = listener;
        this.meals = new ArrayList<>();
        this.deleteClickListener = deleteClickListener;
    }

    public void updateData(List<ScheduledMeal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    public void removeMeal(ScheduledMeal meal) {
        int position = meals.indexOf(meal);
        if (position != -1) {
            meals.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        ScheduledMeal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        private TextView mealName;
        private ImageView mealImage;
        private ImageView deleteButton;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tv_title);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            mealImage = itemView.findViewById(R.id.iv_meal);

            deleteButton.setOnClickListener(v -> {
                if (deleteClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    deleteClickListener.onMealDelete(meals.get(getAdapterPosition()));
                }
            });

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onScheduledMealClick(meals.get(getAdapterPosition()).getMeal().getIdMeal());
                }
            });
        }

        public void bind(ScheduledMeal meal) {
            mealName.setText(meal.getMeal().getStrMeal());

            Glide.with(itemView.getContext())
                    .load(meal.getMeal().getStrMealThumb())
                    .placeholder(R.drawable.background)
                    .into(mealImage);
        }
    }
}
