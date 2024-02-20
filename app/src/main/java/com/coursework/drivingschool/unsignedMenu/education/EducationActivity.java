package com.coursework.drivingschool.unsignedMenu.education;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.coursework.drivingschool.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EducationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView educationNavigationView;
    CategoryB categoryB = new CategoryB();
    CategoryA categoryA = new CategoryA();
    Vip vip = new Vip();
    AdditionalDriving addDrive = new AdditionalDriving();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        educationNavigationView = findViewById(R.id.educationNavigationView);

        educationNavigationView.setOnNavigationItemSelectedListener(this);
        educationNavigationView.setSelectedItemId(R.id.categoryB);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categoryB:
                getSupportFragmentManager().beginTransaction().replace(R.id.edFragment, categoryB).commit();
                return true;

            case R.id.categoryA:
                getSupportFragmentManager().beginTransaction().replace(R.id.edFragment, categoryA).commit();
                return true;

            case R.id.vipEduc:
                getSupportFragmentManager().beginTransaction().replace(R.id.edFragment, vip).commit();
                return true;

            case R.id.addDriving:
                getSupportFragmentManager().beginTransaction().replace(R.id.edFragment, addDrive).commit();
                return true;
        }
        return false;
    }
}