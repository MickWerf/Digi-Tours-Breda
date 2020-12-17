package com.mickwerf.digi_tours_breda.gui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.Database;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.gui.RouteItemAdapter;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RouteOverviewFragment extends Fragment {
    private static final String TAG = RouteOverviewFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RouteItemAdapter adapter;
    private MainViewModel mainViewModel;
    private List<Route> routes;

    public RouteOverviewFragment(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_overview, container, false);

        Runnable runnable = () -> {
            this.routes = this.mainViewModel.getRoutes().getValue();
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(this.routes == null){
            this.routes = new ArrayList<>();
        }

        this.recyclerView = view.findViewById(R.id.routeRecyclerView);
        this.adapter = new RouteItemAdapter(routes);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.recyclerView.setAdapter(adapter);


        TextView title = view.findViewById(R.id.title);
        title.setText(timeOfDay());

        return view;
    }

    public String timeOfDay(){
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            return this.getString(R.string.route_fragment_title1);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            return this.getString(R.string.route_fragment_title2);
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            return this.getString(R.string.route_fragment_title3);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            return this.getString(R.string.route_fragment_title4);
        }
        return "";
    }



}