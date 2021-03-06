package com.mickwerf.digi_tours_breda.gui.fragments;

import android.content.Context;
import android.content.res.Resources;
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

    private Context context;
    private String language;

    Observer<List<Route>> routesUpdateObserver = new Observer<List<Route>>() {
        @Override
        public void onChanged(List<Route> newRoutes) {
            routes = newRoutes;
        }
    };

    public RouteOverviewFragment(MainViewModel mainViewModel, Context context){
        this.mainViewModel = mainViewModel;
        this.context = context;
        this.language = mainViewModel.getUserSettings2().getLanguage();
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_overview, container, false);


        this.mainViewModel.getRoutes().observe(this, this.routesUpdateObserver);
        this.routes = this.mainViewModel.getRoutes2();

        recyclerView = view.findViewById(R.id.routeRecyclerView);
        adapter = new RouteItemAdapter(routes,this.context,this.mainViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);


        TextView title = view.findViewById(R.id.title);
        title.setText(timeOfDay());

        return view;
    }

    public String timeOfDay(){
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            return this.getString(R.string.route_fragment_title1);
        }else if(timeOfDay >= 12 && timeOfDay < 18){
            return this.getString(R.string.route_fragment_title2);
        }else if(timeOfDay >= 18 && timeOfDay < 21){
            return this.getString(R.string.route_fragment_title3);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            return this.getString(R.string.route_fragment_title4);
        }
        return "";
    }



}