package com.coursework.drivingschool.teacherMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.objects.DrivingGroup;
import com.coursework.drivingschool.objects.Lesson;
import com.coursework.drivingschool.roles.Coursant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetLessonActivity extends AppCompatActivity {

    private MaterialButton dateSet;
    private MaterialButton timeSet;

    private List<Lesson> lessons = new ArrayList<>();

    FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference data = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Lessons");

    Calendar dateAndTime = Calendar.getInstance();
    int day, month;
    private MaterialButton add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_lesson);

        dateSet = findViewById(R.id.dateSet);
        timeSet = findViewById(R.id.timeSet);
        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dateSet.getText().toString().equals("Дата") || timeSet.getText().toString().equals("Время")) {
                    Toast.makeText(SetLessonActivity.this, "Настройте все поля!", Toast.LENGTH_SHORT).show();
                } else {
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            lessons.clear();
                            for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                                Lesson lesson = groupSnapshot.getValue(Lesson.class);
                                lessons.add(lesson);
                            }

                            String coursantId = getIntent().getExtras().getString("Uid");
                            String teacherId = auth.getCurrentUser().getUid();

                            Lesson lesson = new Lesson(dateSet.getText().toString(), timeSet.getText().toString(), teacherId, coursantId);
                            lessons.add(lesson);

                            data.setValue(lessons);

                            Toast.makeText(SetLessonActivity.this, "Занятие добавлено", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(SetLessonActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(SetLessonActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDate() {
        dateSet.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE));
        day = dateAndTime.get(Calendar.DAY_OF_MONTH);
        month = dateAndTime.get(Calendar.MONTH);
    }

    private void setInitialTime() {
        timeSet.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

}