package com.example.opskrift.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.opskrift.R;
import com.example.opskrift.ViewModels.DrinkViewModel;

public class SettingsFragment extends Fragment {

    Button resetButton,btn_sync;
    DrinkViewModel viewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_settings, container,false);

        //UI
        resetButton = RootView.findViewById(R.id.fragment_settings_reset_button);
        btn_sync = RootView.findViewById(R.id.btn_sync);

        //hide search in app bar
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
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteFavoriteDrinks();
                Toast.makeText(getActivity(), "Reset complete!", Toast.LENGTH_LONG).show();
            }
        });
        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.syncWithCloud();
                Toast.makeText(getActivity(), "Synchronized!", Toast.LENGTH_LONG).show();
            }
        });

        return RootView;
    }
}
