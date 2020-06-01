package com.example.droweathermvp.rest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo {
    private static OpenWeatherRepo instanse = null;
    private IOpenWeather API;

    //для загрузки по координатам
    private ByCoordOpenWeather API_COORD;



    private OpenWeatherRepo() {
        API = createAdapter();
        API_COORD = createCoordAdapter();
    }

    public static OpenWeatherRepo getInstance()  {
        if (instanse == null) {
            instanse = new OpenWeatherRepo();
        }
        return instanse;
    }

    public IOpenWeather getAPI(){
          return API;
    }

    private IOpenWeather createAdapter(){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IOpenWeather.class);
    }


    public ByCoordOpenWeather getAPI_COORD() {
        return API_COORD;
    }
    private ByCoordOpenWeather createCoordAdapter(){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(ByCoordOpenWeather.class);
    }
}
