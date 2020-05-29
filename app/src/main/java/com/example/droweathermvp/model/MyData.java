package com.example.droweathermvp.model;

import android.util.Log;

import androidx.navigation.NavController;

import com.example.droweathermvp.database.CitiesDatabase;
import com.example.droweathermvp.database.City;
import com.example.droweathermvp.database.CityDao;
import com.example.droweathermvp.interfaces.Observable;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.rest.WeatherLoader;
import com.example.droweathermvp.ui.home.CurrentDataPresenter;
import com.example.droweathermvp.ui.home.DayDataPresenter;
import com.example.droweathermvp.ui.home.WeekDataPresenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


//Класс с данными, наблюдаемый
public class MyData implements Observable {
    boolean weatherRequestIsDone;

    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    NavController navController;
    //Переменные для вывода сообщений об исключениях
    private Exception exceptionWhileLoading;
    private int exceptionNameId;
    private int exceptionAdviceId;

    //В этот Хэшмап будем класть все погодные данные int - порядковый номер в ArrayList-е,
    //а массив строк - собственно данные: время, температура, давление, ветер
    //соответствие номеров элементов массива значениям есть в классе Constants
    private HashMap<Integer, String[]> allWeatherDataHashMap;
    private static MyData instance;
    public List<Observer> observers;
    //узнаем время
    static Date currentDate;
    int currentHour;
    public ArrayList<String> citiesList;

    String currentCity;
    private ImageLoader imageLoader;
    WeatherLoader weatherLoader;

    //список изображений, температур и имен городов, которые мы искали
    public ArrayList<String> searchedImgStringsList;
    public ArrayList<String> searchedTempStringsList;
    public ArrayList<String> datesList;

    MyDataHandler myDataHandler;

    public void setWeatherLoader(WeatherLoader weatherLoader) {
        this.weatherLoader = weatherLoader;
    }

    //Получим HashMap с погодными данными
    public HashMap<Integer, String[]> getAllWeatherDataHashMap() {
        return allWeatherDataHashMap;
    }


    private MyData() {
        currentCity = "Moscow";
        currentHour = 0;
        observers = new LinkedList<>();
        //Получим базу данных
        citiesList = new <String>ArrayList();

        allWeatherDataHashMap = new HashMap<>();
        this.weatherRequestIsDone = false;
        //пока зададим города тут
        exceptionWhileLoading = null;
        imageLoader = new ImageLoader();

        //Массивы с данными о городах, которые искали
        searchedImgStringsList = new ArrayList<>();
        searchedTempStringsList = new ArrayList<>();
        datesList = new ArrayList<>();
        myDataHandler = new MyDataHandler(this);
    }

    //сделаем наблюдаемый класс сингл-тоном
    public static MyData getInstance() {
        if (instance == null) {
            instance = new MyData();

        }
//получим текущий час
        currentDate = new Date();
        currentDate.getTime();
//вернём MyData
        return instance;
    }

    //добавить наблюдателя
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Наблюдатель добавлен. Список наблюдателей " + observers.toString());
    }

    //удалить наблюдателя
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Наблюдатель удалён. Список наблюдателей " + observers.toString());
    }

    //уведомить наблюдателей
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateData();
        }
    }

    //Высчитать текущий час
    private int takeCurrentHour(Date currentDate) {
        DateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String dateText = hourFormat.format(currentDate);
        currentHour = Integer.parseInt(dateText);
        return currentHour;
    }

    //геттер текущего часа
    public int getCurrentHour() {
        takeCurrentHour(currentDate);
        return currentHour;
    }

    //получим или изменим текущий город
    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }


    //    //Получим или изменим исключение
    public void setException(Exception exceptionWhileLoading) {
        this.exceptionWhileLoading = exceptionWhileLoading;
    }

    public void setExceptionWhileLoading(Exception exceptionWhileLoading, int exceptionNameId, int exceptionAdviceId) {
        this.exceptionWhileLoading = exceptionWhileLoading;
        this.exceptionNameId = exceptionNameId;
        this.exceptionAdviceId = exceptionAdviceId;
    }

    public Exception getException() {
        return exceptionWhileLoading;
    }

    public int getExceptionNameId() {
        return exceptionNameId;
    }

    public int getExceptionAdviceId() {
        return exceptionAdviceId;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public MyDataHandler getMyDataHandler() {
        return myDataHandler;
    }

}