package com.example.opskrift.Model.DataSource;

import android.util.Log;

import com.example.opskrift.Model.Business_Classes.Drink;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DrinkResponse {

    @SerializedName("idDrink")
    private int id;

    @SerializedName("strDrink")
    private String name;

    @SerializedName(value="ingredients", alternate = {
            "strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5",
            "strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10",
            "strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15"}
            )
    private ArrayList<String> ingredients;

    @SerializedName(value="ingredientsMeasures", alternate = {
            "strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5",
            "strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10",
            "strMeasure11, strMeasure12,strMeasure13, strMeasure14, strMeasure15"}
    )
    private ArrayList<String> ingredientsMeasures;

    @SerializedName("strAlcoholic")
    private String isAlcoholicString;
    private boolean isAlcoholic;

    @SerializedName("strGlass")
    private String recommendedGlass;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strDrinkThumb")
    private String drinkThumbnail;

    @SerializedName("")
    private boolean isDrink;

    public Drink getDrink() {

        ingredients = mergeIngredients();
        ingredientsMeasures = mergeMeasures();

        return new Drink(id, name, ingredients, ingredientsMeasures, isAlcoholic, recommendedGlass, instructions, drinkThumbnail, isDrink);
    }

    @Override
    public String toString() {
        return "\n ID: "+this.id + "\n name: "+ this.name + "\n ingredients: "+ ingredients + "\n ingredientsMeasures: "+ingredientsMeasures + "\n recommendedGlass: "+ recommendedGlass + "\n instructions: "+ instructions + "\n drinkThumbnail: "+ drinkThumbnail + "\n ";
    }

    //All the ingredient and measures (Ugly design from API forces me to do this :')  )

    public ArrayList<String> mergeIngredients() {
        ArrayList<String> result = new ArrayList<>();

        if(strIngredient1 != null)
            result.add(strIngredient1);
        if(strIngredient2 != null)
            result.add(strIngredient2);
        if(strIngredient3 != null)
            result.add(strIngredient3);
        if(strIngredient4 != null)
            result.add(strIngredient4);
        if(strIngredient5 != null)
            result.add(strIngredient5);
        if(strIngredient6 != null)
            result.add(strIngredient6);
        if(strIngredient7 != null)
            result.add(strIngredient7);
        if(strIngredient8 != null)
            result.add(strIngredient8);
        if(strIngredient9 != null)
            result.add(strIngredient9);
        if(strIngredient10 != null)
            result.add(strIngredient10);
        if(strIngredient11 != null)
            result.add(strIngredient11);
        if(strIngredient12 != null)
            result.add(strIngredient12);
        if(strIngredient13 != null)
            result.add(strIngredient13);
        if(strIngredient14 != null)
            result.add(strIngredient14);
        if(strIngredient15 != null)
            result.add(strIngredient15);

        return result;
    }

    public ArrayList<String> mergeMeasures() {
        ArrayList<String> result = new ArrayList<>();

        if(strMeasure1 != null)
            result.add(strMeasure1);
        if(strMeasure2 != null)
            result.add(strMeasure2);
        if(strMeasure3 != null)
            result.add(strMeasure3);
        if(strMeasure4 != null)
            result.add(strMeasure4);
        if(strMeasure5 != null)
            result.add(strMeasure5);
        if(strMeasure6 != null)
            result.add(strMeasure6);
        if(strMeasure7 != null)
            result.add(strMeasure7);
        if(strMeasure8 != null)
            result.add(strMeasure8);
        if(strMeasure9 != null)
            result.add(strMeasure9);
        if(strMeasure10 != null)
            result.add(strMeasure10);
        if(strMeasure11 != null)
            result.add(strMeasure11);
        if(strMeasure12 != null)
            result.add(strMeasure12);
        if(strMeasure13 != null)
            result.add(strMeasure13);
        if(strMeasure14 != null)
            result.add(strMeasure14);
        if(strMeasure15 != null)
            result.add(strMeasure15);

        return result;
    }

    @SerializedName("strIngredient1")
    private String strIngredient1;
    @SerializedName("strIngredient2")
    private String strIngredient2;
    @SerializedName("strIngredient3")
    private String strIngredient3;
    @SerializedName("strIngredient4")
    private String strIngredient4;
    @SerializedName("strIngredient5")
    private String strIngredient5;
    @SerializedName("strIngredient6")
    private String strIngredient6;
    @SerializedName("strIngredient7")
    private String strIngredient7;
    @SerializedName("strIngredient8")
    private String strIngredient8;
    @SerializedName("strIngredient9")
    private String strIngredient9;
    @SerializedName("strIngredient10")
    private String strIngredient10;
    @SerializedName("strIngredient11")
    private String strIngredient11;
    @SerializedName("strIngredient12")
    private String strIngredient12;
    @SerializedName("strIngredient13")
    private String strIngredient13;
    @SerializedName("strIngredient14")
    private String strIngredient14;
    @SerializedName("strIngredient15")
    private String strIngredient15;
    @SerializedName("strMeasure1")
    private String strMeasure1;
    @SerializedName("strMeasure2")
    private String strMeasure2;
    @SerializedName("strMeasure3")
    private String strMeasure3;
    @SerializedName("strMeasure4")
    private String strMeasure4;
    @SerializedName("strMeasure5")
    private String strMeasure5;
    @SerializedName("strMeasure6")
    private String strMeasure6;
    @SerializedName("strMeasure7")
    private String strMeasure7;
    @SerializedName("strMeasure8")
    private String strMeasure8;
    @SerializedName("strMeasure9")
    private String strMeasure9;
    @SerializedName("strMeasure10")
    private String strMeasure10;
    @SerializedName("strMeasure11")
    private String strMeasure11;
    @SerializedName("strMeasure12")
    private String strMeasure12;
    @SerializedName("strMeasure13")
    private String strMeasure13;
    @SerializedName("strMeasure14")
    private String strMeasure14;
    @SerializedName("strMeasure15")
    private String strMeasure15;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredientsMeasures(ArrayList<String> ingredientsMeasures) {
        this.ingredientsMeasures = ingredientsMeasures;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public void setRecommendedGlass(String recommendedGlass) {
        this.recommendedGlass = recommendedGlass;
    }

    public String getIsAlcoholicString() {
        return isAlcoholicString;
    }

    public void setIsAlcoholicString(String isAlcoholicString) {
        this.isAlcoholicString = isAlcoholicString;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setDrinkThumbnail(String drinkThumbnail) {
        this.drinkThumbnail = drinkThumbnail;
    }

    public void setDrink(boolean drink) {
        isDrink = drink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {

        return ingredients;
    }

    public ArrayList<String> getIngredientsMeasures() {
        return ingredientsMeasures;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public String getRecommendedGlass() {
        return recommendedGlass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getDrinkThumbnail() {
        return drinkThumbnail;
    }

    public boolean isDrink() {
        return isDrink;
    }
}
