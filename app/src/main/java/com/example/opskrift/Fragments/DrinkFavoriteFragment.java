package com.example.opskrift.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opskrift.Adapters.DrinkAdapter;
import com.example.opskrift.Model.Business_Classes.Drink;
import com.example.opskrift.R;
import com.example.opskrift.ViewModels.DrinkViewModel;

import java.util.List;

public class DrinkFavoriteFragment extends Fragment  implements DrinkAdapter.OnListItemClickListener{

    //UI
    TextView errorText;
    EditText searchEditText;
    RecyclerView drinkListView;
    // ProgressBar progressBar;

    //Architecture
    DrinkViewModel viewModel;
    DrinkAdapter drinkAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_drinks_favorite, container,false);

        //Hide custom back button
        Button customBackButton = getActivity().findViewById(R.id.toolbar_custom_back);
        if(customBackButton != null)
            customBackButton.setVisibility(View.INVISIBLE);

        //Hide search bar
        searchEditText = getActivity().findViewById(R.id.toolbar_search);
        if(searchEditText != null)
            searchEditText.setVisibility(View.INVISIBLE);

        //UI
        drinkListView = RootView.findViewById(R.id.frag_drink_favorite_ListView);
        errorText = RootView.findViewById(R.id.frag_drink_favorite_error);

        //Architecture
        viewModel = ViewModelProviders.of(getActivity()).get(DrinkViewModel.class);
        viewModel.getFavoriteDrinks().observe(this, new Observer<List<Drink>>() {
            @Override
            public void onChanged(List<Drink> drinks) {
                refreshList();
            }
        });

        //RecyclerView
        drinkListView.hasFixedSize();
        drinkListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        refreshList();

        return RootView;

    }

    public void refreshList() {
        drinkAdapter = new DrinkAdapter(viewModel.getFavoriteDrinks().getValue(),this);
        drinkListView.setAdapter(drinkAdapter);

        if(drinkAdapter.getItemCount() == 0)
            errorText.setText(R.string.favError);
        else
            errorText.setText("");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        viewModel.setSelectedDrink(viewModel.getFavoriteDrinkByIndex(clickedItemIndex));
        if(getFragmentManager() != null)
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DrinkDetailsFragment()).addToBackStack("details").commit();
    }

}
