package com.example.retrofitwithmvvmandtmdb.request;

import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.example.retrofitwithmvvmandtmdb.response.MovieResponse;
import com.example.retrofitwithmvvmandtmdb.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
                @Query("api_key") String apikey,
                @Query("query") String query,
                @Query("page") int page
        );

    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);

    @GET("movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String apikey,
            @Query("page") int page
    );


}
