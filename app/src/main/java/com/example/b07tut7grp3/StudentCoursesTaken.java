package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentCoursesTaken extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    String userId;
    utscStudent student;
    ArrayList<TakenListModel> list = new ArrayList<>();
    Subject subject;
    String courseName;
    TextView addMore;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TakenM_RecyclerViewAdap adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses_taken);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
        toolbar = findViewById(R.id.courses_taken_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses Taken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                recyclerView = findViewById(R.id.taken_recycler);
                student = new utscStudent(snapshot.child("Users").child("Students").child("utscStudents").child(userId));
                addMore = (TextView) findViewById(R.id.add_more_courses);
                if(student.coursesTaken.size() == 0){
                    addMore.setText("You have not completed any courses yet! Add some by clicking the '+' above.");
                } else {
                    addMore.setText("");
                }
                for(int i = 0; i < student.coursesTaken.size(); i++){
                    if(snapshot.child("Courses").hasChild(student.coursesTaken.get(i))){
                        subject = Subject.valueOf(snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Subject").getValue().toString());
                        courseName = snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Name").getValue().toString();
                        list.add(new TakenListModel(student.coursesTaken.get(i),courseName, subject.toString()));
                    }
                }
                adapter = new TakenM_RecyclerViewAdap(StudentCoursesTaken.this, list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(StudentCoursesTaken.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if(item.getItemId() == R.id.add_taken_act){
            Intent intent = new Intent(StudentCoursesTaken.this, AddTakenCourse.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taken_courses_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.show_taken_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

