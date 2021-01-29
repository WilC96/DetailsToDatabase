package com.example.retrofitwil.rest;


import com.example.retrofitwil.model.MovieModel;
import com.example.retrofitwil.model.MovieResult;

import java.util.Map;

import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    /*
    @GET("movie/top_rated")
    Observable<MovieModel> getTopRatedMovies(@Query("api_key") String apiKey, String user);

    @GET("movie/{id}")
    Observable<MovieModel> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
    */

    @GET("movie/top_rated")
    Observable<MovieResult> getMovieDetails(@QueryMap Map<String, String> param);
}
