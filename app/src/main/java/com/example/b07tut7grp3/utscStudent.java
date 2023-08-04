package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for UTSC student users, inherits methods from Student
 * @author Kevin Li
 * @since 0.1
 */
public final class utscStudent extends Student{
    private Subject currentPOSt;

    /**
     * A class constructor for creating a new UTSC student
     * Will also add the student's data to the database
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param currentYear the student's current year
     * @param post the student's current program of study
     * @param email the student's email
     * @param username the student's username
     */
    public utscStudent(String firstName, String lastName,
                   int currentYear, Subject post,
                       String email, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTaken = new ArrayList<>();
        this.plannedCourses = new ArrayList<>();
        this.currentYear = currentYear;
        this.currentSchool = "UTSC";
        this.currentPOSt = post;
        this.email = email;
        this.username = username;
        uploadData();
    }

    /**
     * A constructor for retrieving student information from the database
     * NOTE: this method involves a thread, so do not use utscStudent if the thread
     * is not completed
     * I.e. do not make any of utscStudent's methods inside
     * the method containing the constructor
     * @param dbref a DataSnapshot pointing to the student's data
     */
    public utscStudent(DataSnapshot dbref) {
        /*
        To whoever uses this function
        Add the following code:
        In global: private utscStudent student;
        In method:
        Database dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(whatever the username is);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void OnComplete(@NonNull Task<DataSnapshot> task){
                student = new utscStudent(task.getResult());
                // can add a success message or something here...
            }
        });
        // continue code here...
         */

        this.firstName = dbref.child("FirstName").getValue().toString();
        this.lastName = dbref.child("LastName").getValue().toString();
        this.username = dbref.child("Username").getValue().toString();
        this.email = dbref.child("Email").getValue().toString();
        this.currentSchool = "UTSC";
        this.currentYear = Integer.parseInt(dbref.child("currentYear").getValue().toString());
        this.currentPOSt = Subject.valueOf(dbref.child("POst").getValue().toString());
        List<String> coursesTaken = new ArrayList<>();
        List<String> plannedCourses = new ArrayList<>();
        for(DataSnapshot i : dbref.child("coursesTaken").getChildren()){
            if(!(i.getValue().equals("*"))) coursesTaken.add((String)(i.getValue()));
        }
        for(DataSnapshot i : dbref.child("plannedCourses").getChildren()){
            if(!(i.getValue().equals("*"))) plannedCourses.add((String)(i.getValue()));
        }
        this.coursesTaken = coursesTaken;
        this.plannedCourses = plannedCourses;
    }
    private void uploadData(){
        Map<String, Object> userMap = new HashMap<>();
        Map<String, Object> detailsMap = new HashMap<>();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot()
                .child("Users").child("Students").child("utscStudents").child(username);
        detailsMap.put("Email", this.email);
        detailsMap.put("POst", this.currentPOSt.name());
        detailsMap.put("currentSchool", this.currentSchool);
        detailsMap.put("currentYear", this.currentYear);
        detailsMap.put("FirstName", this.firstName);
        detailsMap.put("LastName", this.lastName);
        detailsMap.put("Username",this.username);
        List<String> coursesTaken = this.coursesTaken;
        List<String> plannedCourses = this.plannedCourses;
        if(coursesTaken.isEmpty()) coursesTaken.add("*");
        if(plannedCourses.isEmpty()) plannedCourses.add("*");
        detailsMap.put("coursesTaken", coursesTaken);
        detailsMap.put("plannedCourses", plannedCourses);
        dbref.setValue(detailsMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error,
                                   @NonNull DatabaseReference ref) {
                MessageSystem message = new MessageSystem("Successfully uploaded data");
                message.successMessage();
            }
        });
    }

    @Override
    protected void updateCourses(DatabaseReference dbref){
        dbref = dbref.child("utscStudents").child(username);
        Map<String, Object> detailsMap = new HashMap<>();
        List<String> coursesTaken = new ArrayList<>();
        List<String> plannedCourses = new ArrayList<>();
        if(this.coursesTaken.size() == 0){
            coursesTaken.add("*");
        }
        else
            coursesTaken = this.coursesTaken;

        if(this.plannedCourses.size() == 0) {
            plannedCourses.add("*");
        }
        else
            plannedCourses = this.plannedCourses;

        detailsMap.put("plannedCourses", plannedCourses);
        detailsMap.put("coursesTaken", coursesTaken);
        dbref.updateChildren(detailsMap);
    }

    /**
     * Changes the current program of study for a student
     * @param newPOst the Subject referring to the student's new program
     */
    public void changePOst(Subject newPOst){
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students").child("utscStudents")
                .child(this.username);
        this.currentPOSt = newPOst;
        Map<String, Object> input = new HashMap<>();
        input.put("POst", currentPOSt.name());
        dbref.updateChildren(input);

    }
    /**
     * returns the number of credits earned according to UofT calculations
     * @return total number of credits earned
     */
    @Override
    public double getCreditsEarned() {
        return coursesTaken.size() * 0.5;
    }
    // getter methods
    public Subject getCurrentPOSt() {
        return currentPOSt;
    }
}
