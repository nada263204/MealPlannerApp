package com.example.mealplannerapp.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements OnDeleteClickListener, FavoriteMealView {

    private RecyclerView recyclerView;
    private FavoriteMealAdapter favoriteMealAdapter;
    private FavoriteMealPresenter presenter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteMealAdapter = new FavoriteMealAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(favoriteMealAdapter);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext().getApplicationContext())
        );

        presenter = new FavoriteMealPresenterImpl(this, repository);
        presenter.getFavorite();

        return view;
    }

    @Override
    public void showFavoriteMeals(List<Meal> meals) {
        favoriteMealAdapter.updateData(meals);
    }

    @Override
    public void showErrMsg(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteFavorite(meal);
        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
    }
}
