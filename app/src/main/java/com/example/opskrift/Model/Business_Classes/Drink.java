package com.example.opskrift.Model.Business_Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.opskrift.Model.Storage.Converters;

import java.util.ArrayList;

@Entity(tableName = "drink_table")
public class Drink {

    @PrimaryKey (autoGenerate = true)
    private int drinkID;
    private String name;
    @TypeConverters({Converters.class})
    private ArrayList<String> ingredients;
    @TypeConverters({Converters.class})
    private ArrayList<String> ingredientsMeasures;
    private boolean isAlcoholic;
    private String recommendedGlass;
    private String instructions;
    private String drinkThumbnail;
    private boolean isDrink;


    private boolean isFavorite;

    public Drink() {
        // Default constructor required for calls to DataSnapshot.getValue(Drink.class)
    }

    public Drink(int drinkID, String name,  ArrayList<String> ingredients,  ArrayList<String> ingredientsMeasures, boolean isAlcoholic, String recommendedGlass, String instructions, String drinkThumbnail, boolean isDrink) {
        this.drinkID = drinkID;
        this.name = name;
        this.ingredients = ingredients;
        this.ingredientsMeasures = ingredientsMeasures;
        this.isAlcoholic = isAlcoholic;
        this.recommendedGlass = recommendedGlass;
        this.instructions = instructions;
        this.drinkThumbnail = drinkThumbnail;
        this.isDrink = isDrink;
        isFavorite = false; //default not favorite.
    }

    @Override
    public String toString() {
        return "\n ID: "+this.drinkID + "\n name: "+ this.name + "\n ingredients: "+ ingredients + "\n ingredientsMeasures: "+ingredientsMeasures + "\n recommendedGlass: "+ recommendedGlass + "\n instructions: "+ instructions + "\n drinkThumbnail: "+ drinkThumbnail + "\n ";
    }

    @Override
    public boolean equals(Object object) //Had to override this to make  'contains comparision' worked.
    {
        boolean isEqual = false;
        if (object != null && object instanceof Drink) {
            isEqual = this.getDrinkID() == ((Drink) object).getDrinkID();
        }
        return isEqual;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredientsMeasures() {
        return ingredientsMeasures;
    }

    public void setIngredientsMeasures(ArrayList<String> ingredientsMeasures) {
        this.ingredientsMeasures = ingredientsMeasures;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public String getRecommendedGlass() {
        return recommendedGlass;
    }

    public void setRecommendedGlass(String recommendedGlass) {
        this.recommendedGlass = recommendedGlass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDrinkThumbnail() {
        return drinkThumbnail;
    }

    public void setDrinkThumbnail(String drinkThumbnail) {
        this.drinkThumbnail = drinkThumbnail;
    }

    public boolean isDrink() {
        return isDrink;
    }

    public void setDrink(boolean drink) {
        isDrink = drink;
    }
}
