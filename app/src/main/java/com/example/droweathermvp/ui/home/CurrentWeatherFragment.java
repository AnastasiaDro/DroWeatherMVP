package com.example.droweathermvp.ui.home;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.droweathermvp.R;
import com.example.droweathermvp.interfaces.FrObserver;
import com.example.droweathermvp.interfaces.FragmentMethods;


import com.example.droweathermvp.model.ImageLoader;
import static com.example.droweathermvp.ui.home.CurrentWeatherConstants.*;
import com.example.droweathermvp.ui.ThermometerView;
import com.facebook.drawee.view.SimpleDraweeView;

public class CurrentWeatherFragment extends Fragment implements FragmentMethods, FrObserver {

    //используемые View
    //в этом массиве все загружаемые данные
    private TextView[] curWeathTVsArr;
    //первый элемент - время (дата)
    //второй - температура
    //третий - description
    //четвёртый - ветер
    //пятый - давление
    //шестой - город
    //седьмой - ссылка на иконку

    //градусник
    private ThermometerView thermometerView;
    //сюда поставится картинка с облаками/солнцем/дождем
    private SimpleDraweeView draweeView;

    private CurrentDataPresenter currentDataP;
    private ImageLoader imageLoader;
    //для высчитывания цвета на градуснике
    private int currentTemp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = new ImageLoader();
        //получаем презентер
        currentDataP = new CurrentDataPresenter();
        currentDataP.setObserver(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        //-1, потому что последний элемент - в массиве выгруженных данных - строчка для загрузки погодного изображения
        curWeathTVsArr = new TextView[CURRENT_DATA_ARR_SIZE-2];
       curWeathTVsArr[CURRENT_CITY] = view.findViewById(R.id.cityTextView);
       curWeathTVsArr[CURRENT_TEMP] = view.findViewById(R.id.temperatureTextView);
        curWeathTVsArr[CURRENT_DESCRIPT_STRING] = view.findViewById(R.id.descriptTextView);
        curWeathTVsArr[CURRENT_WIND] = view.findViewById(R.id.windTextView);
        curWeathTVsArr[CURRENT_PRESSURE] = view.findViewById(R.id.pressureTextView);
        thermometerView = view.findViewById(R.id.thermometerView);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.curWeathImg);
    }


    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    //Ставить текст
    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                setDataForTV(curWeathTVsArr, draweeView, currentDataP.getCurrentDataArr());
                compareTemp(currentTemp);
            }
        });
    }

    //поставим данные в передаваемый массив TextView
    private void setDataForTV(TextView [] arrTv, SimpleDraweeView draweeView, String [] dataForTv) {
        //с первого по 5й элемент (включительно) dataForTv информация для arrTV
        for (int i = 0; i < arrTv.length; i++) {
            arrTv[i].setText(dataForTv[i]);
        }
        //вытащим текущую темпеатуру для изменения цвета градусника
        currentTemp = Integer.parseInt(dataForTv[CURRENT_TEMP]);
        //в четвертом - информация для загрузки картинки
        imageLoader.loadDraweeImage(draweeView, dataForTv[CURRENT_ICON_STRING]);
    }

    //в зависимости от температуры меняем цвет полоски в градуснике
    //TODO подумать, как перенести в класс термометра
    private void compareTemp(int currentTemp) {
        thermometerView.changeTempColor(currentTemp);
    }

    @Override
    public void updateViewData() {
        setWeatherValuesToTextViews();
    }

    @Override
    public void onPause(){
        super.onPause();
        currentDataP.removeObserver();
    }
}
