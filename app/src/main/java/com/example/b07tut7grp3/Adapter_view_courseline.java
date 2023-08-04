package com.example.b07tut7grp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admintoolsUTSC.Course_admin;

import java.util.ArrayList;

public class Adapter_view_courseline extends RecyclerView.Adapter<Adapter_view_courseline.courseline_ViewHolder> {

    Context context;
    ArrayList<courseline> list;

    public  Adapter_view_courseline(Context context, ArrayList<courseline> list){
        this.context = context;
        this.list = list;

    }



    @NonNull
    @Override
    public Adapter_view_courseline.courseline_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.courseline_item,parent,false);
        return new courseline_ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_view_courseline.courseline_ViewHolder holder, int position) {
        courseline courseline = list.get(position);
        holder.session.setText(courseline.getsession());
        holder.courses.setText(courseline.getcourses());


    }







    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class courseline_ViewHolder extends RecyclerView.ViewHolder{

        TextView courses, session;

        public courseline_ViewHolder(@NonNull View itemView) {
            super(itemView);

            courses = itemView.findViewById(R.id.course_name);
            session = itemView.findViewById(R.id.semester);


        }
    }

}
