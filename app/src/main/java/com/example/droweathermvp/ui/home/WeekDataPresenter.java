package com.example.droweathermvp.ui.home;

import com.example.droweathermvp.model.Constants;
import com.example.droweathermvp.model.MyData;

import java.util.ArrayList;
import java.util.HashMap;
//константы со значениями для массивов данных (время, утро, день, вечер)
import static com.example.droweathermvp.ui.home.WeekConstants.*;

public class WeekDataPresenter {
    private MyData myData;
    private HashMap<Integer, String[]> allWeatherDataHashMap;
    private String[] zeroArr;
    private int curTime;
    private String curTimeString;
    private int twentyFourHours;
    //"Шаг" между значением времени в элементах массива
    //У нас он сейчас 3
    private int timeStep;
    private int firstDayBeginIndex;
    private ArrayList indexesForFourDaysList;

    //индексы для первого дня
    int fstDayMorKey;
    int fstDayAftKey;
    int fstDayEvKey;

    //массивы для данных первого дня
    private String[] fstDayMorArr;
    private String[] fstDayAftArr;
    private String[] fstDayEvArr;

    //индексы для данных второго дня
    int scndDayMorKey;
    int scndDayAftKey;
    int scndDayEvKey;

    //массивы для данных второго дня
    private String[] scndDayMorArr;
    private String[] scndDayAftArr;
    private String[] scndDayEvArr;

    //индексы для данных третьего дня
    int thrdDayMorKey;
    int thrdDayAftKey;
    int thrdDayEvKey;

    //массивы для данных третьего дня
    private String[] thrdDayMorArr;
    private String[] thrdDayAftArr;
    private String[] thrdDayEvArr;


    public WeekDataPresenter() {
        myData = MyData.getInstance();
        indexesForFourDaysList = new ArrayList();
        twentyFourHours = 24;
        timeStep = 3;
        //TODO можно потом это вынести в отдельный поток
        //выгружаем данные из модели для последующей обработки
    }

    //выгружаем данные из модели для последующей обработки
    public void getDataFromModel(){
        takeAllIndexesForDaysData();
        getStringsArraysWithDaysData();
    }


    public int[] findDayBeginningIndexesArr() {
        allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        //получим время указанное в первом, втором и третьем элементах
        //индексы времени, из которых мы будем получать температуру
        int[] dayBeginningIndexesArr = new int[3];
        zeroArr = allWeatherDataHashMap.get(0);
        curTimeString = zeroArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY];
        curTimeString = curTimeString.substring(11, 13);
        curTime = Integer.parseInt(curTimeString);
        firstDayBeginIndex = (twentyFourHours - curTime) / 3;
        dayBeginningIndexesArr[0] = firstDayBeginIndex;
        for (int i = 1; i < 3; i++) {
            dayBeginningIndexesArr[i] = dayBeginningIndexesArr[i - 1] + 8;
        }
        return dayBeginningIndexesArr;
    }



    //получим начальные индексы, по которым будем выбирать из всех выгруженных данных нужные данные по ближайшим дням
    private void takeAllIndexesForDaysData() {
        int[] daysBeginningTimesArr = findDayBeginningIndexesArr();
        fstDayMorKey = daysBeginningTimesArr[0] + 3;
        fstDayAftKey = daysBeginningTimesArr[0] + 5;
        fstDayEvKey = daysBeginningTimesArr[0] + 7;

        //индексы для данных второго дня
        scndDayMorKey = daysBeginningTimesArr[1] + 3;
        scndDayAftKey = daysBeginningTimesArr[1] + 5;
        scndDayEvKey = daysBeginningTimesArr[1] + 7;

        //индексы для данных третьего дня
        thrdDayMorKey = daysBeginningTimesArr[2] + 3;
        thrdDayAftKey = daysBeginningTimesArr[2] + 5;
        thrdDayEvKey = daysBeginningTimesArr[2] + 7;
    }

    //получим с помощью индексов массивы строк, по которым будем искать значения
    private void getStringsArraysWithDaysData() {
        //массив строк с данными для первого дня
        HashMap<Integer, String[]> allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        fstDayMorArr = allWeatherDataHashMap.get(fstDayMorKey);
        fstDayAftArr = allWeatherDataHashMap.get(fstDayAftKey);
        fstDayEvArr = allWeatherDataHashMap.get(fstDayEvKey);
        //массив строк с данными для второго дня
        scndDayMorArr = allWeatherDataHashMap.get(scndDayMorKey);
        scndDayAftArr = allWeatherDataHashMap.get(scndDayAftKey);
        scndDayEvArr = allWeatherDataHashMap.get(scndDayEvKey);
        //массив строк с данными для третьего дня
        thrdDayMorArr = allWeatherDataHashMap.get(thrdDayMorKey);
        thrdDayAftArr = allWeatherDataHashMap.get(thrdDayAftKey);
        thrdDayEvArr = allWeatherDataHashMap.get(thrdDayEvKey);
    }

    //получим данные для конкретных дней

    //массив данных для текстВью первого дня
    public String [] takeFirstDayDataForTVArr(){
        String [] firstDayDataForTVArr =  takeDayDataArr(fstDayMorArr, fstDayAftArr, fstDayEvArr);
       return firstDayDataForTVArr;
    }

    //массив  данных для текствью для второго дня
    public String [] takeSecondDayDataForTVArr(){
        String [] secondDayDataForTVArr =  takeDayDataArr(scndDayMorArr, scndDayAftArr, scndDayEvArr);
        return secondDayDataForTVArr;
    }

    //массив  данных для текстВью третьего дня
    public String [] takeThirdDayDataForTVArr(){
        String [] thirdDayDataForTVArr =  takeDayDataArr(thrdDayMorArr, thrdDayAftArr, thrdDayEvArr);
        return thirdDayDataForTVArr;
    }

    //получение данных для textView для конкретного дня из от общего массива данных этого дня
    private String[] takeDayDataArr(String[] dayMorArr, String [] dayAftArr, String [] dayEvArr){
        String[] dataForTVArr = new String[4];
        //первым идёт время
        dataForTVArr[DAY_DATA] = dayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10);
        //вторым температура утром
        dataForTVArr[MORNING_TEMP] = dayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
        //третьим температура днем
        dataForTVArr[AFTERNOON_TEMP] = dayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
        //четвертым - температура вечером
        dataForTVArr[EVENING_TEMP] = dayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
        return dataForTVArr;
    }
}


