package com.example.opskrift.Model.Storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.opskrift.Model.Business_Classes.Drink;

import java.util.List;

@Dao
public interface DrinkDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Drink drink);

    @Delete
    void delete(Drink drink);

    @Query("DELETE FROM drink_table")
    void deleteFavoriteDrinks();

    //Only favorite drinks are stored in the database
    @Query("SELECT * FROM drink_table ORDER BY name ASC")
    LiveData<List<Drink>> getAllDrinks();

    //check if favorite drink already exists in database and return it if it does.
    @Query("SELECT * FROM drink_table WHERE drinkID = :drinkID")
    Drink getDrinkFromDatabase(int drinkID);
}
