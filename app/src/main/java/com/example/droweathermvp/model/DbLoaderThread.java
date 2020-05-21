package com.example.droweathermvp.model;

import android.util.Log;


import com.example.droweathermvp.database.City;
import com.example.droweathermvp.database.CityDao;

import java.util.List;

public class DbLoaderThread extends Thread {
    CityDao cityDao;
    List <City> dataList;

//выгружает данные их базы данных
    public DbLoaderThread (CityDao cityDao, List dataList) {
        this.cityDao = cityDao;
        this.dataList = dataList;
    }

        @Override
    public void run() {
        super.run();
        dataList.addAll(cityDao.getAll());
        Log.d("DbLoaderThread", "Размер выгруженных данных: " + dataList.size());
    }
}
