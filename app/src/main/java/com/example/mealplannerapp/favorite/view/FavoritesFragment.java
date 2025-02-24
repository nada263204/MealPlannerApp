package com.example.mealplannerapp.favorite.view;

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
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.favorite.model.OnDeleteClickListener;
import com.example.mealplannerapp.favorite.presenter.FavoriteMealPresenter;
import com.example.mealplannerapp.favorite.presenter.FavoriteMealPresenterImpl;
import com.example.mealplannerapp.meal.models.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        favoriteMealAdapter = new FavoriteMealAdapter(requireContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(favoriteMealAdapter);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(requireContext()),
                new FirestoreDataSource()
        );

        presenter = new FavoriteMealPresenterImpl(this, repository);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkUserAndFetchFavorites();
    }

    private void checkUserAndFetchFavorites() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            presenter.getFavorite();
        } else {
            showErrMsg("User not authenticated");
        }
    }

    @Override
    public void showFavoriteMeals(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            favoriteMealAdapter.updateData(meals);
        } else {
            showErrMsg("No favorites found");
        }
    }

    @Override
    public void showErrMsg(String error) {
        if (isAdded()) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteFavorite(meal);
        showErrMsg("Removed from favorites");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter = null;
    }
}
