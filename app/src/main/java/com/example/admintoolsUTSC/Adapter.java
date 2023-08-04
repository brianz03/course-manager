package com.example.admintoolsUTSC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.b07tut7grp3.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final itemclick itemClick;
    Context context;
    ArrayList<Course_admin> list;

    public  Adapter(Context context, ArrayList<Course_admin> list, itemclick itemClick){
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course,parent,false);
        return new ViewHolder(v, itemClick);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course_admin course = list.get(position);
        holder.courseName.setText(course.getCourseName());
        holder.Subject.setText(course.getSubject());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView courseName, Subject;

        public ViewHolder(@NonNull View courseView, itemclick itemClick){
            super(courseView);
            courseName = courseView.findViewById((R.id.tvcourseName));
            Subject = courseView.findViewById((R.id.tvsubjectName));
            courseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClick != null){
                        int pos = getAdapterPosition();
                        itemClick.onItemClick(pos);

                    }
                }
            });




        }

    }
}
