package com.example.mealplannerapp.Home.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.meal.models.OnScheduledMealClickListener;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;

import java.util.ArrayList;
import java.util.List;

public class ScheduledMealsAdapter extends RecyclerView.Adapter<ScheduledMealsAdapter.ViewHolder> {
    private List<ScheduledMeal> scheduledMeals = new ArrayList<>();
    private final OnScheduledMealClickListener listener;

    public ScheduledMealsAdapter(OnScheduledMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<ScheduledMeal> meals) {
        if (meals != null) {
            this.scheduledMeals.clear();
            this.scheduledMeals.addAll(meals);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheduled_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduledMeal scheduledMeal = scheduledMeals.get(position);

        holder.lottieLoading.setVisibility(View.VISIBLE);
        holder.scheduledMealImage.setVisibility(View.INVISIBLE);
        holder.scheduledMealTitle.setText(scheduledMeal.getMeal().getStrMeal());

        if (scheduledMeal.getMeal() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(scheduledMeal.getMeal().getStrMealThumb())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.lottieLoading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                            holder.lottieLoading.setVisibility(View.GONE);
                            holder.scheduledMealImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.scheduledMealImage);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onScheduledMealClick(scheduledMeal.getMeal().getIdMeal());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return scheduledMeals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView scheduledMealImage;
        TextView scheduledMealTitle;
        LottieAnimationView lottieLoading;

        public ViewHolder(View itemView) {
            super(itemView);
            scheduledMealImage = itemView.findViewById(R.id.scheduledMealImage);
            lottieLoading = itemView.findViewById(R.id.lottieLoading);
            scheduledMealTitle = itemView.findViewById(R.id.scheduledMealTitle);
        }
    }
}
