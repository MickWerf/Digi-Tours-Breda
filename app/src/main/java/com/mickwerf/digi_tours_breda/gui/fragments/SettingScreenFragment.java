package com.mickwerf.digi_tours_breda.gui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingScreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView howToUseIV;
    private ImageView noGpsIV;

    private TextView howToUseTV;
    private TextView noGpsTV;

    private boolean howToUseOpen;
    private boolean noGpsOpen;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingScreenFragment newInstance(String param1, String param2) {
        SettingScreenFragment fragment = new SettingScreenFragment();
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

    public void showHowToUse(){
        if(!this.howToUseOpen){
            this.howToUseIV.setRotation(90);
            this.howToUseTV.setVisibility(View.VISIBLE);
            this.howToUseOpen = true;
        }else {
            this.howToUseIV.setRotation(0);
            this.howToUseTV.setVisibility(View.GONE);
            this.howToUseOpen = false;
        }
    }

    public void showNoGpsCase(){
        if(!this.noGpsOpen){
            this.noGpsIV.setRotation(90);
            this.noGpsTV.setVisibility(View.VISIBLE);
            this.noGpsOpen = true;
        }else {
            this.noGpsIV.setRotation(0);
            this.noGpsTV.setVisibility(View.GONE);
            this.noGpsOpen = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.howToUseIV = this.getView().findViewById(R.id.howToUseIV);
        this.noGpsIV = this.getView().findViewById(R.id.noGpsIV);

        this.noGpsOpen = false;
        this.howToUseOpen = false;

        this.howToUseTV = this.getView().findViewById(R.id.HowToUseTV);
        this.noGpsTV = this.getView().findViewById(R.id.noGpsTV);

        this.howToUseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToUse();
            }
        });

        this.noGpsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoGpsCase();
            }
        });
    }
}