package com.example.droweathermvp.ui.home;


import android.util.Log;

import com.example.droweathermvp.interfaces.FrObseravable;
import com.example.droweathermvp.interfaces.FrObserver;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.model.Constants;
import com.example.droweathermvp.model.MyData;
import static com.example.droweathermvp.ui.home.CurrentWeatherConstants.*;

public class CurrentDataPresenter implements Observer, FrObseravable {
    private MyData myData;
    //фрагмент-обсёрвер, из интерфейса FrObserver
    //так мы избегаем исользования неJava-классов Фрагментов в коде
    private FrObserver frObserver;

    private String[] dataArr;


    public CurrentDataPresenter() {
        myData = MyData.getInstance();
        myData.registerObserver(this);
    }

    //получим массив данных  из MyData
    private void getAllDataArrs(){
        dataArr = new String[CURRENT_DATA_ARR_SIZE];
        String[] loadedDataArr = myData
                .getAllWeatherDataHashMap()
                .get(CURRENT_DATA_KEY_IN_HASHMAP);
        dataArr[CURRENT_TIME] = loadedDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 16);
        dataArr[CURRENT_TEMP] = loadedDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY];
                //currentTemp.concat(" \u2103");
        dataArr[CURRENT_DESCRIPT_STRING] = loadedDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
        dataArr[CURRENT_ICON_STRING] = loadedDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
        dataArr[CURRENT_WIND] = loadedDataArr[Constants.WIND_KEY_IN_WEATHERDATA_ARRAY];
        dataArr[CURRENT_PRESSURE] = loadedDataArr[Constants.PRESSURE_KEY_IN_WEATHERDATA_ARRAY];
        dataArr[CURRENT_CITY] = myData.getCurrentCity();
        Log.d("CurrentDataPresenter", "getAllDataArrs() размер массива "+dataArr.length);
    }

    public String[] getCurrentDataArr() {
        return dataArr;
    }

    @Override
    public void setObserver(FrObserver observer) {
        frObserver = observer;
    }

    @Override
    public void notifyFrObserver() {
        frObserver.updateViewData();
    }

    @Override
    public void updateData() {
        getAllDataArrs();
        notifyFrObserver();
        myData.sendDataToDb(dataArr[CURRENT_TEMP], dataArr[CURRENT_TIME], dataArr[CURRENT_ICON_STRING]);
    }

    //TODO вынести в отдельный метод или класс загрузку в MyData
//    //передадим данные в массивы для города с последним поиском:
//    //имя города
//                    myData.getSearchedTempStringsList().add(forTemp);
//                    myData.getSearchedImgStringsList().add(iconString);
//                    myData.getDatesList().add(currentTime);
//    //удалим задвоенную информацию
//                    myData.deleteCopyAddNewList(myData.getCurrentCity(), myData.getCitiesList());
//                    System.out.println("УСТАНОВИЛИ ДАННЫЕ ДЛЯ ИСТОРИИ ПОИСКА");
//    //поставим данные последнего найденного города в начало списка
//                    myData.lastToFirstAllArrays();
//    //внесем данные о температуре и последнем загруженном времени в базу данных
//    //внутри этого метода мы создаём новый поток
//                    myData.addCityDataToDb(myData.getCurrentCity(), forTemp, currentTime, iconString);




}
