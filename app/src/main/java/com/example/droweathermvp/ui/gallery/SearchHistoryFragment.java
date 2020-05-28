package com.example.droweathermvp.ui.gallery;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.droweathermvp.R;
import com.example.droweathermvp.interfaces.FragmentMethods;

public class SearchHistoryFragment extends Fragment implements FragmentMethods {
    RecyclerView searchRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private SearchAdapter searchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        initRecycler(view);
        return view;
    }

    //пригодится, если захотим добавить новые вью в этот фрагмент
    @Override
    public void findViews(View view) {

    }

    @Override
    public void postFragment(AppCompatActivity activity, int placeId) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }


    public void initRecycler(View view) {
        searchRecycler = view.findViewById(R.id.searchRecycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        searchRecycler.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        searchRecycler.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        searchAdapter = new SearchAdapter();
        searchRecycler.setAdapter(searchAdapter);
    }

}
