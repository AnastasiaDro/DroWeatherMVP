package com.example.droweathermvp.model;

import android.util.Log;

import androidx.navigation.NavController;

import com.example.droweathermvp.database.CitiesDatabase;
import com.example.droweathermvp.database.City;
import com.example.droweathermvp.database.CityDao;
import com.example.droweathermvp.interfaces.Observable;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.rest.WeatherLoader;

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
    ArrayList<String> citiesList;


    private HashMap citiesMap;
    String currentCity;
    private ImageLoader imageLoader;
    WeatherLoader weatherLoader;

    //список изображений, температур и имен городов, которые мы искали
    ArrayList<String> searchedImgStringsList;
    ArrayList<String> searchedTempStringsList;
    ArrayList<String> datesList;

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


//    //метод удаления последнего элемента из массива и сдвига всех элементов
//    //используется в классе SearchAdapter
//    public ArrayList deleteCopyAddNewList(String newString, ArrayList arrayList) {
//        for (int i = 0; i < arrayList.size(); i++) {
//            if (arrayList.get(i).equals(newString)) {
//                arrayList.remove(i);
//                searchedTempStringsList.remove(i);
//                searchedImgStringsList.remove(i);
//                datesList.remove(i);
//            }
//        }
//        arrayList.add(newString);
//        return arrayList;
//    }
//
//    public ArrayList<String> lastToFirst(ArrayList<String> arrayList) {
//        String lastItem = arrayList.get(arrayList.size() - 1);
//        arrayList.add(0, lastItem);
//        arrayList.remove(arrayList.size() - 1);
//        return arrayList;
//    }
//
//    public ArrayList addToListIfNotExist(ArrayList arrayList, String newString) {
//        int count = checkListForExistElement(arrayList, newString);
//        if (count == 0) {
//            arrayList.add(newString);
//        }
//        return arrayList;
//    }
//
//    //метод выгрузки данных из БД
//    public void loadDbDataToMyData() {
//        DbLoaderThread dbLoaderThread = new DbLoaderThread(cityDao, citiesDataList);
//        dbLoaderThread.start();
//        try {
//            dbLoaderThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Log.d("MyData", "ОШИБКА В ПОДОЖДАТЬ ПОТОК dbLoaderThread");
//        }
//    }
//
//    //получим имена выгруженных городов
//    //метод запускается в MainActivity
//    public void getCitiesNamesFromDbData() {
//        for (int i = 0; i < citiesDataList.size(); i++) {
//            citiesList.add(citiesDataList.get(i).cityName);
//            searchedTempStringsList.add(citiesDataList.get(i).cityTemp);
//            searchedImgStringsList.add(citiesDataList.get(i).imgString);
//            datesList.add(citiesDataList.get(i).lastLoadTime);
//        }
//    }
//
//
//    //метод добавления города если его ещё не было
//    public void addNewCityIfNotExist(String newName) {
//        int count = checkListForExistElement(citiesList, newName);
//        if (count == 0) {
//            //     citiesList.add(newName);
//            final City city = new City();
//            city.cityName = newName;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    cityDao.insert(city);
//                }
//            }).start();
//        }
//    }
//
//    //метод удаления города из базы данных
//    public void deleteCityFromDb(final String cityName) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                City city = cityDao.getByCityName(cityName);
//                cityDao.delete(city);
//            }
//        }).start();
//    }
//
//    //добавляем данные города, создавая новый поток
//    public void addCityDataToDb(final String cityName, final String temp, final String lastLoadTime, final String imgString) {
//        //проверим массив на пустоту
//        if (citiesDataList.size() != 0) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    cityDao.updateCityTempInDb(cityName, temp);
//                    cityDao.updateCityLoadTimeInDp(cityName, lastLoadTime);
//                    cityDao.updateCityImgInDb(cityName, imgString);
//                }
//            }).start();
//        }
//    }
//
//    //проверка массива на повторы элемента и подсчёт, сколько раз он повторился
//    private int checkListForExistElement(ArrayList arrayList, String newString) {
//        int count = 0;
//        System.out.println(arrayList.size());
//        for (int i = 0; i < arrayList.size(); i++) {
//            if (arrayList.get(i).equals(newString)) {
//                count++;
//            }
//        }
//        return count;
//    }
//
//    public void lastToFirstAllArrays(){
//        lastToFirst(searchedTempStringsList);
//        lastToFirst(searchedImgStringsList);
//        lastToFirst(citiesList);
//        lastToFirst(datesList);
//    }

//    public void sendDataToDb(String temp, String currentTime, String iconString) {
//        getSearchedTempStringsList().add(temp);
//        getSearchedImgStringsList().add(iconString);
//        getDatesList().add(currentTime);
////    //удалим задвоенную информацию
//        deleteCopyAddNewList(getCurrentCity(), getCitiesList());
//       System.out.println("УСТАНОВИЛИ ДАННЫЕ ДЛЯ ИСТОРИИ ПОИСКА");
//   //поставим данные последнего найденного города в начало списка
//        lastToFirstAllArrays();
////    //внесем данные о температуре и последнем загруженном времени в базу данных
////    //внутри этого метода мы создаём новый поток
//      addCityDataToDb(getCurrentCity(), temp, currentTime, iconString);
//    }
}



