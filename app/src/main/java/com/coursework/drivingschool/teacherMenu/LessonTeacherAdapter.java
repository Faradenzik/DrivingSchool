package com.coursework.drivingschool.teacherMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.objects.Lesson;

import java.util.List;

public class LessonTeacherAdapter extends RecyclerView.Adapter<LessonTeacherAdapter.LessonViewHolder>{

    interface OnLessonClickListener{
        void onLessonClick(Lesson lesson, int position);
    }

    private final OnLessonClickListener onClickListener;
    private final Context context;
    private List<Lesson> lessons;

    public LessonTeacherAdapter(OnLessonClickListener onClickListener, Context context, List<Lesson> lessons) {
        this.onClickListener = onClickListener;
        this.lessons = lessons;
        this.context = context;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);

        holder.timeView.setText(lesson.getTime());
        holder.dateView.setText(lesson.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onLessonClick(lesson, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView timeView;
        TextView dateView;

        public LessonViewHolder(View view) {
            super(view);
            timeView = view.findViewById(R.id.time);
            dateView = view.findViewById(R.id.date);
        }
    }
}
