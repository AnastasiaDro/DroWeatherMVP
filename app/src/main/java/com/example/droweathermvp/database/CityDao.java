package com.example.droweathermvp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CityDao {

   @Query("SELECT * FROM city")
    List<City> getAll();

   @Query("SELECT * FROM city WHERE id = :id")
    City getById(long id);

   //получить по имени города
   @Query("SELECT * FROM city WHERE cityName = :cityName")
   City getByCityName(String cityName);

 //  добавить к городу температуру
    @Query("UPDATE city SET cityTemp = :newTemp WHERE cityName LIKE :cityName")
    void updateCityTempInDb(String cityName, String newTemp);

    //добавить к городу время запроса
    @Query("UPDATE city SET lastLoadTime = :newTime WHERE cityName LIKE :cityName")
    void updateCityLoadTimeInDp(String cityName, String newTime);

    //добавить к городу строку картинки
    @Query("UPDATE city SET imgString = :newImgString WHERE cityName LIKE :cityName")
    void updateCityImgInDb(String cityName, String newImgString);

   @Insert
    void insert(City city);

   @Update
    void update(City city);

   @Delete
    void delete(City city);
}
