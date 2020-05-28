package com.example.droweathermvp.ui.slideshow;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;


import com.example.droweathermvp.R;
import com.example.droweathermvp.database.DataBaseHelper;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.model.Constants;
import com.example.droweathermvp.model.MyData;
import com.example.droweathermvp.rest.WeatherLoader;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter implements Observer {
    MyData myData;
    ArrayList<String> citiesList;
    WeatherLoader weatherLoader;
    DataBaseHelper dataBaseHelper;

    public MyAdapter() {
        this.myData = MyData.getInstance();
        this.citiesList = myData.citiesList;
        dataBaseHelper = new DataBaseHelper();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TextView textCityName = holder.itemView.findViewById(R.id.searchCityNameTV);
        textCityName.setText(citiesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public void updateData() {
        citiesList = myData.citiesList;
        this.notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textCityName;
        CardView cardView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            final NavController navController = myData.getNavController();
            textCityName = itemView.findViewById(R.id.searchCityNameTV);
            cardView = itemView.findViewById(R.id.myLinearCard);
            cardView.setOnCreateContextMenuListener(this);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String currentCityName = textCityName.getText().toString();
                    myData.setCurrentCity(currentCityName);
                    System.out.println("Текущий город в myData " + myData.getCurrentCity());
                    navController.navigate(R.id.nav_home);
                    myData.notifyObservers();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menu.add(0, this.getAdapterPosition(), 200, R.string.delete);
            menu.add(0, Constants.HIDE_CONTEXTMENU_ITEM, 300, R.string.delete);
        }
    }

    //удалить элемент
    public void deleteItem(int position) {
        //удалить из списка городов в myData
        if (myData.citiesList.size() > 1) {
            //удалить из базы данных (создается отдельный поток в методе deleteCityFromDb()
            dataBaseHelper.deleteCityFromDb(myData.citiesList.get(position));
            myData.citiesList.remove(position);
        } else {
            //удалить из базы данных
            //удалить из базы данных (создается отдельный поток в методе deleteCityFromDb()
            dataBaseHelper.deleteCityFromDb(myData.citiesList.get(position));
            myData.citiesList.remove(position);
        }
        System.out.println("myData cities list " + myData.citiesList.toString());
        myData.notifyObservers();
        notifyDataSetChanged();
    }
}

