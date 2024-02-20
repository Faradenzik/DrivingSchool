package com.coursework.drivingschool.unsignedMenu;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.coursework.drivingschool.LoginActivity;
import com.coursework.drivingschool.R;
import com.coursework.drivingschool.unsignedMenu.education.EducationActivity;
import com.coursework.drivingschool.unsignedMenu.groups.GroupsList;
import com.coursework.drivingschool.unsignedMenu.prices.PricesActivity;
import com.coursework.drivingschool.unsignedMenu.teachers.TeachersList;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MaterialButton educ_btn = findViewById(R.id.education_btn);
        MaterialButton prices_btn = findViewById(R.id.prices_btn);
        MaterialButton groups_btn = findViewById(R.id.groups_btn);
        MaterialButton teachers_btn = findViewById(R.id.teachers_btn);
        TextView yesAcc = findViewById(R.id.yesAcc);

        yesAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        educ_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EducationActivity.class);
                startActivity(intent);
            }
        });

        prices_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PricesActivity.class);
                startActivity(intent);
            }
        });

        groups_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GroupsList.class);
                startActivity(intent);
            }
        });

        teachers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TeachersList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            finish();
        }
    }

}