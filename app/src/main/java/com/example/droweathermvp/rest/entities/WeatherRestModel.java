package com.example.droweathermvp.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRestModel {
    @SerializedName("id") public int id;
    @SerializedName("main") public String main;

    public String getDescription() {
        return description;
    }

    @SerializedName("description") public String description;
    @SerializedName("icon") public String icon;
}
