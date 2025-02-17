package com.example.mealplannerapp.search.ingedients.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplannerapp.R;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.view.OnIngredientClickListener;
import java.util.List;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientViewHolder> {
    private List<Ingredient> ingredientsList;
    private OnIngredientClickListener clickListener;

    public IngredientsRecyclerViewAdapter(List<Ingredient> ingredientsList, OnIngredientClickListener clickListener) {
        this.ingredientsList = ingredientsList;
        this.clickListener = clickListener;
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
        holder.bind(ingredient, clickListener);
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
            tvIngredientName = itemView.findViewById(R.id.categoryName);
            ivIngredientImage = itemView.findViewById(R.id.categoryImage);
        }

        public void bind(Ingredient ingredient, OnIngredientClickListener clickListener) {
            tvIngredientName.setText(ingredient.getStrIngredient());

            Glide.with(itemView.getContext())
                    .load(ingredient.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.background)
                    .into(ivIngredientImage);

            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onIngredientClick(ingredient);
                }
            });
        }
    }
}
