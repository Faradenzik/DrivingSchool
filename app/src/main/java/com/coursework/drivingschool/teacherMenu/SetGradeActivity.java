package com.coursework.drivingschool.teacherMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.coursework.drivingschool.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetGradeActivity extends AppCompatActivity {

    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference data;
    private String grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_grade);

        CheckBox check1 = findViewById(R.id.check1);
        CheckBox check2 = findViewById(R.id.check2);
        CheckBox check3 = findViewById(R.id.check3);
        CheckBox check4 = findViewById(R.id.check4);
        CheckBox check5 = findViewById(R.id.check5);
        CheckBox check6 = findViewById(R.id.check6);

        List<CheckBox> checks = new ArrayList<>();
        checks.add(check1);
        checks.add(check2);
        checks.add(check3);
        checks.add(check4);
        checks.add(check5);
        checks.add(check6);

        grades = getIntent().getExtras().getString("grades", "000000");
        String uid = getIntent().getExtras().getString("uid", "");
        data = mDatabase.getReference("Coursants/" + uid);

        for (int i = 0; i < 6; i++) {
            if (grades.charAt(i) == '1') {
                checks.get(i).setChecked(true);
            }
        }

        MaterialButton save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] grades1 = grades.toCharArray();
                for (int i = 0; i < 6; i++) {
                    if(checks.get(i).isChecked()) {
                        grades1[i] = '1';
                    } else {
                        grades1[i] = '0';
                    }
                }

                grades = new String(grades1);
                data.child("grades").setValue(grades);
                Toast.makeText(SetGradeActivity.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
            }
        });

    }
}