package com.example.retrofitwithmvvmandtmdb.viewModelApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.example.retrofitwithmvvmandtmdb.response.MovieResponse;

import java.util.List;
import java.util.PrimitiveIterator;

public class MovieRepository {


    private MovieApiClient movieApiClient;


    // Single tern pattern in java
    private static MovieRepository instance;

    public String mQuery;
    public int mPageNumber;

    public static MovieRepository getInstance(){
        if (instance == null){
            instance  = new MovieRepository();
        }
        return instance;
    }

    //Constructor
    public MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }


    public LiveData<List<MovieModel>> getMovie(){
        return movieApiClient.getMovie();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviePop();
    }


    // 2-Calling method in MovieRepository
    public void  searchMovieApi(String query, int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query,pageNumber);
    }
   public void  searchMovieAPop(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop(pageNumber);
    }








    public void searchNextpage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }


     public void searchNextpagePop(){
        searchMovieAPop(mPageNumber+1);
    }




}
