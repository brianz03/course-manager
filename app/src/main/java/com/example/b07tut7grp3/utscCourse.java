package com.example.b07tut7grp3;

import com.google.firebase.database.*;
import java.util.*;

/**
 * A class that stores information about UTSC courses
 * @author Kevin Li
 * @since 0.1
 */
public final class utscCourse implements Course{
    private final String course_id;
    private final List<String> prerequisites;
    private final List<Semester> semester;
    private final Subject subject;
    private final String course_name;

    public utscCourse(String course_id, List<String> prerequisites,
                      List<Semester> semester, Subject subject, String course_name) {
        this.course_id = course_id;
        this.prerequisites = prerequisites;
        this.semester = semester;
        this.subject = subject;
        this.course_name = course_name;
    }

    /**
     * Class constructor for UTSC courses
     * @param data a dataSnapshot of all courses, use the root/Courses directory
     * @param course_id a course code of the course to be obtained
     * @throws ExceptionMessage if the course cannot be found within the dataSnapshot
     */
    public utscCourse(DataSnapshot data, String course_id) throws ExceptionMessage{

        if(!data.hasChild(course_id))
            throw new ExceptionMessage("could not find course!");
        this.course_id = course_id;
        DataSnapshot courseInfo = data.child(course_id);
        this.subject = Subject.valueOf((String)(courseInfo.child("Subject").getValue()));
        prerequisites = new ArrayList<>();

        this.course_name = courseInfo.child("Name").getValue().toString();
        if(courseInfo.hasChild("Prerequisites")) {
            for (DataSnapshot i : courseInfo.child("Prerequisites").getChildren())
                if(!i.getValue().toString().equals("*"))prerequisites.add(i.getValue().toString());
        }
        semester = new ArrayList<>();
        for(DataSnapshot i : courseInfo.child("Semester").getChildren())
            semester.add(Semester.valueOf(i.getValue().toString()));
    }

    //Simple getter methods
    @Override
    public String getCourseId() {
        return course_id;
    }
    @Override
    public String getName(){
        return course_name;
    }
    @Override
    public List<String> getPrerequisites() {
        return this.prerequisites;
    }

    @Override
    public List<Semester> getSemester() {
        return semester;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public int hashCode() {
        return course_id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if((!(obj instanceof utscCourse)))return false;
        return this.course_id.equals(((utscCourse) obj).getCourseId());
    }
    @Override
    public String toString(){
        return getCourseId();
    }
}

//poi