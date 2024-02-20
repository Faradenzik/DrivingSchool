package com.coursework.drivingschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.coursework.drivingschool.objects.DrivingGroup;
import com.coursework.drivingschool.roles.Coursant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth reg = FirebaseAuth.getInstance();;
    private DatabaseReference data;
    private DatabaseReference refG;
    int group;

    private EditText personSurname;
    private EditText personName;
    private EditText personPatronomyc;
    private EditText personBirthday;
    private EditText personPhone;
    private EditText personEmail;
    private EditText personPassword;

    private final Calendar dateAndTime = Calendar.getInstance();

    public void setDate(View v) {
        new DatePickerDialog(RegisterActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        personBirthday.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app");
        data = mDatabase.getReference();

        TextView personCategory = findViewById(R.id.personCategory);
        personSurname = findViewById(R.id.personSurname);
        personName = findViewById(R.id.personName);
        personPatronomyc = findViewById(R.id.personPatronomic);
        personPhone = findViewById(R.id.personPhone);
        personBirthday = findViewById(R.id.personBirthday);
        personBirthday.setKeyListener(null);
        personEmail = findViewById(R.id.personEmail);
        personPassword = findViewById(R.id.personPassword);

        TextView personGroup = findViewById(R.id.personGroup);
        group = getIntent().getExtras().getInt("groupId", -1);
        if(group != -1) {
            personGroup.setText("Группа: " + group);
        }

        String category = getIntent().getExtras().getString("category", " ");
        String type = getIntent().getExtras().getString("type", "Вечерняя");

        personCategory.setText(category + " " + type);


        if(group == -1) {
            List<DrivingGroup> groups = new ArrayList<>();
            refG = mDatabase.getReference("Groups");
            refG.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    groups.clear();
                    for (DataSnapshot groupSnapshot : snapshot.getChildren()) {
                        DrivingGroup groupf = groupSnapshot.getValue(DrivingGroup.class);

                        if (groupf.getType().equals(type) && groupf.getCategory().equals(category)) {
                            groups.add(groupf);
                        }
                    }

                    if (groups.size() == 0) {
                        Toast.makeText(RegisterActivity.this, "На данный момент набор не идет", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        int rnd = new Random().nextInt(groups.size());
                        group = groups.get(rnd).getId();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Button reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personEmail.getText().toString().trim().equals("") || personPassword.getText().toString().trim().equals("") || personName.getText().toString().trim().equals("")
                || personSurname.getText().toString().trim().equals("") || personPatronomyc.getText().toString().trim().equals("") || personBirthday.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.empty_fields_err, Toast.LENGTH_LONG).show();
                }
                else {
                    reg.createUserWithEmailAndPassword(personEmail.getText().toString().trim(), personPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        setResult(1);
                                        Coursant cadet = new Coursant(reg.getCurrentUser().getUid(), personSurname.getText().toString().trim(), personName.getText().toString().trim(),
                                                personPatronomyc.getText().toString(), personPhone.getText().toString(), personBirthday.getText().toString(),
                                                personEmail.getText().toString(), "000000", group);
                                        String personUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        data.child("Coursants").child(personUid).setValue(cadet);
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), R.string.reg_err, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}