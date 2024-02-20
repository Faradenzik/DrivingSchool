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

public class CategoryA extends Fragment {


    public CategoryA() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View categoryA = inflater.inflate(R.layout.fragment_category_a, container, false);

        MaterialButton zapis = categoryA.findViewById(R.id.zapis_btn);
        zapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("category", "A");
                intent.putExtra("type", "Вечерняя");
                startActivity(intent);
            }
        });

        return categoryA;
    }
}