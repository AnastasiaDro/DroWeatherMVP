package com.example.droweathermvp.ui.home;

import android.os.Bundle;
import android.os.Handler;
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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

public class DayWeatherFragment extends Fragment implements FragmentMethods, Observer {
    private static final int DAY_FIRST_DATA_KEY_IN_HASHMAP = 1;
    private static final int DAY_SECOND_DATA_KEY_IN_HASHMAP = 2;
    private static final int DAY_THIRD_DATA_KEY_IN_HASHMAP = 3;

    //используемые View
    private TextView fSoonTimeText;
    private TextView sSoonTimeText;
    private TextView thSoonTimeText;
    private TextView fSoonTempText;
    private TextView sSoonTempText;
    private TextView thSoonTempText;
    private TextView fDescriptText;
    private TextView sDescriptText;
    private TextView thDescriptText;
    private SimpleDraweeView fSoonDraweeView;
    private SimpleDraweeView sSoonDraweeView;
    private SimpleDraweeView thSoonDraweeView;


//    String[] firstDataArr;
//    String[] secondDataArr;
//    String[] thirdDataArr;

    private String temp;
    private String descriptString;
    private String iconString;

//    private MyData myData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //получаем аргументы назад
        //... место для аргументов
//        myData = MyData.getInstance();
//        myData.registerObserver(this);
    }

    //создаем View
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_weather, container, false);
        findViews(view);
        //updateViewData();
        return view;
    }

    @Override
    public void findViews(View view) {
        fSoonTimeText = view.findViewById(R.id.f_soonTextView);
        sSoonTimeText = view.findViewById(R.id.s_soonTextView);
        thSoonTimeText = view.findViewById(R.id.th_soonTextView);
        fSoonTempText = view.findViewById(R.id.f_soonTempText);
        sSoonTempText = view.findViewById(R.id.s_soonTempText);
        thSoonTempText = view.findViewById(R.id.th_soonTempText);
        fDescriptText = view.findViewById(R.id.fDescriptText);
        sDescriptText = view.findViewById(R.id.sDescriptText);
        thDescriptText = view.findViewById(R.id.thDescriptText);
        fSoonDraweeView = view.findViewById(R.id.fSoonWeathImg);
        sSoonDraweeView = view.findViewById(R.id.sSoonWeathImg);
        thSoonDraweeView = view.findViewById(R.id.thSoonWeathImg);
    }

    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }

    @Override
    public void updateViewData() {
   //     setWeatherValuesToTextViews();
    }

    //Ставить текст
//    public void setWeatherValuesToTextViews() {
//        final Handler handler = new Handler();
//        // do{} while (myData.getWeatherRequestIsDone()==false);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                HashMap<Integer, String[]> curHashMap = myData.getAllWeatherDataHashMap();
//                try {
//                    //Через три часа
//                    firstDataArr = curHashMap.get(DAY_FIRST_DATA_KEY_IN_HASHMAP);
//                    fSoonTimeText.setText(firstDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = firstDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    fSoonTempText.setText(temp);
//                    descriptString = firstDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    fDescriptText.setText(descriptString);
//                    iconString = firstDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(fSoonDraweeView, firstDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                    //Через 6 часов
//                    secondDataArr = curHashMap.get(DAY_SECOND_DATA_KEY_IN_HASHMAP);
//                    sSoonTimeText.setText(secondDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = secondDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    sSoonTempText.setText(temp);
//                    descriptString = secondDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    sDescriptText.setText(descriptString);
//                    iconString = secondDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(sSoonDraweeView, secondDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                    //Через 9 часов
//                    thirdDataArr = curHashMap.get(DAY_THIRD_DATA_KEY_IN_HASHMAP);
//                    thSoonTimeText.setText(thirdDataArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY].substring(10, 16));
//                    temp = thirdDataArr[Constants.TEMP_KEY_IN_WEATHERDATA_ARRAY].concat(" \u2103");
//                    thSoonTempText.setText(temp);
//                    descriptString = thirdDataArr[Constants.DESCRIPT_KEY_IN_WEATHERDATA_ARRAY];
//                    thDescriptText.setText(descriptString);
//                    iconString = thirdDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY];
//                    myData.getImageLoader().loadDraweeImage(thSoonDraweeView, thirdDataArr[Constants.ICON_ID_KEY_IN_WEATHERDATA_ARRAY]);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // myData.removeObserver(this);
    }
}
