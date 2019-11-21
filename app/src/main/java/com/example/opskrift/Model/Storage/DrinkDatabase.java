package com.example.opskrift.Model.Storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.opskrift.Model.Business_Classes.Drink;

@Database(entities = {Drink.class}, version = 1, exportSchema = false)
public abstract class DrinkDatabase extends RoomDatabase {
    private static DrinkDatabase instance;
    public abstract DrinkDAO drinkDAO();

    public static synchronized DrinkDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DrinkDatabase.class, "drink_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
