package com.coursework.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coursework.drivingschool.roles.Teacher;
import com.coursework.drivingschool.teacherMenu.MenuTeacherActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button auth_btn;
    private EditText userEmail;
    private EditText userPassword;
    private FirebaseAuth auth;
    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        auth_btn = findViewById(R.id.auth_btn);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        DatabaseReference data = mDatabase.getReference("Teachers");
        List<String> ids = new ArrayList<>();

        auth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userEmail.getText().toString().trim().equals("") || userPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, R.string.empty_fields_err, Toast.LENGTH_LONG).show();
                } else {
                    auth.signInWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    setResult(1);

                                    data.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                                                String id = productSnapshot.getValue(Teacher.class).getUid();
                                                ids.add(id);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });

                                    Intent intent;

                                    if (Arrays.asList(ids).contains(auth.getCurrentUser().getUid())) {
                                        intent = new Intent(LoginActivity.this, MenuTeacherActivity.class);
                                    } else {
                                        intent = new Intent(LoginActivity.this, MainActivity.class);
                                    }

                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}