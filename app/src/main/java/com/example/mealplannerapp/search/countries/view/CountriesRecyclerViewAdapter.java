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
import com.example.mealplannerapp.search.ingedients.models.Ingredient;
import com.example.mealplannerapp.search.ingedients.view.OnIngredientClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountryViewHolder> {
    private List<Country> countriesList;
    private final Map<String, Integer> countryThumbnails;
    private OnCountryClickListener listener;

    public CountriesRecyclerViewAdapter(List<Country> countriesList,OnCountryClickListener listener) {
        this.countriesList = countriesList;
        this.listener =listener;
        countryThumbnails = initializeThumbnails();
    }

    private Map<String, Integer> initializeThumbnails() {
        Map<String, Integer> thumbnails = new HashMap<>();
        thumbnails.put("American", R.drawable.america);
        thumbnails.put("British", R.drawable.britain);
        thumbnails.put("Canadian", R.drawable.canada);
        thumbnails.put("Chinese", R.drawable.china);
        thumbnails.put("Croatian", R.drawable.coratia);
        thumbnails.put("Dutch", R.drawable.dutch);
        thumbnails.put("Egyptian", R.drawable.egypt);
        thumbnails.put("Filipino", R.drawable.philippine);
        thumbnails.put("French", R.drawable.france);
        thumbnails.put("Greek", R.drawable.greece);
        thumbnails.put("Indian", R.drawable.india);
        thumbnails.put("Irish", R.drawable.ireland);
        thumbnails.put("Italian", R.drawable.italy);
        thumbnails.put("Jamaican", R.drawable.jamaica);
        thumbnails.put("Japanese", R.drawable.japan);
        thumbnails.put("Kenyan", R.drawable.kenya);
        thumbnails.put("Malaysian", R.drawable.malaysia);
        thumbnails.put("Mexican", R.drawable.mexico);
        thumbnails.put("Moroccan", R.drawable.morocco);
        thumbnails.put("Polish", R.drawable.poland);
        thumbnails.put("Portuguese", R.drawable.portugal);
        thumbnails.put("Russian", R.drawable.russia);
        thumbnails.put("Spanish", R.drawable.spain);
        thumbnails.put("Thai", R.drawable.thailand);
        thumbnails.put("Tunisian", R.drawable.tunisia);
        thumbnails.put("Turkish", R.drawable.turkey);
        thumbnails.put("Ukrainian", R.drawable.ukraine);
        thumbnails.put("Uruguayan", R.drawable.uruguay);
        thumbnails.put("Vietnamese", R.drawable.vietnam);
        return thumbnails;
    }

    public void updateData(List<Country> newCountriesList) {
        if (newCountriesList != null) {
            this.countriesList = newCountriesList;
            notifyDataSetChanged();
        }
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
        holder.bind(country, listener);

        int thumbnailResId = countryThumbnails.getOrDefault(country.getStrArea(), R.drawable.background);
        holder.ivCountryFlag.setImageResource(thumbnailResId);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCountryClick(country);
            }
        });
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

        public void bind(Country country, OnCountryClickListener clickListener) {
            tvCountryName.setText(country.getStrArea());

            Glide.with(itemView.getContext())
                    .load(country.getFlagUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.background)
                    .into(ivCountryFlag);

            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onCountryClick(country);
                }
            });
        }
    }
}
