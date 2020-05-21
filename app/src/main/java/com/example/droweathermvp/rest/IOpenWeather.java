package com.example.droweathermvp.rest;

import com.example.droweathermvp.rest.entities.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {

    @GET("data/2.5/forecast")
        //Прописываем нашу модель
    Call<WeatherRequestRestModel> loadWeather(@Query("q") String city,
                                              @Query("appid") String keyApi
    );
}
