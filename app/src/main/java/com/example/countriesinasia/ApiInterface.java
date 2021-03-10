package com.example.countriesinasia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("asia")
    Call<List<Country>> getList();

}
