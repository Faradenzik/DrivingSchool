package com.coursework.drivingschool.unsignedMenu.teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.roles.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeachersList extends AppCompatActivity {

    private List<Teacher> teachers = new ArrayList<>();
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        setInitialData();

        RecyclerView recyclerView = findViewById(R.id.teach_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TeacherAdapter(this, teachers);
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Teachers");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot groupSnapshot: dataSnapshot.getChildren()) {
                    Teacher teacher = groupSnapshot.getValue(Teacher.class);
                    teachers.add(teacher);

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

}