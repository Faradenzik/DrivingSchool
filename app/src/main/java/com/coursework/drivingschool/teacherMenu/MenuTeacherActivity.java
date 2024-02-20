package com.coursework.drivingschool.teacherMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.coursework.drivingschool.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuTeacherActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final ProfileTeacherFragment profileFragment = new ProfileTeacherFragment();
    private final GroupFragment groupFragment = new GroupFragment();
    private final TimetableTeacherFragment timetableFragment = new TimetableTeacherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.teachFragment, profileFragment).commit();
                return true;

            case R.id.group:
                getSupportFragmentManager().beginTransaction().replace(R.id.teachFragment, groupFragment).commit();
                return true;

            case R.id.timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.teachFragment, timetableFragment).commit();
                return true;
        }

        return false;
    }
}