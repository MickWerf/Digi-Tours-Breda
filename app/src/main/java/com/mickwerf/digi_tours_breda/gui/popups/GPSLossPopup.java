package com.mickwerf.digi_tours_breda.gui.popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mickwerf.digi_tours_breda.R;

public class GPSLossPopup extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.no_gps_popup, container, false);
        return view;
    }
}