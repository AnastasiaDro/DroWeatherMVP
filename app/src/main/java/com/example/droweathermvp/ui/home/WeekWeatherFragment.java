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
import com.example.droweathermvp.model.MyData;
//константы со значениями для массивов данных (время, утро, день, вечер)
import static com.example.droweathermvp.ui.home.WeekConstants.*;

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
    private MyData myData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO придумать как убрать нахрен отсюда модель

        myData = MyData.getInstance();
        myData.registerObserver(this);
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
        firstDayTVArr[DAY_DATA] = view.findViewById(R.id.firstDayTimeText);
        firstDayTVArr[MORNING_TEMP] = view.findViewById(R.id.firstDayMorningTempText);
        firstDayTVArr[AFTERNOON_TEMP] = view.findViewById(R.id.firstDayAftTempText);
        firstDayTVArr[EVENING_TEMP] = view.findViewById(R.id.firstDayEvTempText);

        //второй день
        secondDayTVArr = new TextView[4];
        secondDayTVArr[DAY_DATA] = view.findViewById(R.id.scndDayTimeText);
        secondDayTVArr[MORNING_TEMP] = view.findViewById(R.id.scndDayMorningTempText);
        secondDayTVArr[AFTERNOON_TEMP] = view.findViewById(R.id.scndDayAftTempText);
        secondDayTVArr[EVENING_TEMP] = view.findViewById(R.id.scndDayEvTempText);

        //третий день
        thirdDayTVArr = new TextView[4];
        thirdDayTVArr[DAY_DATA] = view.findViewById(R.id.trdDayTimeText);
        thirdDayTVArr[MORNING_TEMP] = view.findViewById(R.id.trdDayMorningTempText);
        thirdDayTVArr[AFTERNOON_TEMP] = view.findViewById(R.id.trdDayAftTempText);
        thirdDayTVArr[EVENING_TEMP] = view.findViewById(R.id.trdDayEvTempText);
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

    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    //TODO нужно, чтобы данные обновлялись не тут, а сперва в WeekDataPresenter
    //Пока сделала отдельный метод для забора данных из модели... ух
    @Override
    public void updateViewData() {
    //должны идти по порядку, а то сломается... =(
        weekDataP.getDataFromModel();
        setWeatherValuesToTextViews();
    }

    public void setWeatherValuesToTextViews() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                setDataForTV(firstDayTVArr, weekDataP.takeFirstDayDataForTVArr());
                setDataForTV(secondDayTVArr, weekDataP.takeSecondDayDataForTVArr());
                setDataForTV(thirdDayTVArr, weekDataP.takeThirdDayDataForTVArr());
            }
        });
    }

    //так как при каждом запуске мы добавляем фрагмент в список обсёрверов, то при закрытии/перерисовке нужно
    // его из этого списка удалить
    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // myData.removeObserver(this);
    }
}
