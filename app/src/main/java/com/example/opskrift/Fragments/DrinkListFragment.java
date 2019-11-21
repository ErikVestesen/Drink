package com.example.opskrift.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

public class DrinkListFragment extends Fragment implements DrinkAdapter.OnListItemClickListener {

    //UI
    TextView errorText;
    EditText searchEditText;
    RecyclerView drinkListView;

    //Architecture
    DrinkViewModel viewModel;
    DrinkAdapter drinkAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_drink_list, container,false);

        //Hide custom back button
        Button customBackButton = getActivity().findViewById(R.id.toolbar_custom_back);
        if(customBackButton != null)
            customBackButton.setVisibility(View.INVISIBLE);

        //Show search bar
        searchEditText = getActivity().findViewById(R.id.toolbar_search);
        if(searchEditText != null)
            searchEditText.setVisibility(View.VISIBLE);

        //UI
        drinkListView = RootView.findViewById(R.id.frag_drinkListView);
        errorText = RootView.findViewById(R.id.frag_drink_list_error);


        //Architecture
        viewModel = ViewModelProviders.of(getActivity()).get(DrinkViewModel.class);
        viewModel.getAllDrinks().observe(this, new Observer<List<Drink>>() {
            @Override
            public void onChanged(@Nullable List<Drink> drinks) {
                refreshList();
            }
        });

        //RecyclerView
        drinkListView.hasFixedSize();
        drinkListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //find drinks from internet, which updates the viewModel's list of drinks
        viewModel.searchForDrinks(viewModel.getCurrentDrinkSearch());

        //Adapter
        refreshList();

        //On keyboard Enter pressed, do search
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    viewModel.setCurrentDrinkSearch(getSearchText());
                    viewModel.searchForDrinks(getSearchText());
                    refreshList();
                    return true;
                }
                return false;
            }
        });


        return RootView;

    }

    public void refreshList() {
        drinkAdapter = new DrinkAdapter(viewModel.getAllDrinks().getValue(),this);
        drinkListView.setAdapter(drinkAdapter);

        if(drinkAdapter.getItemCount() == 0)
            errorText.setText(R.string.searchError);
        else
            errorText.setText("");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        viewModel.setSelectedDrink(viewModel.getDrinkByIndex(clickedItemIndex));
        if(getFragmentManager() != null)
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DrinkDetailsFragment()).addToBackStack("details").commit();
    }

    public String getSearchText() {
        if(searchEditText != null) {
            return searchEditText.getText().toString();
        }
        return null;
    }


}
