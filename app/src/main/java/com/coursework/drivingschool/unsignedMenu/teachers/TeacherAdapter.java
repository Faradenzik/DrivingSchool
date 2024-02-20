package com.coursework.drivingschool.unsignedMenu.teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.roles.Teacher;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>{

    private final LayoutInflater inflater;
    private List<Teacher> teachers;

    public TeacherAdapter(Context context, List<Teacher> teachers) {
        this.teachers = teachers;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.teacher_item, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);

        holder.surname.setText(teacher.getSurname());
        holder.name.setText(teacher.getName());
        holder.patronomyc.setText(teacher.getPatronomyc());
        holder.phone.setText(teacher.getPhone());

        if(teacher.getUrl_im() != null) {
            Picasso.get().load(teacher.getUrl_im()).into(holder.profile_im);
        }
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_im;
        TextView surname;
        TextView name;
        TextView patronomyc;
        TextView phone;

        public TeacherViewHolder(View view) {
            super(view);
            profile_im = view.findViewById(R.id.profile_im);
            surname = view.findViewById(R.id.surname_text);
            name = view.findViewById(R.id.name_text);
            patronomyc = view.findViewById(R.id.patronomyc_text);
            phone = view.findViewById(R.id.phone);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
