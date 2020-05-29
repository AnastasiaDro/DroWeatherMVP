package com.example.droweathermvp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.droweathermvp.database.DataBaseHelper;
import com.example.droweathermvp.model.MyData;
import com.example.droweathermvp.receivers.BatteryReceiver;
import com.example.droweathermvp.receivers.ConnectionReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.droweathermvp.model.Constants.APP_PREFERENCES;
import static com.example.droweathermvp.model.Constants.APP_PREFERENCES_IS_AUTOTHEME;
import static com.example.droweathermvp.model.Constants.APP_PREFERENCES_IS_PRESSURE;
import static com.example.droweathermvp.model.Constants.APP_PREFERENCES_IS_WIND;
import static com.example.droweathermvp.model.Constants.APP_PREFERENCES_LAST_SEARCHED_CITY;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    //приёмник сообщений о состоянии сети
    private ConnectionReceiver connectReceiver = new ConnectionReceiver();
    //приёмник сообщений о заряде батареи
    private BatteryReceiver batteryReceiver = new BatteryReceiver();
    //сохранение настроек интерфейса
    private SharedPreferences mSettings;
    int isWind;
    int isPressure;
    int isAutoTheme;

    MyData myData;
    NavController navController;
    DataBaseHelper dbHelper;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //инициализируем канал нотификаций
        initNotificationChannel();
        myData = MyData.getInstance();
        //работа с сохраненными настройками
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        checkAndLoadSettings(mSettings);

        dbHelper = new DataBaseHelper();

//        //инициализиируем Fresco
//        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button weathByLocBtn = findViewById(R.id.weathByLocBtn);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        myData.setNavController(navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //автонастройка темы
        //TODO
        //выгрузка базы данных в myData
        dbHelper.loadDbDataToMyData();
        //выгрузка имен из полученных данных
        dbHelper.getCitiesNamesFromDbData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myData.setCurrentCity(query);
                dbHelper.addNewCityIfNotExist(query);
                System.out.println("второй запуск loadWeatherData, temp =");
                navController.navigate(R.id.nav_home);
                menu.close();
                searchView.clearFocus();
                //Почему не работает?!?!
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putInt(APP_PREFERENCES_IS_WIND, interfaceChanger.getIsWind());
//        editor.putInt(APP_PREFERENCES_IS_PRESSURE, interfaceChanger.getIsPressure());
//        editor.putInt(APP_PREFERENCES_IS_AUTOTHEME, interfaceChanger.getIsAutoThemeChanging());
        editor.putString(APP_PREFERENCES_LAST_SEARCHED_CITY, myData.getCurrentCity());
        // System.out.println("OnPause" + "interfaceChanger.getIsAutoThemeChanging()" + interfaceChanger.getIsAutoThemeChanging());
        editor.apply();
        unregisterReceiver(connectReceiver);
        unregisterReceiver(batteryReceiver);
    }

    //обработка нажатий на пункты optionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //переходим на фрагмент настроек
                navController.navigate(R.id.nav_slideshow);
                return true;
            case R.id.toTheSearchPage:
                //переходим на фрагмент поиска
                navController.navigate(R.id.nav_gallery);
                //если нажали на поиск
            case R.id.app_bar_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void updateInterfaceViewData() {
//        isWind = interfaceChanger.getIsWind();
//        isPressure = interfaceChanger.getIsPressure();
//        isAutoTheme = interfaceChanger.getIsAutoThemeChanging();
//    }


    //проверим и загрузим сохраненные настройки
    private void checkAndLoadSettings(SharedPreferences mSettings) {
        if (mSettings.contains(APP_PREFERENCES_LAST_SEARCHED_CITY)) {
            //получаем название города из настроек
            myData.setCurrentCity(mSettings.getString(APP_PREFERENCES_LAST_SEARCHED_CITY, getString(R.string.noData)));
        }
        if (mSettings.contains(APP_PREFERENCES_IS_WIND)) {
            // Получаем число из настроек
            isWind = mSettings.getInt(APP_PREFERENCES_IS_WIND, 1);
            //interfaceChanger.setWind(isWind);
        }
        if (mSettings.contains(APP_PREFERENCES_IS_PRESSURE)) {
            // Получаем число из настроек
            isPressure = mSettings.getInt(APP_PREFERENCES_IS_PRESSURE, 1);
            //interfaceChanger.setPressure(isPressure);
        }
        if (mSettings.contains(APP_PREFERENCES_IS_AUTOTHEME)) {
            // Получаем число из настроек
            isAutoTheme = mSettings.getInt(APP_PREFERENCES_IS_AUTOTHEME, 1);
            //interfaceChanger.setIsAutoThemeChanging(isAutoTheme);
        }
    }

    // инициализация канала нотификаций
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ресивер для состояния сети
        registerReceiver(connectReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        //ресивер для состояния батареи
        IntentFilter batIntFilter = new IntentFilter();
        batIntFilter.addAction(Intent.ACTION_BATTERY_LOW);
        batIntFilter.addAction(Intent.ACTION_BATTERY_OKAY);
    //    registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
       registerReceiver(batteryReceiver, batIntFilter);
    }
}


