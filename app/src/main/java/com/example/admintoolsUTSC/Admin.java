package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;

import com.example.b07tut7grp3.Course;
import com.example.b07tut7grp3.ExceptionMessage;
import com.example.b07tut7grp3.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An administrator module used for adding and changing course information
 * Can only be instantiated once
 * @author Kevin Li
 * @since 0.1
 */
public final class Admin extends User{
    // A singleton Admin class
    private static Admin admin;

    private utscCourseModifier courseMod;

    private Admin() throws ExceptionMessage {
        // Admin constructor here
        courseMod = new utscCourseModifier(this);
        admin = this;
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference()
                .getRoot().child("Users").child("Admin");
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                email = task.getResult().child("Email").getValue().toString();
                username = task.getResult().child("Username").getValue().toString();
            }
        });
    }

    /**
     * Used to obtain an instance of administrator
     * If there is no available instance, create a new
     * one by initializing a new utscCourseModifier
     * and a DatabaseReference to get personal information
     * @return an administrator instance
     * @throws ExceptionMessage if utscCourseModifier cannot be instantiated
     * @see utscCourseModifier
     */
    public static Admin getInstance() throws ExceptionMessage{
        if(admin == null) admin = new Admin();
        return admin;
    }

    /**
     * Adds a new course by initializing a new instance of utscCourseModifier
     * @param course The Course to be added with all the information filled out
     * @throws ExceptionMessage if utscCourseModifier returns an error
     */
    public void addCourse(Course course) throws ExceptionMessage{
        courseMod = new utscCourseModifier(this, course);
    }
}
