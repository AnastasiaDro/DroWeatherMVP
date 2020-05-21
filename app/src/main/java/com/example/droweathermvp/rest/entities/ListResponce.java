package com.example.droweathermvp.rest.entities;

import com.google.gson.annotations.SerializedName;

public class ListResponce {
    @SerializedName("coord") public CoordRestModel coordinates;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public WindRestModel wind;
    @SerializedName("dt_txt") public String textDt;
    @SerializedName("name") public String name;
    @SerializedName("id") public long id;
    //для отображения дождя
//    @SerializedName("sys") public SysRestModel sys;
//    @SerializedName("rain") public RainRestModel rain;
}
