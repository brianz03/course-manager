package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.b07tut7grp3.*;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A module for adding/changing course information. Can be used for changing course information,
 * adding courses, as well as deleting them. Requires an instance of Admin to initialize,
 * otherwise an error will be returned.
 * @author Kevin Li
 * @since 0.1
 * @see Admin
 */
final class utscCourseModifier {
    private DatabaseReference dbref;

    /**
     * Constructor for modifying/deleting courses, initialize for access to setter methods
     * @param user an instance of Admin
     * @throws ExceptionMessage if the user is not an Admin
     */
    public utscCourseModifier(User user) throws ExceptionMessage{
        //Security check, utscCourseModifier must be instantiated with Admin credentials
        if(!(user instanceof Admin)) throw new ExceptionMessage("Action restricted");
        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");

    }

    /**
     * Constructor for adding a course, requires a Course with all information filled out
     * @param user an instance of Admin
     * @param course a copy of the Course to be added
     * @throws ExceptionMessage if user is not Admin
     */
    public utscCourseModifier(User user, Course course) throws ExceptionMessage {
        if (!(user instanceof Admin)) throw new ExceptionMessage("Action restricted");
        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
        HashMap<String, Object> courseMap = new HashMap<>();
        HashMap<String, Object> detailsMap = new HashMap<>();
        if(!course.getPrerequisites().isEmpty())
            detailsMap.put("Prerequisites", course.getPrerequisites());
        else{
            List<String> input = new ArrayList<>();
            input.add("*");
            detailsMap.put("Prerequisites", input);
        }
        detailsMap.put("Subject", course.getSubject().name());
        detailsMap.put("Name", course.getName());
        detailsMap.put("courseName", course.getCourseId());
        String[] semester = new String[course.getSemester().size()];
        int iter = 0;
        for (Semester i : course.getSemester()){
            semester[iter] = i.name();
            iter++;
        }
        detailsMap.put("Semester", semester);
        courseMap.put(course.getCourseId(), detailsMap);
        dbref.setValue(courseMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                MessageSystem message = new MessageSystem("Successfully uploaded course.");
                message.successMessage();
            }
        });
    }

    /**
     * Change the course code of a course
     * @param id the new ID of the course
     * @param to_change the ID of the course to be changed
     */
    public void setCourseID(String id, String to_change){
        dbref.child(to_change).setValue(id);
        dbref.child(id).child("courseName").setValue(id);

    }

    /**
     * Change the name of a course
     * @param id the course code
     * @param name the new course name
     */
    public void setName(String id, String name){
        Map<String, Object> input = new HashMap<>();
        input.put("Name", name);
        dbref.child(id).updateChildren(input);
    }

    /**
     * Change the prerequisites of a course
     * @param prereqs the new prerequisites
     * @param id the course code
     */
    public void setPrereqs(List<String> prereqs, String id){
        Map<String, Object> prereqs_map = new HashMap<>();

        if(prereqs.isEmpty()){
            List<String> prereqs_empty = new ArrayList<>();
            prereqs_empty.add("*");
            prereqs_map.put("Prerequisites", prereqs_empty);
        }
        else prereqs_map.put("Prerequisites", prereqs);
        dbref.child(id).updateChildren(prereqs_map);
    }

    /**
     * Change the available semester for the course
     * @param semesters the new available semesters
     * @param id the course code
     */
    public void setSemester(List<Semester> semesters, String id){
        List<String> semester_array = new ArrayList<>();
        for(int i = 0;i<semesters.size(); i++)
            semester_array.add(semesters.get(i).name());
        Map<String, Object> semester_map = new HashMap<>();
        semester_map.put("Semester", semester_array);
        dbref.child(id).updateChildren(semester_map);
    }

    /**
     * Change the course subject
     * @param sub the new subject name
     * @param id the course code
     */
    public void setSubject(Subject sub, String id){
        Map<String, Object> input = new HashMap<>();
        input.put("Subject", sub.name());
        dbref.child(id).updateChildren(input);
    }

    /**
     * Delete the course
     * @param id the course to be deleted
     */
    public void deleteData(String id){ dbref.child(id).setValue(null); }
}
