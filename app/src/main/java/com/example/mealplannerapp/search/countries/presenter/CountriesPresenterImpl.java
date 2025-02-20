package com.example.mealplannerapp.search.countries.presenter;

import com.example.mealplannerapp.data.repo.Repository;
import com.example.mealplannerapp.search.countries.models.CountriesCallBack;
import com.example.mealplannerapp.search.countries.models.Country;
import com.example.mealplannerapp.search.countries.view.CountriesView;
import com.example.mealplannerapp.search.ingedients.view.IngredientsView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenterImpl implements CountriesPresenter, CountriesCallBack {
    private CountriesView _view;
    private Repository _repo;

    public CountriesPresenterImpl(CountriesView view,Repository repo){
        this._view = view;
        this._repo =repo;
    }
    @Override
    public void onSuccessResult(List<Country> countries) {
        _view.showCountries(countries);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }

    @Override
    public void getCountries() {
        _repo.getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        country -> _view.showCountries(country),
                        error -> _view.showErrMsg(error.getMessage())
                );
    }
}
