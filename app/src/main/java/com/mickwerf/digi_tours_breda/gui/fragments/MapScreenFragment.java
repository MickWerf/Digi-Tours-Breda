package com.mickwerf.digi_tours_breda.gui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.NextLocationAdapter;
import com.mickwerf.digi_tours_breda.gui.NextLocationItem;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final LinkedList<NextLocationItem> mLocationList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NextLocationAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapScreenFragment newInstance(String param1, String param2) {
        MapScreenFragment fragment = new MapScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLocationList.add(new NextLocationItem("test1"));
        mLocationList.add(new NextLocationItem("test2"));
        mLocationList.add(new NextLocationItem("test3"));
        mLocationList.add(new NextLocationItem("test4"));
        mLocationList.add(new NextLocationItem("test5"));
        mLocationList.add(new NextLocationItem("test6"));
        mLocationList.add(new NextLocationItem("test7"));
        //TODO: deletetest code ^

        // Create recycler view.
        mRecyclerView = getView().findViewById(R.id.NextLocationRecyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new NextLocationAdapter(getContext(), mLocationList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}