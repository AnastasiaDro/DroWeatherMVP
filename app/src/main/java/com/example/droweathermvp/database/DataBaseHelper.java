package com.example.droweathermvp.database;

import android.util.Log;

import com.example.droweathermvp.model.App;
import com.example.droweathermvp.model.DbLoaderThread;
import com.example.droweathermvp.model.MyData;
import com.example.droweathermvp.model.MyDataHandler;
import com.facebook.common.logging.LoggingDelegate;

import java.util.ArrayList;


//загружает данные из БД в MyData и
public class DataBaseHelper {

    MyData myData;
    //для базы данных
    CitiesDatabase db;
    ArrayList<City> citiesDataList;
    CityDao cityDao;
    MyDataHandler myDataHandler;


public DataBaseHelper () {
    myData = MyData.getInstance();
    //подключимся к базе данных
    db = App.getInstance().getDatabase();
    cityDao = db.cityDao();
    citiesDataList = new ArrayList<>();
    this.myDataHandler = myData.getMyDataHandler();
    }


    //метод выгрузки данных из БД
    public void loadDbDataToMyData() {
        DbLoaderThread dbLoaderThread = new DbLoaderThread(cityDao, citiesDataList);
        dbLoaderThread.start();
        try {
            dbLoaderThread.join();
            Log.d("размер после выгрузки", citiesDataList.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("MyData", "ОШИБКА В ПОДОЖДАТЬ ПОТОК dbLoaderThread");
        }

    }


    //    //метод добавления города если его ещё не было
    public void addNewCityIfNotExist(String newName) {
        int count = myDataHandler.checkListForExistElement(newName);
        if (count == 0) {
            //     citiesList.add(newName);
            final City city = new City();
            city.cityName = newName;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cityDao.insert(city);
                }
            }).start();
        }
    }

    //основной метод отправки в базу данных
    //отправляем в классе CurrentDataPresenter
    public void sendDataToDb(String temp, String currentTime, String iconString) {

        myDataHandler.parseDataToDb(temp, currentTime, iconString);
//    //внесем данные о температуре и последнем загруженном времени в базу данных
//    //внутри этого метода мы создаём новый поток
        addCityDataToDb(myData.getCurrentCity(), temp, currentTime, iconString);
    }

    //получим имена выгруженных городов
    //метод запускается в MainActivity
    public void getCitiesNamesFromDbData() {
    City city;
        for (int i = 0; i < citiesDataList.size(); i++) {
            city = citiesDataList.get(i);
            myDataHandler.addCityData(city.cityName, city.cityTemp, city.imgString, city.lastLoadTime);
            Log.d("getCitiesFromDb", "название города" + city.cityName + " температура " + city.cityTemp +" строчка изображения "+ city.imgString +" время загрузки "+ city.lastLoadTime);
        }
    }

        //метод удаления города из базы данных
        public void deleteCityFromDb(final String cityName) {
            new Thread(() -> {
                City city = cityDao.getByCityName(cityName);
                cityDao.delete(city);
            }).start();
        }

        //добавляем данные города, создавая новый поток
        public void addCityDataToDb(String cityName, String temp, String lastLoadTime, String imgString) {
            Log.d("addCityDataToDb", cityName + " " + temp + " "+ lastLoadTime + " "+ imgString);
            Log.d("citieaDataList", citiesDataList.toString());
            //проверим массив на пустоту
           // if (citiesDataList.size() != 0) {
                new Thread(() -> {

                    cityDao.updateCityTempInDb(cityName, temp);
                    cityDao.updateCityLoadTimeInDp(cityName, lastLoadTime);
                    cityDao.updateCityImgInDb(cityName, imgString);
                }).start();
            //}
        }


}
