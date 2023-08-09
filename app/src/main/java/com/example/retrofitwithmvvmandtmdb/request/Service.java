package com.example.retrofitwithmvvmandtmdb.request;

import com.example.retrofitwithmvvmandtmdb.utils.Credentials;

import java.security.PublicKey;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



//    public static  Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//            .baseUrl(Credentials.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create());
//
//    public   static  Retrofit retrofit = retrofitBuilder.build();



    public static MovieApi movieApi = getRetrofitInstance().create(MovieApi.class);
    public static MovieApi getMovieApi(){
        return movieApi;
    }


}
