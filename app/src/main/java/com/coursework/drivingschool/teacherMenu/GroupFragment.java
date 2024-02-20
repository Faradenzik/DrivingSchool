package com.coursework.drivingschool.teacherMenu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.RegisterActivity;
import com.coursework.drivingschool.roles.Coursant;
import com.coursework.drivingschool.roles.Teacher;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupFragment extends Fragment {

    public GroupFragment() {
        // Required empty public constructor
    }

    private CoursantAdapter adapter;
    private List<Coursant> coursants = new ArrayList<>();
    private int groupId;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group, container, false);

        setInitialData();

        TextView title = rootView.findViewById(R.id.title);
        title.setText("Группа: " + groupId);

        RecyclerView recyclerView = rootView.findViewById(R.id.coursants_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CoursantAdapter.OnCoursantClickListener coursantClickListener = new CoursantAdapter.OnCoursantClickListener() {
            @Override
            public void onCoursantClick(Coursant coursant, int position) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_coursant_profile);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);

                TextView fio = dialog.findViewById(R.id.coursant_FIO);
                fio.setText(coursant.getSurname() + " " + coursant.getName() + " " + coursant.getPatronomyc());

                ImageView profile_im = dialog.findViewById(R.id.profile_im);
                if(coursant.getUrl_im() != null) {
                    Picasso.get().load(coursant.getUrl_im()).into(profile_im);
                }

                TextView phone = dialog.findViewById(R.id.coursant_phone);
                phone.setText(coursant.getPhone());

                TextView email = dialog.findViewById(R.id.coursant_email);
                email.setText(coursants.get(position).getEmail());

                MaterialButton setGrade = dialog.findViewById(R.id.setGrade);
                setGrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SetGradeActivity.class);
                        String grades = coursants.get(position).getGrades();
                        intent.putExtra("grades", grades);
                        String uid = coursant.getUid();
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                    }
                });

                MaterialButton setLesson = dialog.findViewById(R.id.setLesson);
                setLesson.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SetLessonActivity.class);
                        String Uid = coursants.get(position).getUid();
                        intent.putExtra("Uid", Uid);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        };

        adapter = new CoursantAdapter(coursantClickListener, getContext(), coursants);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void setInitialData() {

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference refC = mDatabase.getReference("Coursants");
        DatabaseReference refT = mDatabase.getReference("Teachers/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        refT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher user = snapshot.getValue(Teacher.class);

                groupId = user.getGroupId();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        refC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coursants.clear();
                for (DataSnapshot groupSnapshot: dataSnapshot.getChildren()) {
                    Coursant coursant = groupSnapshot.getValue(Coursant.class);
                    if (groupId == coursant.getGroupId()) {
                        coursants.add(coursant);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}