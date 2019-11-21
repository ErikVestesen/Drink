package com.example.opskrift.Model.DataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BaseURL = "https://www.thecocktaildb.com/api/json/v1/1/";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static DrinkAPI drinkAPI = retrofit.create(DrinkAPI.class);

    public static DrinkAPI getDrinkAPI(){
        return drinkAPI;
    }
}
