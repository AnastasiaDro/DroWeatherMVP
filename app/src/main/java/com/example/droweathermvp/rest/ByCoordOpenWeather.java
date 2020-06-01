package com.example.droweathermvp.rest;

import com.example.droweathermvp.rest.entities.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ByCoordOpenWeather {

    @GET("data/2.5/forecast")
        //Прописываем нашу модель
    Call<WeatherRequestRestModel> loadWeather(@Query("lat") String latitude,
                                              @Query("lon") String longitude,
                                              @Query("appid") String keyApi
    );
}
