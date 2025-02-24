package com.example.mealplannerapp.search.ingedients.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplannerapp.R;
import com.example.mealplannerapp.data.FirestoreDataSource.FirestoreDataSource;
import com.example.mealplannerapp.data.localDataSource.LocalDataSource;
import com.example.mealplannerapp.data.remoteDataSource.RemoteDataSource;
import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.meal.models.MealBy;
import com.example.mealplannerapp.meal.view.MealDetailsFragment;
import com.example.mealplannerapp.meal.view.OnMealClickListener;
import com.example.mealplannerapp.search.ingedients.presenter.MealByIngredientPresenter;
import com.example.mealplannerapp.search.ingedients.presenter.MealsByIngredientPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MealsByIngredientFragment extends Fragment implements MealsByIngredientView, OnMealClickListener {

    private RecyclerView recyclerView;
    private MealsByIngredientAdapter adapter;
    private MealByIngredientPresenter presenter;
    private EditText searchInput;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private List<MealBy> allMeals = new ArrayList<>();

    public MealsByIngredientFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Repository repository = Repository.getInstance(
                RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(getContext()), new FirestoreDataSource()
        );
        presenter = new MealsByIngredientPresenterImpl(this, repository);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals_by_ingredient, container, false);

        recyclerView = view.findViewById(R.id.recycler_meals_by_ingredient);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MealsByIngredientAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        searchInput = view.findViewById(R.id.et_search_ingredient);

        presenter.getMealsByIngredient("");

        setupSearchObservable();

        return view;
    }

    private void setupSearchObservable() {
        disposables.add(
                Observable.create(emitter -> searchInput.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                emitter.onNext(charSequence.toString().trim());
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {}
                        }))
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .map(text -> text.toString().trim())

                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::filterMeals, throwable -> showToast("Error filtering meals"))
        );
    }

    private void filterMeals(String query) {
        List<MealBy> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            filteredList.addAll(allMeals);
        } else {
            for (MealBy meal : allMeals) {
                if (meal.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(meal);
                }
            }
        }

        adapter.updateMeals(filteredList);
    }

    @Override
    public void showMeals(List<MealBy> meals) {
        if (meals == null || meals.isEmpty()) {
            showToast("No meals found for this ingredient");
            return;
        }
        allMeals.clear();
        allMeals.addAll(meals);
        adapter.updateMeals(meals);
    }

    @Override
    public void showError(String message) {
        showToast("Error: " + message);
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnMealClick(MealBy meal) {
        if (meal == null || meal.getId() == null) {
            showToast("Invalid meal selected");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("MEAL_ID", meal.getId());

        MealDetailsFragment fragment = new MealDetailsFragment();
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }
}
