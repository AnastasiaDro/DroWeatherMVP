package com.example.droweathermvp.model;

import android.util.Log;

import java.util.ArrayList;



//Класс, обрабатывающй данные из MyData для DataBaseHelper-а
public class MyDataHandler {

    MyData myData;

    public MyDataHandler(MyData myData) {
        this.myData = myData;
    }

    //подготовка данных к отправке в БД
    public void parseDataToDb(String temp, String currentTime, String iconString) {
        myData.searchedTempStringsList.add(temp);
        myData.searchedImgStringsList.add(iconString);
        myData.datesList.add(currentTime);
//    //удалим задвоенную информацию
        deleteCopyAddNewList(myData.getCurrentCity(), myData.citiesList, myData.searchedTempStringsList, myData.searchedImgStringsList, myData.datesList);
        System.out.println("УСТАНОВИЛИ ДАННЫЕ ДЛЯ ИСТОРИИ ПОИСКА");
        //поставим данные последнего найденного города в начало списка
        lastToFirstAllArrays();
    }

    //метод удаления последнего элемента из массива и сдвига всех элементов
    //используется в классе SearchAdapter
    public ArrayList deleteCopyAddNewList(String newString, ArrayList arrayList, ArrayList searchedTempStringsList, ArrayList searchedImgStringsList, ArrayList datesList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(newString)) {
                arrayList.remove(i);
                searchedTempStringsList.remove(i);
                searchedImgStringsList.remove(i);
                datesList.remove(i);
            }
        }
        arrayList.add(newString);
        return arrayList;
    }

    //перемешивает лист так, чтобы отобразить с первого до последнего
    public ArrayList<String> lastToFirst(ArrayList<String> arrayList) {
        String lastItem = arrayList.get(arrayList.size() - 1);
        arrayList.add(0, lastItem);
        arrayList.remove(arrayList.size() - 1);
        return arrayList;
    }

    //Добавить в список, если не существует этого элемента
    public ArrayList addToListIfNotExist(ArrayList arrayList, String newString) {
        int count = checkListForExistElement(newString);
        if (count == 0) {
            arrayList.add(newString);
        }
        return arrayList;
    }


    public void addCityData(String cityName, String cityTemp, String imgString, String lastLoadTime) {
        myData.citiesList.add(cityName);
        myData.searchedTempStringsList.add(cityTemp);
        myData.searchedImgStringsList.add(imgString);
        myData.datesList.add(lastLoadTime);
        Log.d("MyDataHandler", "Добавлены данные в myData");
    }

    //проверка массива на повторы элемента и подсчёт, сколько раз он повторился
    public int checkListForExistElement(String newString) {
       ArrayList arrayList = myData.citiesList;
        int count = 0;
        System.out.println(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(newString)) {
                count++;
            }
        }
        return count;
    }

    //поставим данные последнего найденного города в начало списка
    public void lastToFirstAllArrays(){
        lastToFirst(myData.searchedTempStringsList);
        lastToFirst(myData.searchedImgStringsList);
        lastToFirst(myData.citiesList);
        lastToFirst(myData.datesList);
    }

    public void setCurrentCity(String cityName){
        myData.setCurrentCity(cityName);
    }

    public String getCurrentCity() {
        return myData.getCurrentCity();
    }


}
