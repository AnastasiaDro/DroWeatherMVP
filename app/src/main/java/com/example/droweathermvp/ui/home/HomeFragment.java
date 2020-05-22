package com.example.droweathermvp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.droweathermvp.R;
import com.example.droweathermvp.interfaces.ActivMethods;
import com.example.droweathermvp.model.MyData;
import com.example.droweathermvp.rest.WeatherLoader;

public class HomeFragment extends Fragment implements ActivMethods {
    //класс Model
    MyData myData;
    //места для моих фрагментов
    private int currentWeathPlaceId;
    private int dayWeathPlaceId;
    private int weekWeathPlaceId;



    //Мои фрагменты
    private CurrentWeatherFragment curWeathFragment;
    private DayWeatherFragment dayWeathFragment;
    private WeekWeatherFragment weekWeatherFragment;
    private AppCompatActivity mainActivity;

    //штука, пока мне неведомая, но я работаю над этим =)
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        myData = MyData.getInstance();
        WeatherLoader weatherLoader = new WeatherLoader(getContext());
        weatherLoader.loadWeatherData();
        myData.setWeatherLoader(weatherLoader);
        init();
        return root;
    }

    @Override
    public void init() {
        currentWeathPlaceId = R.id.currentWeatherFrame;
        dayWeathPlaceId = R.id.dayWeatherFrame;
        weekWeathPlaceId = R.id.weekWeatherFrame;
        curWeathFragment = new CurrentWeatherFragment();
        curWeathFragment.postFragment(mainActivity, currentWeathPlaceId);
        dayWeathFragment = new DayWeatherFragment();
        dayWeathFragment.postFragment(mainActivity, dayWeathPlaceId);
        weekWeatherFragment = new WeekWeatherFragment();
        weekWeatherFragment.postFragment(mainActivity, weekWeathPlaceId);
    }
}
