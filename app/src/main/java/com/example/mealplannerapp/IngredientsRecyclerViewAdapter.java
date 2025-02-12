package com.example.mealplannerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientsList;

    public IngredientsRecyclerViewAdapter(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_search_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientsList.get(position);

        holder.tvIngredientName.setText(ingredient.getStrIngredient());

        Glide.with(holder.itemView.getContext())
                .load(ingredient.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.background)
                .into(holder.ivIngredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredientsList != null ? ingredientsList.size() : 0;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName;
        ImageView ivIngredientImage;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.ingredientName);
            ivIngredientImage = itemView.findViewById(R.id.ingredientImage);
        }
    }
}



class IngredientRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle, tvIngredients;
    ImageView imageView;

    public IngredientRecyclerViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvIngredients = itemView.findViewById(R.id.tv_ingredients);
        imageView = itemView.findViewById(R.id.iv_meal);
    }
}
