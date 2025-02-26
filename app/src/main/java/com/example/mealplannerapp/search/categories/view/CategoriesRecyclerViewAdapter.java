package com.example.mealplannerapp.search.categories.view;

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
import com.example.mealplannerapp.search.categories.models.Category;
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.view.OnIngredientClickListener;

import java.util.List;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoryViewHolder> {
    private List<Category> categoriesList;
    private OnCategoryClickListener listener;

    public CategoriesRecyclerViewAdapter(List<Category> categoriesList,OnCategoryClickListener listener) {
        this.categoriesList = categoriesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_search_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoriesList.get(position);

        holder.tvCategoryName.setText(category.getStrCategory());
        holder.bind(category, listener);

        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.background)
                .into(holder.ivCategoryImage);
    }

    @Override
    public int getItemCount() {
        return categoriesList != null ? categoriesList.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView ivCategoryImage;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.categoryName);
            ivCategoryImage = itemView.findViewById(R.id.categoryImage);
        }

        public void bind(Category category, OnCategoryClickListener clickListener) {
            tvCategoryName.setText(category.getStrCategory());

            Glide.with(itemView.getContext())
                    .load(category.getStrCategoryThumb())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.background)
                    .into(ivCategoryImage);

            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onCategoryClick(category);
                }
            });
        }
    }
}
