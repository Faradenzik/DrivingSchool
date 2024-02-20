package com.coursework.drivingschool.teacherMenu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.objects.Lesson;
import com.coursework.drivingschool.roles.Coursant;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimetableTeacherFragment extends Fragment {

    public TimetableTeacherFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private List<Lesson> lessons = new ArrayList<>();
    private List<Coursant> coursants = new ArrayList<>();
    private LessonTeacherAdapter adapter;
    private Dialog dialog;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference refL = mDatabase.getReference("Lessons");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View timeTView = inflater.inflate(R.layout.fragment_timetable_teacher, container, false);

        setInitialData();

        RecyclerView rc = timeTView.findViewById(R.id.lessonsList);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));

        LessonTeacherAdapter.OnLessonClickListener lessonClickListener = new LessonTeacherAdapter.OnLessonClickListener() {
            @Override
            public void onLessonClick(Lesson lesson, int position) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_delete_lesson);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);

                MaterialButton delete = dialog.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lessons.remove(position);
                        refL.setValue(lessons);
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        };

        adapter = new LessonTeacherAdapter(lessonClickListener, getContext(), lessons);
        rc.setAdapter(adapter);

        return timeTView;
    }

    private void setInitialData() {
        DatabaseReference refC = mDatabase.getReference("Coursants");

        refL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lessons.clear();
                for (DataSnapshot lessonSnapshot: dataSnapshot.getChildren()) {
                    Lesson lesson = lessonSnapshot.getValue(Lesson.class);

                    if(lesson.getTeacherId().equals(auth.getCurrentUser().getUid())) {
                        lessons.add(lesson);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        refC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coursants.clear();
                for (DataSnapshot coursantSnapshot: dataSnapshot.getChildren()) {
                    Coursant coursant = coursantSnapshot.getValue(Coursant.class);

                    coursants.add(coursant);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}