package com.coursework.drivingschool.teacherMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.drivingschool.R;
import com.coursework.drivingschool.roles.Coursant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CoursantAdapter extends RecyclerView.Adapter<CoursantAdapter.CoursantsViewHolder> {

    interface OnCoursantClickListener{
        void onCoursantClick(Coursant coursant, int position);
    }

    private List<Coursant> coursants;
    private final LayoutInflater inflater;
    private final CoursantAdapter.OnCoursantClickListener onClickListener;

    public CoursantAdapter(OnCoursantClickListener onClickListener, Context context, List<Coursant> coursants) {
        this.coursants = coursants;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CoursantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.coursant_item, parent, false);
        return new CoursantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursantsViewHolder holder, int position) {
        Coursant coursant = coursants.get(position);

        holder.coursant_FIO.setText(coursant.getSurname() + " " + coursant.getName().charAt(0) + "." + coursant.getPatronomyc().charAt(0) + ".");
        if(coursant.getUrl_im() != null) {
            Picasso.get().load(coursant.getUrl_im()).into(holder.coursant_im);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onCoursantClick(coursant, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursants.size();
    }

    public static class CoursantsViewHolder extends RecyclerView.ViewHolder {
        ImageView coursant_im;
        TextView coursant_FIO;

        public CoursantsViewHolder(View view) {
            super(view);
            coursant_im = view.findViewById(R.id.coursant_im);
            coursant_FIO = view.findViewById(R.id.coursant_FIO);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
