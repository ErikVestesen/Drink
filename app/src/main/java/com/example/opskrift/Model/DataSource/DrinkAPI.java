package com.example.opskrift.Model.DataSource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//URL used to search for drink by ID
//final String urlDrinkDetails = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="; etc 11129

//URL used to search for drink by Ingredient
//final String urlDrinkIngredient = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i="; etc lemon (this call will give a list of drink ids, not actual drinks)

//URL used to search for drink by name
//final String urlDrinkName = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="; etc lemon

//Base url = https://www.thecocktaildb.com/api/json/v1/1/

public interface DrinkAPI {
    //@GET("search.php?s={name}")
    @GET("search.php?")
    Call<JsonResponse> getDrinkByName(@Query ("s") String name);

    @GET("filter.php?")
    Call<JsonResponse> getDrinkByIngredient(@Query ("i") String name);

    @GET("lookup.php?")
    Call<JsonResponse> getDrinkByID(@Query ("i") Integer name);
}

//TODO


