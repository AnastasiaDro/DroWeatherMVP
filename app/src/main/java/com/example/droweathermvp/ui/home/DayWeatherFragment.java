package com.example.droweathermvp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.droweathermvp.R;
import com.example.droweathermvp.interfaces.FrObserver;
import com.example.droweathermvp.interfaces.FragmentMethods;
import com.example.droweathermvp.model.ImageLoader;
import com.example.droweathermvp.model.MyData;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import static com.example.droweathermvp.ui.home.DayConstants.*;

public class DayWeatherFragment extends Fragment implements FragmentMethods, FrObserver {


    //используемые View

    //первый элемент - время
    //второй - температура
    //третий - descript - строка с описанием дождь, ветер и т.п.
    private TextView [] fSoonTVsArr;
    private TextView [] sSoonTVsArr;
    private TextView [] thSoonTVsArr;

    //выгружатели иконок с сервера по строкам
    //строки в четвертом элементе входящего из DayDataPresenter-а
    private SimpleDraweeView fSoonDraweeView;
    private SimpleDraweeView sSoonDraweeView;
    private SimpleDraweeView thSoonDraweeView;


    String[] firstDataArr;
    String[] secondDataArr;
    String[] thirdDataArr;

    private String temp;
    private String descriptString;
    private String iconString;

    private MyData myData;

    private DayDataPresenter dayDataP;
    private ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = new ImageLoader();
        //создаём презентер
        dayDataP = new DayDataPresenter();
        dayDataP.setObserver(this);
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_weather, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        fSoonTVsArr = new TextView[3];
        fSoonTVsArr[TIME] = view.findViewById(R.id.f_soonTextView);
        fSoonTVsArr[TEMP] = view.findViewById(R.id.f_soonTempText);
        fSoonTVsArr[DESCRIPT_STRING] = view.findViewById(R.id.fDescriptText);
        fSoonDraweeView = view.findViewById(R.id.fSoonWeathImg);

        sSoonTVsArr = new TextView[3];
        sSoonTVsArr[TIME] = view.findViewById(R.id.s_soonTextView);
        sSoonTVsArr[TEMP] = view.findViewById(R.id.s_soonTempText);
        sSoonTVsArr[DESCRIPT_STRING] = view.findViewById(R.id.sDescriptText);
        sSoonDraweeView = view.findViewById(R.id.sSoonWeathImg);

        thSoonTVsArr = new TextView[3];
        thSoonTVsArr[TIME] = view.findViewById(R.id.th_soonTextView);
        thSoonTVsArr[TEMP] = view.findViewById(R.id.th_soonTempText);
        thSoonTVsArr[DESCRIPT_STRING] = view.findViewById(R.id.thDescriptText);
        thSoonDraweeView = view.findViewById(R.id.thSoonWeathImg);
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
            setDataForTV(fSoonTVsArr, fSoonDraweeView, dayDataP.getAfterThreeHoursWeather());
            setDataForTV(sSoonTVsArr, sSoonDraweeView, dayDataP.getAfterSixHoursWeather());
            setDataForTV(thSoonTVsArr, thSoonDraweeView, dayDataP.getAfterNineHoursWeather());
            }
        });
    }

    //поставим данные в передаваемый массив TextView
    private void setDataForTV(TextView [] arrTv, SimpleDraweeView draweeView, String [] dataForTv) {
        //с первого по 3й элемент dataForTv информация для arrTV
        for (int i = 0; i < arrTv.length; i++) {
            arrTv[i].setText(dataForTv[i]);
        }
        //в четвертом - информация для загрузки картинки
        imageLoader.loadDraweeImage(draweeView, dataForTv[3]);
    }

    @Override
    public void updateViewData() {
        setWeatherValuesToTextViews();
    }
}
