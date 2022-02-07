package com.example.myapplication;

import android.location.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AddrSearchService {
    @GET("v2/local/search/address.json")
    Call<Location> searchAddressList(@Query("query") String query, @Query("page") int page, @Query("size") int size, @Header("Authorization") String apikey);
}

