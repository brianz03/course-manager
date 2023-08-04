package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

public class AddTakenCourse extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    String userId;
    ArrayList<AddListModel> list = new ArrayList<>();
    ArrayList<String> listPrereq = new ArrayList<>();
    DatabaseReference dbref;
    String courseId;
    String courseName;
    Subject courseSubject;
    String prereqs;
    RecyclerView recyclerView;
    AddM_RecyclerViewAdap adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taken_course);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
        toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Taken Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbref = FirebaseDatabase.getInstance().getReference().getRoot();
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                recyclerView = findViewById(R.id.add_recycler_view);
                for(DataSnapshot i: snapshot.child("Courses").getChildren()){
                    courseId = i.getKey();
                    courseName = i.child("Name").getValue().toString();
                    courseSubject = Subject.valueOf(i.child("Subject").getValue().toString());
                    listPrereq.clear();
                    for(DataSnapshot j: i.child("Prerequisites").getChildren()){
                        listPrereq.add(j.getValue().toString());
                    }
                    listPrereq.remove("*");
                    if(listPrereq.size() == 0){
                        prereqs = "No Prerequisites";
                    } else {
                        prereqs = "Prereqs: "+ String.join(", ", listPrereq);
                    }
                    list.add(new AddListModel(courseId,courseName,courseSubject.toString(),prereqs));
                }
                adapter = new AddM_RecyclerViewAdap(AddTakenCourse.this, list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AddTakenCourse.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_taken_search,menu);
        MenuItem searchItem = menu.findItem(R.id.add_taken_search);
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