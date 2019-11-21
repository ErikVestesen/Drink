package com.example.opskrift.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.opskrift.Model.Business_Classes.Drink;
import com.example.opskrift.Model.Repositories.DrinkRepository;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {

    private DrinkRepository drinkRepository;
    private String currentDrinkSearch; //What is currently being searched for - this is responsible for determining which fragment should be used
    private Drink selectedDrink; //This drink is the drink that has been selected in the recycler view.


    public DrinkViewModel(Application application) {
        super(application);
        drinkRepository = DrinkRepository.getInstance(application);
    }

    public void searchForDrinks(String drinkName) {
        drinkRepository.searchForDrinks(drinkName);
    }

    //Use for add a favorite
    public void insertDrink(Drink drink) {
        drinkRepository.insert(drink,true);
    }

    //Use for delete a favorite
    public void deleteDrink(Drink drink) {drinkRepository.delete(drink,true);}


    //Use for delete all favorites.
    public void deleteFavoriteDrinks(){
        drinkRepository.deleteFavoriteDrinks();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return drinkRepository.getAllDrinks();
    }

    public LiveData<List<Drink>> getFavoriteDrinks() {
        return drinkRepository.getFavoriteDrinks();
    }

    public void setCurrentDrinkSearch(String currentDrinkSearch) {
        this.currentDrinkSearch = currentDrinkSearch;
    }

    public Drink getSelectedDrink() {
        return selectedDrink;
    }

    public void setSelectedDrink(Drink selectedDrink) {
        this.selectedDrink = selectedDrink;
    }

    public Drink getDrinkByIndex(int index) {
        return drinkRepository.getDrinkByIndex(index);
    }

    public Drink getFavoriteDrinkByIndex(int index) {
        return drinkRepository.getFavoriteDrinkByIndex(index);
    }

    public String getCurrentDrinkSearch() {
        return currentDrinkSearch;
    }

    public boolean checkIfFavorite(Drink d) {
        Drink temp = drinkRepository.getDrinkFromDatabase(d.getDrinkID());
        if(temp == null)
            return false;
        return true;
    }

    public void syncWithCloud() {
        drinkRepository.syncWithCloud();
    }
}
