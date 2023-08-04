package com.example.b07tut7grp3;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A user module, contains email and username data
 * as well as a module to obtain a list of courses
 * @author Kevin Li
 * @since 0.1
 */
public class User {
    protected String email;
    protected String username;

    /**
     * A method to pull a list of courses from the database
     * @return a list of courses pulled from the database
     */
    public List<Course> getCourseList(){
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Courses");
        List<Course> courses = new ArrayList<>();
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot d : task.getResult().getChildren()){
                    try {
                        courses.add(new utscCourse(d, d.getKey()));
                    } catch (ExceptionMessage e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return courses;
    }
    public String getEmail(){ return email; }
    public String getUsername(){ return username; }
}
