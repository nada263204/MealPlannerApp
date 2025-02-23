package com.example.mealplannerapp.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.meal.models.Meal;
import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<ScheduledMeal> meals;

    public MealAdapter() {
        this.meals = new ArrayList<>();
    }

    public void updateData(List<ScheduledMeal> newMeals) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MealDiffCallback(this.meals, newMeals));
        this.meals.clear();
        this.meals.addAll(newMeals);
        diffResult.dispatchUpdatesTo(this);
    }



    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
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

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        private TextView mealName;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tv_title);
        }

        public void bind(ScheduledMeal meal) {
            mealName.setText(meal.getMeal().getStrMeal());
        }
    }

    public class MealDiffCallback extends DiffUtil.Callback {
        private final List<ScheduledMeal> oldList;
        private final List<ScheduledMeal> newList;

        public MealDiffCallback(List<ScheduledMeal> oldList, List<ScheduledMeal> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

}
