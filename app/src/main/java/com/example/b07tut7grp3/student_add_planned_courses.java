package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class student_add_planned_courses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    List<String> courses;
    List<String> completed;
    List<String> plannedCourses;
    List<String> prereqsLst;
    List<String> plannedDBLST;
    ArrayList<plannedCourse> planned;
    Spinner eligibleCourseList;
    RecyclerView recyclerView;
    Button AddCourseBtn;
    Button saveBtn;
    Button timeline;

    String uid =  FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_planned_courses);

        courses = new ArrayList<>();
        completed = new ArrayList<>();
        plannedCourses = new ArrayList<>();
        prereqsLst = new ArrayList<>();
        plannedDBLST = new ArrayList<>();
        eligibleCourseList = findViewById(R.id.coursesList);
        planned = new ArrayList<>();
        AddCourseBtn = findViewById(R.id.button);
        saveBtn = findViewById(R.id.saveButton);
        timeline = findViewById(R.id.genTimelineButton);

        plannedCourseAdapter courseArrayAdapter = new plannedCourseAdapter(R.layout.plannedcourses_row, planned);
        recyclerView = (RecyclerView) findViewById(R.id.plannedCourseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseArrayAdapter);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eligibleCourseList.setAdapter(adapter);
        eligibleCourseList.setOnItemSelectedListener(this);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                completed.clear();
                planned.clear();
                plannedCourses.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    courses.add(snap.getKey());
                }
                DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child("Students").child("utscStudents").child(uid).child("coursesTaken");
                database2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            completed.add(snap.getValue(String.class));
                            //System.out.println(snap.getValue());
                        }
                        System.out.println(completed.toString());
                        courses.removeAll(completed);

                        //System.out.println(courses.toString());
                        adapter.notifyDataSetChanged();
                        DatabaseReference database3 = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child("Students").child("utscStudents").child(uid).child("plannedCourses");
                        database3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                planned.clear();
                                plannedCourses.clear();
                                plannedDBLST.clear();
                                for (DataSnapshot snap: snapshot.getChildren()) {
                                    if (!snap.getValue(String.class).equals("*")) {
                                        plannedDBLST.add(snap.getValue(String.class));
                                        plannedCourses.add(snap.getValue(String.class));
                                        planned.add(new plannedCourse(snap.getValue(String.class)));
                                    }
                                }
                                System.out.println(plannedCourses);
                                courseArrayAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        AddCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (courses.isEmpty()){
                    Toast.makeText(student_add_planned_courses.this, "Cannot add course", Toast.LENGTH_SHORT).show();
                    return;
                }
                String c = eligibleCourseList.getSelectedItem().toString();

                if (plannedCourses.contains(c)){
                    Toast.makeText(student_add_planned_courses.this, "Course already in planner", Toast.LENGTH_SHORT).show();
                    return;
                }
                plannedCourses.add(c);
                planned.add(new plannedCourse(c));
                courseArrayAdapter.notifyDataSetChanged();
                Toast.makeText(student_add_planned_courses.this, "Added Course", Toast.LENGTH_SHORT).show();
            }
        });

        courseArrayAdapter.setOnItemClickListener(new plannedCourseAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {

                planned.remove(planned.get(position));
                plannedCourses.remove(position);
               // System.out.println(plannedCourses);
                courseArrayAdapter.notifyItemRemoved(position);
                Toast.makeText(student_add_planned_courses.this, "Removed Course", Toast.LENGTH_SHORT).show();

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plannedCourses.contains("*") && !plannedCourses.isEmpty()){
                    plannedCourses.remove("*");
                }
                if (plannedCourses.isEmpty()){
                    plannedCourses.add(0, "*");
                }
                String c = eligibleCourseList.getSelectedItem().toString();
                prereqsLst.clear();
                DatabaseReference prereqs = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses").child(c).child("Prerequisites");
                prereqs.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            if (!snap.getValue(String.class).equals("*")) {
                                prereqsLst.add(snap.getValue(String.class));
                            }
                        }
                        completed.remove("*");
                        prereqsLst.removeAll(Arrays.asList(null,""));
                        System.out.println(prereqsLst);
                        System.out.println(completed);
                        System.out.println(plannedCourses);


                        System.out.println(((!completed.containsAll(prereqsLst)) || (!plannedCourses.containsAll(prereqsLst)) )&& !(prereqsLst.isEmpty()));
                        if (completed.containsAll(prereqsLst) == false && plannedCourses.containsAll(prereqsLst) == false && prereqsLst.isEmpty() == false){
                            Toast.makeText(student_add_planned_courses.this, "Missing prerequisite", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Collections.sort(plannedCourses);
                        HashMap<String, Object> courseMap = new HashMap<>();
                        courseMap.put("plannedCourses", plannedCourses);
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child("Students").child("utscStudents").child(uid);
                        dbref.updateChildren(courseMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(student_add_planned_courses.this, "Saved Changes", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plannedDBLST.isEmpty()){
                    Toast.makeText(student_add_planned_courses.this, "No Courses Added. Add Courses or Click Save Changes To Update", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), view_courseline.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}