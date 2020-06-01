package com.example.droweathermvp.rest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.droweathermvp.R;
import com.example.droweathermvp.model.MyData;
import com.example.droweathermvp.rest.entities.WeatherRequestRestModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//помогает получать данные с сервера
public class WeatherLoader {
    //Засечем время выполнения
    private String city;
    String latitude;
    String longitude;
    private MyData myData;
    private HashMap<Integer, String[]> takenWeatherData;
    Context context;
    Exception e;

    private String gotTime;
    private String gotTemp;
    private String gotPressure;
    private String gotWind;
    private String gotRain;
    private String gotIconIdStr;
    private String apiKey;

    //Конструктор
    public WeatherLoader(Context context) {
        this.myData = myData.getInstance();
        city = myData.getCurrentCity();
        latitude = myData.getLatitude();
        longitude = myData.getLongitude();
        takenWeatherData = myData.getAllWeatherDataHashMap();
        apiKey = "cf6eb93358473e7ee159a01606140722";
        this.context = context;
        e = new Exception();
    }

    public void loadWeatherByCity() {
        OpenWeatherRepo.getInstance().getAPI().loadWeather(city + ",RU", apiKey)
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call, @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        } else {
                            //если код не в диапазоне [200...300) то случилась ошибка
                            //здесь её обрабатываем
                            //Не работает
                            myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
                            if (response.code() == 500) {
                                //Произошёл Internal Server Error, обраюотать
                            } else if (response.code() == 401) {
                                //не авторизованы, обработать
                                // и т.д.
                                //не работает, так как приходит Responce с сервера
//                            } else if (response.code() == 404) {
//                                myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
                            }
                        }
                        myData.notifyObservers();
                        Log.d("WeatherLOADER СРАБОТАЛ", "СРАБОТАЛ");
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        myData.setExceptionWhileLoading(e, R.string.connectionError, R.string.adviceConnectonError);
                        showExceptionAlert();
                        myData.setException(null);
                        myData.notifyObservers();
                    }
                });
    }

    public void loadWeatherByCoord(){
        OpenWeatherRepo.getInstance().getAPI_COORD().loadWeather(latitude, longitude, apiKey)
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call, @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        } else {
                            //если код не в диапазоне [200...300) то случилась ошибка
                            //здесь её обрабатываем
                            //Не работает
                            myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
                            if (response.code() == 500) {
                                //Произошёл Internal Server Error, обраюотать
                            } else if (response.code() == 401) {
                                //не авторизованы, обработать
                                // и т.д.
                                //не работает, так как приходит Responce с сервера
//                            } else if (response.code() == 404) {
//                                myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
                            }
                        }
                        myData.notifyObservers();
                        Log.d("WeatherLOADER СРАБОТАЛ", "СРАБОТАЛ");
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        myData.setExceptionWhileLoading(e, R.string.connectionError, R.string.adviceConnectonError);
                        showExceptionAlert();
                        myData.setException(null);
                        if (takenWeatherData.size()>0) {
                            myData.notifyObservers();
                        }
                    }
                });
    }


    private void renderWeather(WeatherRequestRestModel model) {
        for (int i = 0; i < 40; i++) {
            //здесь нужно засунуть всё в HashMap в MyData
            gotTime = model.listResponce[i].textDt;
            gotTemp = String.valueOf(model.listResponce[i].main.getTemp());
            gotPressure = String.valueOf(model.listResponce[i].main.getPressure());
            gotWind = String.valueOf(model.listResponce[i].wind.speed);
            gotRain = model.listResponce[i].weather[0].description;
            gotIconIdStr = model.listResponce[i].weather[0].icon;
            String[] weatherDataArr = {gotTime, gotTemp, gotPressure, gotWind, gotRain, gotIconIdStr};
            takenWeatherData.put(i, weatherDataArr);
        }
    }


    public void showExceptionAlert() {
        final int exceptionStringId = myData.getExceptionNameId();
        final int adviceStringId = myData.getExceptionAdviceId();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(exceptionStringId);
        builder.setMessage(adviceStringId);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
