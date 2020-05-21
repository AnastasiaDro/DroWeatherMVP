package com.example.droweathermvp.ui.home;

import com.example.droweathermvp.model.MyData;

import java.util.HashMap;

public class DayDataPresenter {

    HashMap<Integer, String[]> curHashMap;
    String[] firstDataArr;
    String[] secondDataArr;
    String[] thirdDataArr;

    private String temp;
    private String descriptString;
    private String iconString;

    private MyData myData;





    HashMap<Integer, String[]> curHashMap = myData.getAllWeatherDataHashMap();
//                try {
//                    //Через три часа
//                    firstDataArr = curHashMap.get(DAY_FIRST_DATA_KEY_IN_HASHMAP);
//                    fSoonTimeText.setText(firstDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = firstDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    fSoonTempText.setText(temp);
//                    descriptString = firstDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    fDescriptText.setText(descriptString);
//                    iconString = firstDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(fSoonDraweeView, firstDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                    //Через 6 часов
//                    secondDataArr = curHashMap.get(DAY_SECOND_DATA_KEY_IN_HASHMAP);
//                    sSoonTimeText.setText(secondDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = secondDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    sSoonTempText.setText(temp);
//                    descriptString = secondDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    sDescriptText.setText(descriptString);
//                    iconString = secondDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(sSoonDraweeView, secondDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                    //Через 9 часов
//                    thirdDataArr = curHashMap.get(DAY_THIRD_DATA_KEY_IN_HASHMAP);
//                    thSoonTimeText.setText(thirdDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = thirdDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    thSoonTempText.setText(temp);
//                    descriptString = thirdDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    thDescriptText.setText(descriptString);
//                    iconString = thirdDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(thSoonDraweeView, thirdDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }

}
