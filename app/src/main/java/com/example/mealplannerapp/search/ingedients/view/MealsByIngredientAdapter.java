package com.example.mealplannerapp.search.ingedients.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.meal.models.OnMealClickListener;
import com.example.mealplannerapp.meal.models.MealBy;
import java.util.List;

public class MealsByIngredientAdapter extends RecyclerView.Adapter<MealViewHolder> {
    private List<MealBy> mealsList;
    private OnMealClickListener listener;

    public MealsByIngredientAdapter(List<MealBy> mealsList, OnMealClickListener listener) {
        this.mealsList = mealsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealBy meal = mealsList.get(position);
        holder.mealName.setText(meal.getName());

        Glide.with(holder.itemView.getContext())
                .load(meal.getThumbnail())
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.OnMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    public void updateMeals(List<MealBy> newMeals) {
        this.mealsList.clear();
        this.mealsList.addAll(newMeals);
        notifyItemRangeChanged(0, newMeals.size());
    }
}

class MealViewHolder extends RecyclerView.ViewHolder {
    TextView mealName;
    ImageView mealImage;

    public MealViewHolder(View itemView) {
        super(itemView);
        mealName = itemView.findViewById(R.id.tv_title);
        mealImage = itemView.findViewById(R.id.iv_meal);
    }
}
