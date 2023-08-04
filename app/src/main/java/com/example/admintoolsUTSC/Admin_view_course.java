package com.example.admintoolsUTSC;

import static com.example.b07tut7grp3.R.id.addbutton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.b07tut7grp3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_view_course extends AppCompatActivity implements itemclick{

    List<String> courselist;
    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter Adapter;
    ArrayList<Course_admin> list;
    com.google.android.material.floatingactionbutton.FloatingActionButton addbutton;
    ListView lvcourse;


    ProgressDialog progressDialog;



    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_course);

        addbutton = findViewById(R.id.addbutton);









        recyclerView = findViewById(R.id.courseList);





        database = FirebaseDatabase.getInstance().getReference("Courses");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        Adapter = new Adapter(this,list, this);
        recyclerView.setAdapter(Adapter);





        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AddCourse.class));
            }
        });



        database.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Course_admin course = dataSnapshot.getValue(Course_admin.class);
                    list.add(course);
                }
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Admin_view_course.this, coursemodify_test.class);
        intent.putExtra("courseName", list.get(position).getCourseName());
        intent.putExtra("Subject", list.get(position).getSubject());
        startActivity(intent);


    }
}