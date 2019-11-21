package com.example.opskrift.Model.DataSource;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse {

    @SerializedName("drinks")
    @Expose
    private List<DrinkResponse> drinks = new ArrayList<>();

    public List<DrinkResponse> getDrinksList() {
        if(drinks != null)
            return drinks;
        return new ArrayList<>();
    }
}
