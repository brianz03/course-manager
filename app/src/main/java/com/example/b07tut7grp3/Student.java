package com.example.b07tut7grp3;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * An abstract module for storing and obtaining data about student users
 * Contains information such as name, courses taken, planned courses,
 * current year, and current school
 * @author Kevin Li
 * @since 0.1
 */
public abstract class Student extends User{
    /**
     * An abstract method for calculating total credits earned
     * @return the total number of credits earned
     */
    public abstract double getCreditsEarned();

    protected String firstName, lastName;
    public List<String> coursesTaken;
    protected List<String> plannedCourses;
    protected int currentYear;
    protected String currentSchool;

    /**
     * Abstract method for adding planned courses
     * @param planned the course code of the planned course
     */
    public void addPlannedCourse(String planned){
        plannedCourses.add(planned);
    }

    /**
     * A method for completing planned courses
     * Updates the course database by calling the abstract method updateCourses since
     * student information is stored in different paths depending on the school attended
     * @param completed the course to be completed
     */
    public void completeCourse(String completed) {

        coursesTaken.add(completed);
        plannedCourses.remove(completed);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference()
                .getRoot().child("Users").child("Students");
        updateCourses(dbref);

    }

    /**
     * An abstract class to update course information
     * @param dbref a DatabaseReference to Root/Users/Students
     */
    abstract void updateCourses(DatabaseReference dbref);
    // Getter methods
    public int getCurrentYear(){
        return currentYear;
    }
    public List<String> getCoursesTaken(){ return coursesTaken; }
    public List<String> getPlannedCourses(){ return plannedCourses; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCurrentSchool(){
        return currentSchool;
    }

}
