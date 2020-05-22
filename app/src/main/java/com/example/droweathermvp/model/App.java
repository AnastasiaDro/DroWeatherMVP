package com.example.droweathermvp.model;

import android.app.Application;

import androidx.room.Room;

import com.example.droweathermvp.database.CitiesDatabase;
import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {

    public static App instance;
    MyData myData;
    private CitiesDatabase database;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, CitiesDatabase.class, "database")
                .build();
        //чтобы не пересоздавался myData
        myData = MyData.getInstance();
        //инициализиируем Fresco
        Fresco.initialize(this);
    }

    public static App getInstance() {
        return instance;
    }

    public CitiesDatabase getDatabase() {
        return database;
    }
}
