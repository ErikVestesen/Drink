package com.example.opskrift.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.opskrift.Model.Business_Classes.Drink;
import com.example.opskrift.R;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {

    private List<Drink> allDrinks;

    final private OnListItemClickListener onListItemClickListener;

    public DrinkAdapter(List<Drink> drinks, OnListItemClickListener listener) {
        allDrinks = drinks;
        onListItemClickListener = listener;
    }

    //responsible for converting your single list item layout file from XML into View objects and storing them in a ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.drink_list_item, parent, false);
        return new ViewHolder(view);
    }

    //responsible for setting the data from the data source on each relevant view.
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(allDrinks.get(position).getName());
        Glide.with(viewHolder.image).load(allDrinks.get(position).getDrinkThumbnail()).into(viewHolder.image);
    }

    public int getItemCount(){
        if(allDrinks != null)
            return allDrinks.size();
        return 0;
    }

    //class to contain the Views that will be send to the RecyclerView. Include the Views that needs to display data.(name and image etc)
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        ImageView image;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.DrinkName);
            image = itemView.findViewById(R.id.DrinkImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClickListener.onListItemClick(getAdapterPosition()); //getAdapterPosition is the position of the View that was clicked
        }
    }

    //own click listener for recyclerView
    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}

