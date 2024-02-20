package com.coursework.drivingschool;

import static com.google.common.collect.Iterables.contains;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.coursework.drivingschool.coursantMenu.MenuCoursantActivity;
import com.coursework.drivingschool.roles.Teacher;
import com.coursework.drivingschool.teacherMenu.MenuTeacherActivity;
import com.coursework.drivingschool.unsignedMenu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private List<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Teachers");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                        Teacher teacher = groupSnapshot.getValue(Teacher.class);
                        ids.add(teacher.getUid());
                    }

                    Intent intent;
                    if (contains(ids, auth.getCurrentUser().getUid())) {
                        intent = new Intent(MainActivity.this, MenuTeacherActivity.class);
                    } else {
                        intent = new Intent(MainActivity.this, MenuCoursantActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    throw databaseError.toException();
                }
            });
        } else {
            Intent intent;
            intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}