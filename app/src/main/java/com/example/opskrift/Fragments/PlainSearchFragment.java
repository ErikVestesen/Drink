package com.example.opskrift.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.opskrift.R;
import com.example.opskrift.ViewModels.DrinkViewModel;

public class PlainSearchFragment extends Fragment {

    Button searchButton;
    EditText searchText;
    DrinkViewModel viewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_search, container,false);

        //UI
        searchButton = RootView.findViewById(R.id.Search_button);
        searchText = RootView.findViewById(R.id.txt_search);

        //Hide search in app bar
        EditText searchEditText = getActivity().findViewById(R.id.toolbar_search);
        if(searchEditText != null)
            searchEditText.setVisibility(View.INVISIBLE);

        //Hide custom back button
        Button customBackButton = getActivity().findViewById(R.id.toolbar_custom_back);
        if(customBackButton != null)
            customBackButton.setVisibility(View.INVISIBLE);

        //Architecture
        viewModel = ViewModelProviders.of(getActivity()).get(DrinkViewModel.class);

        //Events
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search(view);
            }
        });

        return RootView;
    }


    public void Search(View v){
        if(getSearchText().length() > 0 && getFragmentManager() != null) {
            viewModel.setCurrentDrinkSearch(getSearchText());
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DrinkListFragment()).commit();
        }
    }

    public String getSearchText() {
        return searchText.getText().toString();
    }


}
