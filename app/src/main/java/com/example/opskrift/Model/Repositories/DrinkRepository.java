package com.example.opskrift.Model.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.opskrift.Model.Business_Classes.Drink;
import com.example.opskrift.Model.DataSource.DrinkAPI;
import com.example.opskrift.Model.DataSource.DrinkResponse;
import com.example.opskrift.Model.DataSource.JsonResponse;
import com.example.opskrift.Model.DataSource.ServiceGenerator;
import com.example.opskrift.Model.Storage.DrinkDAO;
import com.example.opskrift.Model.Storage.DrinkDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkRepository {

    private DrinkDAO drinkDAO;
    private static DrinkRepository instance;
    public MutableLiveData<List<Drink>> allDrinks; //all current drink you have searched for. These are not stored anywhere and they are all fetched from internet.
    public LiveData<List<Drink>> favoriteDrinks; //all the drinks you have marked as favorites - these should also be stored in a local database and/or cloud


    public static synchronized  DrinkRepository getInstance(Application application) {
        if(instance == null)
            instance = new DrinkRepository(application);
        return instance;
    }

    private DrinkRepository(Application application) {
        DrinkDatabase database = DrinkDatabase.getInstance(application);
        drinkDAO = database.drinkDAO();
        allDrinks = new MutableLiveData<>();

        favoriteDrinks = drinkDAO.getAllDrinks();
    }

    public Drink getDrinkByIndex(int index) {
        if(allDrinks.getValue().size() > index) {
            return allDrinks.getValue().get(index);
        }
        return null;
    }

    public Drink getFavoriteDrinkByIndex(int index) {
        if(favoriteDrinks.getValue().size() > index) {
            return favoriteDrinks.getValue().get(index);
        }
        return null;
    }

    //LiveData, so its immutable
    public LiveData<List<Drink>> getAllDrinks() {
        return allDrinks;
    }


    public LiveData<List<Drink>> getFavoriteDrinks() {
        return favoriteDrinks;
    }

    //Insert a drink into the database
    public void insert(Drink drink, boolean cloud) {
        //Insert into local database
        new InsertDrinkAsync(drinkDAO).execute(drink);

        //Insert into firebase database
        if(cloud)
            addDrinkToFirebaseDB(drink);

    }

    //delete a drink from the database
    public void delete(Drink drink, boolean cloud) {
        new DeleteDrinkAsync(drinkDAO).execute(drink);

        if(cloud)
            removeDrinkFromFirebaseDB(drink);

    }

    //Delete all drinks from the database
    public void deleteFavoriteDrinks() {
        if(getFavoriteDrinks().getValue() != null) {
            for (Drink d : getFavoriteDrinks().getValue()) {
                delete(d,true); // this will also remove them all from firebase
            }
            favoriteDrinks = getFavoriteDrinks();
        } else {
            Log.i("myTag","favoriteDrinks delete error");
        }
        //new DeleteAllFavoritesAsync(drinkDAO).execute();
    }

    //Main search function, find drinks based on both ingredients and drink names. Automatic updates LiveData
    public void searchForDrinks(String input) {
        //First search if its a drink name
        getDrinkByName(input);

        //Search if it is an ingredient in a drink. This will return drink IDs which we can search for in another net call. 10/10 api.
        getDrinkIDsByIngredient(input);
    }

    public Drink getDrinkFromDatabase(int drinkID) {
        Drink drink = null;
        try {
            drink = new GetDrinkAsync(drinkDAO).execute(drinkID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    return drink;
    }

    //Get drinks by name and fill the list with results, if any.
    private void getDrinkByName(String drinkName) {
        final DrinkAPI drinkAPI = ServiceGenerator.getDrinkAPI();
        Call<JsonResponse> call = drinkAPI.getDrinkByName(drinkName);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.code() == 200) {
                    ArrayList<Drink> tempDrinks = new ArrayList<>();
                    for(DrinkResponse drinkResponse : response.body().getDrinksList()) {
                        tempDrinks.add(drinkResponse.getDrink());
                    }
                    allDrinks.setValue(tempDrinks); //Updating LiveData
                }
            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i("Retrofit", "Get Drink By name went wrong :( "+t.toString());
            }
        });
    }
    private void getDrinkIDsByIngredient(final String ingredient) {
        final DrinkAPI drinkAPI = ServiceGenerator.getDrinkAPI();
        Call<JsonResponse> call = drinkAPI.getDrinkByIngredient(ingredient);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.code() == 200) {
                    for(DrinkResponse drinkResponse : response.body().getDrinksList()) {
                        getDrinkByID(drinkResponse.getDrink().getDrinkID());
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i("Retrofit", "Getting ID went wrong :( "+t.toString() +" for element: "+ingredient);
            }
        });
    }
    private void getDrinkByID(Integer drinkId) {
        final DrinkAPI drinkAPI = ServiceGenerator.getDrinkAPI();
        Call<JsonResponse> call = drinkAPI.getDrinkByID(drinkId);
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (response.code() == 200) {
                    List<Drink> currentDrinks = allDrinks.getValue();
                    for(DrinkResponse drinkResponse : response.body().getDrinksList()) {
                        if(!currentDrinks.contains(drinkResponse.getDrink()))
                            currentDrinks.add(drinkResponse.getDrink());
                    }
                    allDrinks.setValue(currentDrinks); //Updating LiveData
                }
            }
            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.i("Retrofit", "Get Drink By id went wrong :( "+t.toString());
            }
        });
    }

    //Insert favorite drink into the database on UI thread
    private static class InsertDrinkAsync extends AsyncTask<Drink,Void,Void> {
        private DrinkDAO drinkDAO;

        private InsertDrinkAsync(DrinkDAO drinkDAO) {
            this.drinkDAO = drinkDAO;
        }

        @Override
        protected Void doInBackground(Drink... drinks) {
            drinkDAO.insert(drinks[0]);
            return null;
        }
    }

    //Delete favorite drink from the database on UI thread
    private static class DeleteDrinkAsync extends AsyncTask<Drink,Void,Void> {
        private DrinkDAO drinkDAO;

        private DeleteDrinkAsync(DrinkDAO drinkDAO) {
            this.drinkDAO = drinkDAO;
        }

        @Override
        protected Void doInBackground(Drink... drinks) {
            drinkDAO.delete(drinks[0]);
            return null;
        }
    }

    //Get favorite drink from database on UI thread based on drinkID
    private static class GetDrinkAsync extends AsyncTask<Integer,Void,Drink> {

        private DrinkDAO drinkDAO;

        private GetDrinkAsync(DrinkDAO drinkDAO) {
            this.drinkDAO = drinkDAO;
        }

        @Override
        protected Drink doInBackground(Integer... integers) {
           return drinkDAO.getDrinkFromDatabase(integers[0]);
        }
    }

    //Delete all favorite drink from the database on UI thread
    private static class DeleteAllFavoritesAsync extends AsyncTask<Void,Void,Void> {

        private DrinkDAO drinkDAO;

        private DeleteAllFavoritesAsync(DrinkDAO drinkDAO) {
            this.drinkDAO = drinkDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            drinkDAO.deleteFavoriteDrinks();
            return null;
        }
    }

    public void addDrinkToFirebaseDB(Drink drink) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();// getEmail().replace(".","");;
            FirebaseDatabase.getInstance().getReference().child(user).child("drinks").child(drink.getDrinkID() + "").setValue(drink);
        }
    }

    public void removeDrinkFromFirebaseDB(Drink drink) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();// getEmail().replace(".","");
            FirebaseDatabase.getInstance().getReference().child(user).child("drinks").child(drink.getDrinkID() + "").removeValue();
        }
    }

    public void syncWithCloud() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(user+"/drinks");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<Drink> drinksInTheCloud  = new ArrayList<>();

                    // Get all drinks from the cloud
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        Drink d = ds.getValue(Drink.class);
                        drinksInTheCloud.add(d);

                    }
                    //Remove the drinks on the phone that isnt on the cloud
                    if(getFavoriteDrinks().getValue() != null) {
                        Log.i("myTag", "drinks on the phone count: "+getFavoriteDrinks().getValue().size());
                        for(Drink drinkOnPhone: getFavoriteDrinks().getValue()) {
                           // if(!drinksInTheCloud.contains(drinkOnPhone)) {
                                delete(drinkOnPhone, false);
                                Log.i("myTag", "deleting "+drinkOnPhone.getName());
                            //}
                        }
                    } else {
                        Log.i("myTag", "favorite drinks null");
                    }

                    //Add the drinks from the cloud to the phone
                    Log.i("myTag", "drinks in the cloud count: "+drinksInTheCloud.size());
                    for(Drink d: drinksInTheCloud) {
                        insert(d,false);
                        Log.i("myTag", "inserting "+d.getName());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
