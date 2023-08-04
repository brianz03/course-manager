package com.example.b07tut7grp3;

public class TakenListModel {

    String courseId;
    String courseName;
    String courseSubject;

    public TakenListModel(String courseId, String courseName, String courseSubject){
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseSubject = courseSubject;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseSubject() {
        return courseSubject;
    }
}
