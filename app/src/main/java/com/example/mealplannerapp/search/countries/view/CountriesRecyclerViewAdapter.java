package com.example.mealplannerapp.search.countries.view;


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
import com.example.mealplannerapp.search.countries.models.Country;

import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountryViewHolder> {
    private List<Country> countriesList;

    public CountriesRecyclerViewAdapter(List<Country> countriesList) {
        this.countriesList = countriesList;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_search_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countriesList.get(position);

        holder.tvCountryName.setText(country.getStrArea());

        Glide.with(holder.itemView.getContext())
                .load(country.getFlagUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.background)
                .into(holder.ivCountryFlag);
    }

    @Override
    public int getItemCount() {
        return countriesList != null ? countriesList.size() : 0;
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName;
        ImageView ivCountryFlag;

        public CountryViewHolder(View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.categoryName);
            ivCountryFlag = itemView.findViewById(R.id.categoryImage);
        }
    }
}

