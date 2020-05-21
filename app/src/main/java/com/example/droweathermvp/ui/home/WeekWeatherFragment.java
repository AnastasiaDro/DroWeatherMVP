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
import com.example.droweathermvp.interfaces.FragmentMethods;
import com.example.droweathermvp.interfaces.Observer;
import com.example.droweathermvp.ui.home.WeekConstants;

public class WeekWeatherFragment extends Fragment implements FragmentMethods, Observer {

    //TextView по дням недели
    //TextView для времени и температур по дням и времени суток
    //первый день
    private TextView [] firstDayTVArr;
    //первый элемент - время (дата)
    //второй - температура утром
    //третий - температура днем
    //четвертый - температура вечером

    //второй день
    private TextView [] secondDayTVArr;

    //третий день
    private TextView [] thirdDayTVArr;

    //Parser, ищущий индексы для забора данных и данные
    private WeekDataPresenter weekDataP;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weekDataP = new WeekDataPresenter();
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_weather, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        //Температура по дням недели: утро, день, вечер
        //первый день
        firstDayTVArr = new TextView[4];
        firstDayTVArr[WeekConstants.DAY_DATA] = view.findViewById(R.id.firstDayTimeText);
        firstDayTVArr[WeekConstants.MORNING_TEMP] = view.findViewById(R.id.firstDayMorningTempText);
        firstDayTVArr[WeekConstants.AFTERNOON_TEMP] = view.findViewById(R.id.firstDayAftTempText);
        firstDayTVArr[WeekConstants.EVENING_TEMP] = view.findViewById(R.id.firstDayEvTempText);

        //второй день
        secondDayTVArr = new TextView[4];
        secondDayTVArr[WeekConstants.DAY_DATA] = view.findViewById(R.id.scndDayTimeText);
        secondDayTVArr[WeekConstants.MORNING_TEMP] = view.findViewById(R.id.scndDayMorningTempText);
        secondDayTVArr[WeekConstants.AFTERNOON_TEMP] = view.findViewById(R.id.scndDayAftTempText);
        secondDayTVArr[WeekConstants.EVENING_TEMP] = view.findViewById(R.id.scndDayEvTempText);

        //третий день
        thirdDayTVArr = new TextView[4];
        thirdDayTVArr[WeekConstants.DAY_DATA] = view.findViewById(R.id.trdDayTimeText);
        thirdDayTVArr[WeekConstants.MORNING_TEMP] = view.findViewById(R.id.trdDayMorningTempText);
        thirdDayTVArr[WeekConstants.AFTERNOON_TEMP] = view.findViewById(R.id.trdDayAftTempText);
        thirdDayTVArr[WeekConstants.EVENING_TEMP] = view.findViewById(R.id.trdDayEvTempText);
    }

    //поставим данные в передаваемый массив TextView
    private void setDataForTV(TextView [] arrTv, String [] dataForTv){
        if (arrTv.length == dataForTv.length) {
            for (int i = 0; i < arrTv.length; i++) {
                arrTv[i].setText(dataForTv[i]);
            }
        }
        //если длины массивов не равны, значит данные не сходятся
        else {
            Log.d("WeekWeatherFragment", "длины массивов текствью и данных из WeekDataPresenter-a не равны");
        }
    }

    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                   setDataForTV(firstDayTVArr, weekDataP.takeFirstDayDataForTVArr());
                   setDataForTV(secondDayTVArr, weekDataP.takeSecondDayDataForTVArr());
                   setDataForTV(thirdDayTVArr, weekDataP.takeThirdDayDataForTVArr());
//                //УШЕЛ В WeekDataPresenter
//
//                takeAllIndexesForDaysData();
//
//                //УШЕЛ В WeekDataPresenter
//                getStringsArraysWithDaysData();
//                //установим данные для первого дня
//
//                //вот сюда просто вставим текст из вик дата презентера
//                firstDayTimeText.setText(fstDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
//                fstDayMorTempText.setText(fstDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                fstDayAftTempText.setText(fstDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                fstDayEvTempText.setText(fstDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                //для второго дня
//                scndDayTimeText.setText(scndDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
//                sndDayMorTempText.setText(scndDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                sndDayAftTempText.setText(scndDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                sndDayEvTempText.setText(scndDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                //для третьего дня
//                thdDayTimeText.setText(thrdDayMorArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(0, 10));
//                thdDayMorTempText.setText(thrdDayMorArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                thdDayAftTempText.setText(thrdDayAftArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
//                thdDayEvTempText.setText(thrdDayEvArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103"));
            }
        });
    }



//    //получим индексы, по которым будем выбирать из всех выгруженных данных нужные данные по ближайшим дням
//    //Убрать в другое место
//    private void takeAllIndexesForDaysData() {
//        int[] daysBeginningTimesArr = weekDataP.findDayBeginningIndexesArr();
//        fstDayMorKey = daysBeginningTimesArr[0] + 3;
//        fstDayAftKey = daysBeginningTimesArr[0] + 5;
//        fstDayEvKey = daysBeginningTimesArr[0] + 7;
//
//        //индексы для данных второго дня
//        scndDayMorKey = daysBeginningTimesArr[1] + 3;
//        scndDayAftKey = daysBeginningTimesArr[1] + 5;
//        scndDayEvKey = daysBeginningTimesArr[1] + 7;
//
//        //индексы для данных третьего дня
//        thrdDayMorKey = daysBeginningTimesArr[2] + 3;
//        thrdDayAftKey = daysBeginningTimesArr[2] + 5;
//        thrdDayEvKey = daysBeginningTimesArr[2] + 7;
//    }

//    //получим массивы строк, по которым будем искать значения
//    private void getStringsArraysWithDaysData() {
//        //массив строк с данными для первого дня
//        HashMap<Integer, String[]> allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
//        fstDayMorArr = allWeatherDataHashMap.get(fstDayMorKey);
//        fstDayAftArr = allWeatherDataHashMap.get(fstDayAftKey);
//        fstDayEvArr = allWeatherDataHashMap.get(fstDayEvKey);
//        //массив строк с данными для второго дня
//        scndDayMorArr = allWeatherDataHashMap.get(scndDayMorKey);
//        scndDayAftArr = allWeatherDataHashMap.get(scndDayAftKey);
//        scndDayEvArr = allWeatherDataHashMap.get(scndDayEvKey);
//        //массив строк с данными для третьего дня
//        thrdDayMorArr = allWeatherDataHashMap.get(thrdDayMorKey);
//        thrdDayAftArr = allWeatherDataHashMap.get(thrdDayAftKey);
//        thrdDayEvArr = allWeatherDataHashMap.get(thrdDayEvKey);
//    }

    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    @Override
    public void updateViewData() {
        setWeatherValuesToTextViews();
    }

    //так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
    // его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        myData.removeObserver(this);
    }
}
