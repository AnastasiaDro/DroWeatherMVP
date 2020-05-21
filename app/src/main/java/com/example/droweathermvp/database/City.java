package com.example.droweathermvp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey (autoGenerate = true)
    public Integer id;

    @ColumnInfo
    public String cityName;

    public String cityTemp;
    public String lastLoadTime;
    public String imgString;

//геттеры








}
