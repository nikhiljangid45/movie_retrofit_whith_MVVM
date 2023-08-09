package com.example.retrofitwithmvvmandtmdb.response;


import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// This class is for getting multiple movie (Movie movie) - popular movie
public class MovieSearchResponse {


    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotal_count() {
        return total_count;
    }

    public List<MovieModel> getMovies(){
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchRespones{" +
                "movies=" + movies +
                '}';
    }
}





