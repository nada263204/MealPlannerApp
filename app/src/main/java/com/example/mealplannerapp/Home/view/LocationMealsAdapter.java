package com.example.mealplannerapp.Home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.meal.models.MealBy;

import java.util.List;

public class LocationMealsAdapter extends RecyclerView.Adapter<LocationMealsAdapter.MealViewHolder> {
    private final Context context;
    private List<MealBy> mealList;
    private final OnMealClickListener listener;

    public interface OnMealClickListener {
        void onMealClick(MealBy meal);
    }

    public LocationMealsAdapter(Context context, List<MealBy> mealList, OnMealClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scheduled_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealBy meal = mealList.get(position);
        holder.mealName.setText(meal.getName());

        Glide.with(context)
                .load(meal.getThumbnail())
                .placeholder(R.drawable.background)
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> listener.onMealClick(meal));
    }

    @Override
    public int getItemCount() {
        return mealList == null ? 0 : mealList.size();
    }

    public void updateMeals(List<MealBy> newMeals) {
        this.mealList = newMeals;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.scheduledMealTitle);
            mealImage = itemView.findViewById(R.id.scheduledMealImage);
        }
    }
}
