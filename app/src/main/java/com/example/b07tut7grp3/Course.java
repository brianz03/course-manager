package com.example.b07tut7grp3;

import java.util.List;

/**
 * An interface for courses
 * @author Kevin Li
 * @since 0.1
 */
public interface Course {

    public String getCourseId();
    public List<String> getPrerequisites();
    public List<Semester> getSemester();
    public Subject getSubject();
    public String getName();
    @Override
    public String toString();
}
