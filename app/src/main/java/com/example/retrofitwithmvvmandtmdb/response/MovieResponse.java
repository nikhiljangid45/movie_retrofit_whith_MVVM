package com.example.retrofitwithmvvmandtmdb.response;


import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// This class is for single movie request
public class MovieResponse {




    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieRespons{" +
                "movie=" + movie +
                '}';
    }
}

