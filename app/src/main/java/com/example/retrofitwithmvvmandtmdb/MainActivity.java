package com.example.retrofitwithmvvmandtmdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.retrofitwithmvvmandtmdb.adapter.MovieRecyclerView;
import com.example.retrofitwithmvvmandtmdb.adapter.MovieViewHolder;
import com.example.retrofitwithmvvmandtmdb.adapter.OnMovieListener;
import com.example.retrofitwithmvvmandtmdb.model.MovieModel;
import com.example.retrofitwithmvvmandtmdb.request.MovieApi;
import com.example.retrofitwithmvvmandtmdb.request.Service;
import com.example.retrofitwithmvvmandtmdb.response.MovieResponse;
import com.example.retrofitwithmvvmandtmdb.response.MovieSearchResponse;
import com.example.retrofitwithmvvmandtmdb.utils.Credentials;
import com.example.retrofitwithmvvmandtmdb.viewModelApp.MovieListViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMovieListener {



    private MovieListViewModel  movieListViewModel;
    private RecyclerView recyclerView;
    private  MovieRecyclerView movieRecyclerViewAdapter;





    boolean isPopular = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);



       movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


       SetupSearchView();


     // Call the observe methord
        ConfigractionRecycleView();
        ObservingChange();
        ObservingChangePop();
        movieListViewModel.searchMoviePop(1);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextpage();
                    movieListViewModel.searchNextpagePop();
                    Toast.makeText(MainActivity.this, "last page ", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }


    //SearchView



   // 4 - Calling methord in main activity
   // private void  searchMovieApi(String query ,int pageNumber){
   //movieListViewModel.searchMovieApi(query, pageNumber);
   //}

   public void ObservingChange(){

        movieListViewModel.getMovie().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if (movieModels != null){
                    Toast.makeText(MainActivity.this, "observe", Toast.LENGTH_SHORT).show();
                    for (MovieModel movieModel : movieModels){
                        Log.v("Tag","onChange "+movieModel.getTitle());
                        movieRecyclerViewAdapter.setmMovie(movieModels);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "kali", Toast.LENGTH_SHORT).show();
                }
            }
        });



}
   public void ObservingChangePop(){

        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                if (movieModels != null){
                    Toast.makeText(MainActivity.this, "observe", Toast.LENGTH_SHORT).show();
                    for (MovieModel movieModel : movieModels){
                        Log.v("Tag","onChange "+movieModel.getTitle());
                        movieRecyclerViewAdapter.setmMovie(movieModels);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "kali", Toast.LENGTH_SHORT).show();
                }
            }
        });



}
    private void ConfigractionRecycleView(){
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
    }
    @Override
    public void onMovieClicked(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
        Toast.makeText(this, "onMovieClicked "+position, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onCategoryClick(String category) {
        Toast.makeText(this, "onMovieClicked ", Toast.LENGTH_SHORT).show();

    }

    public void SetupSearchView(){

        final SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                movieListViewModel.searchMovieApi(query,1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null){
                    Toast.makeText(MainActivity.this, "nihhj", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });




    }



    //    private void getRetrofitResponse(){
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(Credentials.API_KEY,"Jack Reacher",1);
//
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if (response.isSuccessful()){
//                    List<MovieModel> movieSearchResponses = new ArrayList<>(response.body().getMovies());
//                    Log.v("Tag","The release date "  + movieSearchResponses.toString());
//                    for (MovieModel model : movieSearchResponses){
//                        Log.v("Tag","The release date "  + model.getRelease_date());
//                    }
//
//                }else {
//                    Toast.makeText(MainActivity.this, "Failed 1", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//                Toast.makeText(MainActivity.this, "Failed 2"+call.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void getRetrofitResponseAccordingToId(){
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieModel> responseCall = movieApi.getMovie(550,Credentials.API_KEY);
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.isSuccessful()){
//                    MovieModel nikhil = response.body();
//                    Log.v("Tag",nikhil.getTitle());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }
}