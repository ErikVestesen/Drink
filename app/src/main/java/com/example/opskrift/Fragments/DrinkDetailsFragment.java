package com.example.opskrift.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.opskrift.Model.Business_Classes.Drink;
import com.example.opskrift.R;
import com.example.opskrift.ViewModels.DrinkViewModel;

public class DrinkDetailsFragment extends Fragment {

    DrinkViewModel viewModel;

    ImageView drinkImage, favoriteImage;
    TextView instructions, ingredients, drinkName, measure;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_drink_details, container,false);

        //hide search in app bar
        EditText searchEditText = getActivity().findViewById(R.id.toolbar_search);
        if(searchEditText != null)
            searchEditText.setVisibility(View.INVISIBLE);

        //Show custom back button
        Button customBackButton = getActivity().findViewById(R.id.toolbar_custom_back);
        customBackButton.setVisibility(View.VISIBLE);
        customBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                } else {
                    getActivity().onBackPressed();
                }
            }
        });

        //UI
        drinkImage = RootView.findViewById(R.id.fragment_details_drink_image);
        favoriteImage = RootView.findViewById(R.id.fragment_details_drink_favorite);

        instructions = RootView.findViewById(R.id.fragment_details_instructions);
        measure = RootView.findViewById(R.id.fragment_details_drink_measures);
        ingredients = RootView.findViewById(R.id.fragment_details_ingredients);
        drinkName = RootView.findViewById(R.id.fragment_details_drink_name);

        //ViewModel
        viewModel = ViewModelProviders.of(getActivity()).get(DrinkViewModel.class);

        favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchFavorite(view);
            }
        });

        //current drink
        setDrink();

        return RootView;
    }


    public void setDrink() {
        Drink selectedDrink = viewModel.getSelectedDrink();
        if(selectedDrink != null) {
            //Drink name
            drinkName.setText(selectedDrink.getName());

            //Drink image
            Glide.with(this).load(selectedDrink.getDrinkThumbnail()).into(drinkImage);

            //Instructions
            String glass = getString(R.string.RecommendedGlass)+" ";
            instructions.setText(glass+selectedDrink.getRecommendedGlass()+"\n\n"+selectedDrink.getInstructions());

            //Ingredients
            for(int i = 0; i < selectedDrink.getIngredients().size(); i++) {
                ingredients.append(selectedDrink.getIngredients().get(i)+"\n");
            }

            //Measurements
            for(int i = 0; i <selectedDrink.getIngredientsMeasures().size(); i++){
                measure.append(selectedDrink.getIngredientsMeasures().get(i)+"\n");
            }

            //Set favorite image
            setFavoriteImage();
        }
    }

    public void setFavoriteImage(){
        if(viewModel.checkIfFavorite(viewModel.getSelectedDrink()))
            Glide.with(this).load(R.mipmap.favorite_icon).into(favoriteImage);
        else
            Glide.with(this).load(R.mipmap.not_favorite_icon).into(favoriteImage);

    }

    public void SwitchFavorite(View view) {

        if(viewModel.checkIfFavorite(viewModel.getSelectedDrink())) {
            //Update drink
            viewModel.getSelectedDrink().setFavorite(false);

            //Update image
            Glide.with(this).load(R.mipmap.not_favorite_icon).into(favoriteImage);

            //Update database
            viewModel.deleteDrink(viewModel.getSelectedDrink());
        } else {
            //Update drink
            viewModel.getSelectedDrink().setFavorite(true);

            //Update image
            Glide.with(this).load(R.mipmap.favorite_icon).into(favoriteImage);

            //Update database
            viewModel.insertDrink(viewModel.getSelectedDrink());
        }
    }




}
