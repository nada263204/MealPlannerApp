package com.example.mealplannerapp.favorite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.favorite.model.OnDeleteClickListener;
import com.example.mealplannerapp.favorite.model.OnFavoriteMealClickListener;
import com.example.mealplannerapp.meal.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteMealAdapter extends RecyclerView.Adapter<FavoriteMealAdapter.FavoriteMealViewHolder> {

    private Context context;
    private List<Meal> favoriteList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnFavoriteMealClickListener onFavoriteMealClickListener;

    public FavoriteMealAdapter(Context context, List<Meal> favoriteList,
                               OnDeleteClickListener onDeleteClickListener,
                               OnFavoriteMealClickListener onFavoriteMealClickListener) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onFavoriteMealClickListener = onFavoriteMealClickListener;
    }

    @NonNull
    @Override
    public FavoriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_meal_item, parent, false);
        return new FavoriteMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealViewHolder holder, int position) {
        Meal meal = favoriteList.get(position);
        holder.title.setText(meal.getStrMeal());
        Picasso.get().load(meal.getStrMealThumb()).into(holder.thumbnail);

        // Remove button click
        holder.btnRemove.setOnClickListener(view -> onDeleteClickListener.onDeleteClick(meal));

        // Item click for favorite meal
        holder.itemView.setOnClickListener(view -> onFavoriteMealClickListener.onFavoriteMealClick(meal.getIdMeal()));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void updateData(List<Meal> newFavoriteList) {
        this.favoriteList.clear();
        this.favoriteList.addAll(newFavoriteList);
        notifyDataSetChanged();
    }

    public static class FavoriteMealViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail, btnRemove;
        TextView title;

        public FavoriteMealViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_meal);
            title = itemView.findViewById(R.id.tv_title);
            btnRemove = itemView.findViewById(R.id.delete_btn);
        }
    }
}
