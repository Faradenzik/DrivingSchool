package com.coursework.drivingschool.coursantMenu;
import static androidx.constraintlayout.widget.ConstraintSet.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class GradebookFragment extends Fragment {

    public GradebookFragment() {
        // Required empty public constructor
    }

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference data = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Coursants/" + auth.getCurrentUser().getUid());

    private TextView grade1;
    private TextView grade2;
    private TextView grade3;
    private TextView grade4;
    private TextView grade5;
    private TextView grade6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gradeView = inflater.inflate(R.layout.fragment_gradebook, container, false);

        grade1 = gradeView.findViewById(R.id.grade1);
        grade2 = gradeView.findViewById(R.id.grade2);
        grade3 = gradeView.findViewById(R.id.grade3);
        grade4 = gradeView.findViewById(R.id.grade4);
        grade5 = gradeView.findViewById(R.id.grade5);
        grade6 = gradeView.findViewById(R.id.grade6);

        List<TextView> grades = new ArrayList<>();
        grades.add(grade1);
        grades.add(grade2);
        grades.add(grade3);
        grades.add(grade4);
        grades.add(grade5);
        grades.add(grade6);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String grades_stat = snapshot.getValue(Coursant.class).getGrades();

                for (int i = 0; i < 6; i++) {
                    if (grades_stat.charAt(i) == '1') {
                        grades.get(i).setVisibility(View.VISIBLE);
                    } else {
                        grades.get(i).setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return gradeView;
    }
}