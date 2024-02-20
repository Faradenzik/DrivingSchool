package com.coursework.drivingschool.unsignedMenu.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.objects.DrivingGroup;
import com.coursework.drivingschool.roles.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    public interface bip {
        public void execute(String FIO);
    }

    interface OnGroupClickListener{
        void onGroupClick(DrivingGroup group, int position);
    }

    private final OnGroupClickListener onClickListener;
    private final LayoutInflater inflater;
    private List<DrivingGroup> groups;
    String url;

    public GroupAdapter(OnGroupClickListener onClickListener, Context context, List<DrivingGroup> groups) {
        this.onClickListener = onClickListener;
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }

    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        DrivingGroup group = groups.get(position);

        if(group.getCategory().equals("B")) {
            holder.category.setImageResource(R.drawable.car);
        } else {
            holder.category.setImageResource(R.drawable.bike);
        }
        holder.month.setText(group.getMonth());
        holder.type.setText(group.getType());

        bip fik = FIO -> { holder.teacherFIO.setText(FIO); };

        teachFIO(group.getTeacherId(), fik);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onGroupClick(group, position);
            }
        });
    }

    public void teachFIO (String id, bip handler) {

        DatabaseReference ref = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Teachers/" + id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher user = snapshot.getValue(Teacher.class);
                if(user != null) {
                    handler.execute("." + user.getSurname() + " " + user.getName().charAt(0) + ". " + user.getPatronomyc().charAt(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public int getItemCount() {
        return groups.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView category;
        ImageView teacher_im;
        TextView month;
        TextView type;
        TextView teacherFIO;

        public GroupViewHolder(View view) {
            super(view);
            category = view.findViewById(R.id.category_im);
            month = view.findViewById(R.id.month);
            type = view.findViewById(R.id.type);
            teacherFIO = view.findViewById(R.id.teacherFIO);
            teacher_im = view.findViewById(R.id.teacher_im);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}