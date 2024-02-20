package com.coursework.drivingschool.coursantMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursework.drivingschool.objects.Lesson;
import com.coursework.drivingschool.R;
import com.coursework.drivingschool.roles.Coursant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TimetableCoursantFragment extends Fragment {

    public TimetableCoursantFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private List<Lesson> lessons = new ArrayList<>();
    private LessonCoursantAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        setInitialData();

        RecyclerView recyclerView = rootView.findViewById(R.id.timetable_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new LessonCoursantAdapter(getContext(), lessons);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void setInitialData() {
        lessons.clear();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference refL = mDatabase.getReference("Lessons");

        refL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lessons.clear();
                for (DataSnapshot lessonSnapshot: dataSnapshot.getChildren()) {
                    Lesson lesson = lessonSnapshot.getValue(Lesson.class);

                    if(lesson.getCoursantId().equals(auth.getCurrentUser().getUid())) {
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
    }
}