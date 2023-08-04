package com.example.b07tut7grp3;

public class AddListModel {
    String courseId;
    String courseName;
    String courseSubject;
    String prereqs;

    public AddListModel(String courseId, String courseName, String courseSubject, String prereqs) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseSubject = courseSubject;
        this.prereqs = prereqs;
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

    public String getPrereqs() {
        return prereqs;
    }
}
