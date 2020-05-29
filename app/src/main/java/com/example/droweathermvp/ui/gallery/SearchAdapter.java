package com.example.droweathermvp.ui.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.droweathermvp.R;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.model.MyData;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter implements Observer {
    MyData myData;
    ArrayList<String> imgStringsList;
    ArrayList<String> tempStringsList;
    ArrayList<String> citiesNamesList;
    ArrayList<String> datesList;
    String imgString;

    public SearchAdapter() {
        this.myData = MyData.getInstance();

        imgString = null;
        imgStringsList= myData.searchedImgStringsList;
        tempStringsList=myData.searchedTempStringsList;
        citiesNamesList = myData.citiesList;
        datesList = myData.datesList;
    }

    @Override
    public void updateData() {
        Log.d("SearchAdapter", "UpdateData");
        imgStringsList= myData.searchedImgStringsList;
        tempStringsList=myData.searchedTempStringsList;
        citiesNamesList = myData.citiesList;
        datesList = myData.datesList;
        getItemCount();
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleDraweeView weathDraweeView;
        public TextView tempTV;
        public TextView cityNameTV;
        public TextView dateTV;
        RelativeLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            final NavController navController = myData.getNavController();
            weathDraweeView = itemView.findViewById(R.id.searchWeathImg);
            tempTV = itemView.findViewById(R.id.searchCityTempText);
            cityNameTV = itemView.findViewById(R.id.searchCityNameText);
            dateTV = itemView.findViewById(R.id.searchDateText);
            cardView = itemView.findViewById(R.id.mySearchCard);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String selectedCityName = cityNameTV.getText().toString();
                    final String selectedCityTemp = tempTV.getText().toString();
                    final String selectedCityImg = imgString;
                    final String selectedCityDate = dateTV.getText().toString();
                    myData.setCurrentCity(selectedCityName);
                    navController.navigate(R.id.nav_home);
                    myData.notifyObservers();
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        MyViewHolder myViewHolder = new SearchAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SimpleDraweeView draweeView = holder.itemView.findViewById(R.id.searchWeathImg);
        final TextView searchedCityTemp = holder.itemView.findViewById(R.id.searchCityTempText);
        final TextView searchedCityName = holder.itemView.findViewById(R.id.searchCityNameText);
        final TextView searchedCityDate = holder.itemView.findViewById(R.id.searchDateText);
        imgString = imgStringsList.get(position);
        System.out.println("IMGSTRING "+ imgString);
        try {
            myData.getImageLoader().loadDraweeImage(draweeView, imgString);
        } catch (NullPointerException e) {
            Log.d("onBindViewHolder", "haven't image");
            Log.d("ImgStringList", myData.searchedImgStringsList.toString());
        }
        searchedCityTemp.setText(tempStringsList.get(position));
        searchedCityName.setText(citiesNamesList.get(position));
        searchedCityDate.setText(datesList.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesNamesList.size();
    }
}
