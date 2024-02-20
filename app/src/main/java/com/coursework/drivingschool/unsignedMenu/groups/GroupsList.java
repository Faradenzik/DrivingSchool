package com.coursework.drivingschool.unsignedMenu.groups;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.coursework.drivingschool.R;
import com.coursework.drivingschool.RegisterActivity;
import com.coursework.drivingschool.objects.DrivingGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class GroupsList extends AppCompatActivity {

    private List<DrivingGroup> groups = new ArrayList<>();
    private GroupAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        setInitialData();

        RecyclerView recyclerView = findViewById(R.id.groups_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GroupAdapter.OnGroupClickListener groupClickListener = new GroupAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(DrivingGroup group, int position) {
                id = position;
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                FragmentManager manager = getSupportFragmentManager();
                myDialogFragment.show(manager, "dialog");
            }
        };

        adapter = new GroupAdapter(groupClickListener, this, groups);
        recyclerView.setAdapter(adapter);
    }

    public void catClick(View view) {
        Toast.makeText(GroupsList.this, "Категория", Toast.LENGTH_SHORT).show();
    }

    public void positiveClick() {
        Intent intent = new Intent(GroupsList.this, RegisterActivity.class);
        intent.putExtra("groupId", id);
        String category = groups.get(id).getCategory();
        String type = groups.get(id).getType();
        intent.putExtra("category", category);
        intent.putExtra("type", type);

        startActivityForResult(intent, 1);
    }

    private void setInitialData() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Groups");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot groupSnapshot: dataSnapshot.getChildren()) {
                    DrivingGroup group = groupSnapshot.getValue(DrivingGroup.class);

                    groups.add(group);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            finish();
        }
    }
}