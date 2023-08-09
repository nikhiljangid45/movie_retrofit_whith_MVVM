package com.example.retrofitwithmvvmandtmdb.viewModelApp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofitwithmvvmandtmdb.model.MovieModel;

import java.util.List;

public class MovieListViewModel  extends ViewModel {


    private MovieRepository movieRepository;

    public MovieListViewModel() {
              movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovie(){
        return movieRepository.getMovie();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }



    //3 -calling the method
    public void searchMovieApi(String query ,int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }



    public void searchMoviePop(int pageNumber){
        movieRepository.searchMovieAPop(pageNumber);
    }


    public void searchNextpage(){
        movieRepository.searchNextpage();
    }

    public void searchNextpagePop(){
        movieRepository.searchNextpagePop();
    }

}
