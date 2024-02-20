package com.coursework.drivingschool.unsignedMenu.education;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.RegisterActivity;
import com.google.android.material.button.MaterialButton;

public class Vip extends Fragment {

    public Vip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vip = inflater.inflate(R.layout.fragment_vip, container, false);

        MaterialButton zapis = vip.findViewById(R.id.zapis_btn);
        zapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("category", "V");
                startActivity(intent);
            }
        });

        return vip;
    }
}