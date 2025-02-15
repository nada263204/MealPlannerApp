package com.example.mealplannerapp.search.countries;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService {
    @GET("list.php?a=list")
    Call<CountryRespsonse> getCountries();
}
