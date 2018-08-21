package com.developer.abhishek.bakingtime.network;

import com.developer.abhishek.bakingtime.model.ApiEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildUrl {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = ApiEndpoint.BASE_URL;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
