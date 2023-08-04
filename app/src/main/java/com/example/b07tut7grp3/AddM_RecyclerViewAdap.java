package com.example.b07tut7grp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddM_RecyclerViewAdap extends RecyclerView.Adapter<AddM_RecyclerViewAdap.MyAddViewHolder> implements Filterable {

    Context context;
    ArrayList<AddListModel> adminCourses;
    ArrayList<AddListModel> adminCoursesFull;
    SharedPreferences sharedPreferences;
    DatabaseReference dbref;
    Student student;
    ArrayList<String> coursesTakenFill;

    public AddM_RecyclerViewAdap(Context context, ArrayList<AddListModel> adminCourses) {
        this.context = context;
        this.adminCourses = adminCourses;
        adminCoursesFull = new ArrayList<>(adminCourses);
    }

    @NonNull
    @Override
    public AddM_RecyclerViewAdap.MyAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_taken_course_list_item, parent, false);
        return new AddM_RecyclerViewAdap.MyAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddM_RecyclerViewAdap.MyAddViewHolder holder, int position) {
        int p = position;
        holder.courseId.setText(adminCourses.get(p).getCourseId());
        holder.courseName.setText(adminCourses.get(p).getCourseName());
        holder.subject.setText(adminCourses.get(p).getCourseSubject());
        holder.prereqs.setText(adminCourses.get(p).getPrereqs());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance()
                        .getReference().getRoot();
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("user","");
                        student = new utscStudent(snapshot.child("Users").child("Students")
                                .child("utscStudents").child(userId));
                        if(student.coursesTaken.contains(adminCourses.get(p).courseId)){
                            Toast.makeText(context,"You have already taken this course", Toast.LENGTH_SHORT).show();
                        } else if(checkPrereqs(snapshot, student, adminCourses.get(p).courseId) == false){
                            Toast.makeText(context,"You do not meet the course prerequisites", Toast.LENGTH_SHORT).show();
                        } else {
                            student.coursesTaken.add(adminCourses.get(p).courseId);
                            coursesTakenFill = new ArrayList<>(student.coursesTaken);
                            coursesTakenFill.add(0, "*");
                            Map<String, Object> detailsMap = new HashMap<>();
                            detailsMap.put("coursesTaken", coursesTakenFill);
                            dbref.child("Users").child("Students").child("utscStudents").child(userId).updateChildren(detailsMap);
                            Toast.makeText(context,adminCourses.get(p).courseId + " added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});
            }
        });
    }

    private boolean checkPrereqs(DataSnapshot snapshot, Student student, String courseId) {
        for(DataSnapshot i: snapshot.child("Courses").child(courseId).child("Prerequisites").getChildren()){
            if(!student.coursesTaken.contains(i.getValue().toString())  && !i.getValue().toString().equals("*") ){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return adminCourses.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<AddListModel> filtered = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filtered.addAll(adminCoursesFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(AddListModel i: adminCoursesFull){
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
            adminCourses.clear();
            adminCourses.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class MyAddViewHolder extends RecyclerView.ViewHolder {

        TextView courseId;
        TextView courseName;
        TextView subject;
        TextView prereqs;
        Button addBtn;

        public MyAddViewHolder(@NonNull View itemView) {
            super(itemView);
            courseId = itemView.findViewById(R.id.course_id);
            courseName = itemView.findViewById(R.id.course_name);
            subject = itemView.findViewById(R.id.subject_txt);
            prereqs = itemView.findViewById(R.id.prereq_txt);
            addBtn = itemView.findViewById(R.id.add_btn);
        }
    }
}
