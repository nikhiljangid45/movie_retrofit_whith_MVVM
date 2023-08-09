package com.example.retrofitwithmvvmandtmdb.viewModelApp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.retrofitwithmvvmandtmdb.AppExecutors;
import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.example.retrofitwithmvvmandtmdb.request.Service;
import com.example.retrofitwithmvvmandtmdb.response.MovieResponse;
import com.example.retrofitwithmvvmandtmdb.response.MovieSearchResponse;
import com.example.retrofitwithmvvmandtmdb.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {




    //Mutable LiveData for search
    public MutableLiveData<List<MovieModel>> mMovie;


    // making global runnable for search
    public RetrieveMoviesRunnable retrieveMoviesRunnable;



    // Mutable Live Data for popular
    public MutableLiveData<List<MovieModel>> mMoviePop;

    // making global runnable for search
    public RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;




    private static MovieApiClient instance;

    public static MovieApiClient getInstance(){
        if (instance  == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovie = new MutableLiveData<>();
        mMoviePop = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovie(){
        return mMovie;
    }
    public LiveData<List<MovieModel>> getMoviePop(){
        return mMoviePop;
    }





    public void searchMoviesApi(String query , int pageNumber){


        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler.cancel(true);

            }
        }, 3000, TimeUnit.MILLISECONDS);
    }
    public void searchMoviesPop(int pageNumber){


        if (retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler2.cancel(true);

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

    private String query;
    private int pageNumber;
    private boolean cancelRequest;

    public RetrieveMoviesRunnable(String query, int pageNumber) {
        this.query = query;
        this.pageNumber = pageNumber;
        this.cancelRequest = false;
    }




    @Override
    public void run() {


        try {
            Response response = getMovie(query,pageNumber).execute();
            if (cancelRequest){ // true
                return;
            }
            if (response.isSuccessful()){
                List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                if (pageNumber ==1){
                    mMovie.postValue(list);
                }else {
                    List<MovieModel> currentMovie = mMovie.getValue();
                    currentMovie.addAll(list);
                    mMovie.postValue(currentMovie);
                }

            }else {
                String error = response.errorBody().string();
                Log.v("Tag","Error "+error);
                mMovie.postValue(null);
            }





        } catch (IOException e) {
            throw new RuntimeException(e);
            // mMovie.postValue(null);

        }



    }


    public Call<MovieSearchResponse> getMovie(String query , int pageNumber){
        return Service.getMovieApi().searchMovie(Credentials.API_KEY,query,pageNumber);
    }

    private void cancelRequest(){
        Log.v("Tag","cancelling Search Request");
        cancelRequest = true;

    }
}
    private class RetrieveMoviesRunnablePop implements Runnable {

    private String query;
    private int pageNumber;
    private boolean cancelRequest;

    public RetrieveMoviesRunnablePop(int pageNumber) {
        this.pageNumber = pageNumber;
        this.cancelRequest = false;
    }




    @Override
    public void run() {


        try {
            Response response2 = getPop(pageNumber).execute();
            if (cancelRequest){ // true
                return;
            }
            if (response2.isSuccessful()){
                List<MovieModel> list =new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());

                if (pageNumber ==1){
                    mMoviePop.postValue(list);
                }else {
                    List<MovieModel> currentMovie = mMovie.getValue();
                    currentMovie.addAll(list);
                    mMoviePop.postValue(currentMovie);
                }

            }else {
                String error = response2.errorBody().string();
                Log.v("Tag","Error "+error);
                mMoviePop.postValue(null);
            }





        } catch (IOException e) {
            throw new RuntimeException(e);
            // mMovie.postValue(null);

        }



    }


    public Call<MovieSearchResponse> getPop(int pageNumber){
        return Service.getMovieApi().getPopular(Credentials.API_KEY,pageNumber);
    }

    private void cancelRequest(){
        Log.v("Tag","cancelling Search Request");
        cancelRequest = true;

    }
}

}
