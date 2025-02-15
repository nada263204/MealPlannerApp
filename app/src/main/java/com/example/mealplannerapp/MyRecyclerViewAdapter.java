package com.example.mealplannerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mealplannerapp.meal.models.Meal;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {
    private List<Meal> mealList;
    private RecyclerView mealRecyclerView;

    public MyRecyclerViewAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        holder.tvTitle.setText(meal.getStrMeal());
        holder.tvIngredients.setText(meal.getStrInstructions());


    }

    @Override
    public int getItemCount() {
        return mealList != null ? mealList.size() : 0;
    }
}

class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle, tvIngredients;
    ImageView imageView;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvIngredients = itemView.findViewById(R.id.tv_ingredients);
        imageView = itemView.findViewById(R.id.iv_meal);
    }
}
