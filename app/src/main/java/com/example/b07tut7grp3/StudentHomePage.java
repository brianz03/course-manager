package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintStream;

public class StudentHomePage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String userID;
    private utscStudent student;
    TextView schoolName;
    TextView firstName;
    TextView lastName;
    TextView currentYr;
    TextView creditsEarned;
    TextView POst;
    TextView email;
    CardView toTaken;
    CardView toTimeline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user","");
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(userID);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student = new utscStudent(snapshot);
                schoolName = (TextView) findViewById(R.id.txt_school);
                schoolName.setText(student.currentSchool);
                firstName = (TextView) findViewById(R.id.txt_firstName);
                firstName.setText(student.firstName+",");
                lastName = (TextView) findViewById(R.id.txt_lastName);
                lastName.setText(student.lastName);
                currentYr = (TextView) findViewById(R.id.txt_current_yr);
                currentYr.setText("Current Year: " + Integer.toString(student.currentYear));
                creditsEarned = (TextView) findViewById(R.id.txt_credits_earned);
                creditsEarned.setText("Credits Earned: " + Double.toString(student.coursesTaken.size() * 0.5));
                POst = (TextView) findViewById(R.id.txt_POst);
                POst.setText("POst: " + student.getCurrentPOSt());
                email = (TextView) findViewById(R.id.txt_email);
                email.setText(student.email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        Toolbar toolbar = findViewById(R.id.student_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Home Page");
        toTaken = (CardView) findViewById(R.id.go_to_taken);
        toTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentCoursesTaken();
            }
        });

        toTimeline = (CardView) findViewById(R.id.create_timeline);
        toTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openGetPlannedCourses();}
        });

    }

    private void openGetPlannedCourses() {
        Intent intent = new Intent(this, student_add_planned_courses.class);
        startActivity(intent);
    }

    public void openStudentCoursesTaken(){
        Intent intent = new Intent(this, StudentCoursesTaken.class);
        startActivity(intent);
    }
}