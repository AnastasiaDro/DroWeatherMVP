package com.example.droweathermvp.ui.home;

public class DayConstants {
    public static final int DAY_FIRST_DATA_KEY_IN_HASHMAP = 1;
    public static final int DAY_SECOND_DATA_KEY_IN_HASHMAP = 2;
    public static final int DAY_THIRD_DATA_KEY_IN_HASHMAP = 3;

    //какое место в массиве займет какая информация
    //дата
    public static final int TIME = 0;
    //температура
    public static final int TEMP = 1;
    //дождь, гроза и т.п.
    public static final int DESCRIPT_STRING = 2;

    //строка для выгрузки иконки
    //должна быть последней в массиве!!!
    //Иначе в классе CurrentWeatherFragment в методе setDataForTV() будет ошибка
    public static final int ICON_STRING = 3;
}
