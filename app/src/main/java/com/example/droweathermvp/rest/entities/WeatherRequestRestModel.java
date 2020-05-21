package com.example.droweathermvp.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestRestModel {
    @SerializedName("list") public ListResponce[] listResponce;
}
