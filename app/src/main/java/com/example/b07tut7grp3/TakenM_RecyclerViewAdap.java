package com.example.b07tut7grp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TakenM_RecyclerViewAdap extends RecyclerView.Adapter<TakenM_RecyclerViewAdap.MyTakenViewHolder> implements Filterable {

    Context context;
    ArrayList<TakenListModel> coursesTaken;
    ArrayList<TakenListModel> coursesTakenFull;

    public TakenM_RecyclerViewAdap(Context context, ArrayList<TakenListModel> coursesTaken) {
        this.context = context;
        this.coursesTaken = coursesTaken;
        coursesTakenFull = new ArrayList<>(coursesTaken);
    }

    @NonNull
    @Override
    public TakenM_RecyclerViewAdap.MyTakenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.taken_course_list_item, parent, false);
        return new TakenM_RecyclerViewAdap.MyTakenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTakenViewHolder holder, int position) {
        int p = position;
        holder.courseId.setText(coursesTaken.get(p).courseId);
        holder.courseName.setText(coursesTaken.get(p).courseName);
        holder.subject.setText(coursesTaken.get(p).courseSubject);
    }

    @Override
    public int getItemCount() {
        return coursesTaken.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TakenListModel> filtered = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filtered.addAll(coursesTakenFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(TakenListModel i: coursesTakenFull){
                    if(i.getCourseId().toLowerCase().contains(filterPattern)){
                        filtered.add(i);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            coursesTaken.clear();
            coursesTaken.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyTakenViewHolder extends RecyclerView.ViewHolder {
        TextView courseId;
        TextView courseName;
        TextView subject;

        public MyTakenViewHolder(@NonNull View itemView) {
            super(itemView);
            courseId = itemView.findViewById(R.id.taken_course_code);
            courseName = itemView.findViewById(R.id.taken_course_name);
            subject = itemView.findViewById(R.id.taken_subject);
        }
    }
}
