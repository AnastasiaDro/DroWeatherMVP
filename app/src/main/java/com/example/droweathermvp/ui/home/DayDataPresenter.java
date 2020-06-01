package com.example.droweathermvp.ui.home;

import static com.example.droweathermvp.ui.home.DayConstants.*;

import com.example.droweathermvp.interfaces.FrObseravable;
import com.example.droweathermvp.interfaces.FrObserver;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.model.Constants;
import com.example.droweathermvp.model.MyData;


import java.util.HashMap;


public class DayDataPresenter implements Observer, FrObseravable {
    private MyData myData;
    //фрагмент-обсёрвер, из интерфейса FrObserver
    //так мы избегаем исользования неJava-классов Фрагментов в коде
    private FrObserver frObserver;

    HashMap<Integer, String[]> curHashMap;
    private String[] firstDataArr;
    private String[] secondDataArr;
    private String[] thirdDataArr;

    public DayDataPresenter(){
        myData = MyData.getInstance();
        myData.registerObserver(this);
    }

//получим массивы данных для всех трех времен этого дня из MyData
    private void getAllDataArrs() {
        curHashMap = myData.getAllWeatherDataHashMap();
        //Через три часа
        firstDataArr = curHashMap.get(DAY_FIRST_DATA_KEY_IN_HASHMAP);
        //Через 6 часов
        secondDataArr = curHashMap.get(DAY_SECOND_DATA_KEY_IN_HASHMAP);
        //Через 9 часов
        thirdDataArr = curHashMap.get(DAY_THIRD_DATA_KEY_IN_HASHMAP);
    }

    //сформируем выходной массив данных для установки в TextView в классе-наблюдателе
    private String[] getDataArrayForTime(String[] timeDataArr){
        String [] dataArr = new String[4];
        dataArr[TIME] = timeDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16);
        dataArr[TEMP] = timeDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
        dataArr[DESCRIPT_STRING]  = timeDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
        dataArr[ICON_STRING]= timeDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
        return dataArr;
    }

    //получить первый массив данных (погоду через 3 часа)
    public String[] getAfterThreeHoursWeather(){
        return getDataArrayForTime(firstDataArr);
    }

    //получить второй массив данных (погоду через 6 часов)
    public String[] getAfterSixHoursWeather(){
        return getDataArrayForTime(secondDataArr);
    }

    //получить третий массив данных (погоду через 9 часов)
    public String[] getAfterNineHoursWeather(){
        return getDataArrayForTime(thirdDataArr);
    }

    //установить фрагмент-наблюдатель
    @Override
    public void setObserver(FrObserver observer) {
        this.frObserver = observer;
    }

    @Override
    public void removeObserver(){
        frObserver = null;
        myData.removeObserver(this);
    }

    //уведомить наблюдателя
    @Override
    public void notifyFrObserver() {
        frObserver.updateViewData();
    }

    //когда MyData говорит, что изменилась, на самом деле нужно только при вводе в строку поиска,
    // если на этом фрагменте находишься
    @Override
    public void updateData() {
            getAllDataArrs();
            if (frObserver != null) {
                notifyFrObserver();
            }
    }

}
