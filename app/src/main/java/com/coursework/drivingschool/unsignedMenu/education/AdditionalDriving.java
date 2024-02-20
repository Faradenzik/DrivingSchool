package com.coursework.drivingschool.unsignedMenu.education;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursework.drivingschool.R;


public class AdditionalDriving extends Fragment {

    public AdditionalDriving() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_additional_driving, container, false);
    }
}