package com.example.droweathermvp.ui.home;

public class CurrentWeatherConstants {

    //номер элемента с данными текущей погоды в массиве JSON
    //текущая погода всегда первая
    public static final int CURRENT_DATA_KEY_IN_HASHMAP = 0;

    //сколько всего элементов в массиве данных для Presenter-а
    public static final int CURRENT_DATA_ARR_SIZE = 7;

    //какое место в массиве займет какая информация
    //дата

    //температура
    public static final int CURRENT_TEMP = 0;
    //дождь, гроза и т.п.
    public static final int CURRENT_DESCRIPT_STRING = 1;

    //ветер
    public static final int CURRENT_WIND = 2;
    //давление
    public static final int CURRENT_PRESSURE = 3;
    //город
    public static final int CURRENT_CITY = 4;

    //тоже не нужно нам в CurrentWeatherFragment
    public static final int CURRENT_TIME = 5;
    //иконка
    //должна быть последней в массиве!!!
    //Иначе в классе CurrentWeatherFragment в методе setDataForTV() будет ошибка
    public static final int CURRENT_ICON_STRING = 6;

}
