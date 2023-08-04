package com.example.b07tut7grp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class view_courseline extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_view_courseline Adapter;
    ArrayList<courseline> list;
    int i = 0;
    private SharedPreferences sharedPreferences;
    private String userID;
    private utscStudent student;

    Subject subject;
    String courseName;
    String Name;
    String Prerequisites;

    int year = 2022;
    Semester semester = Semester.FALL;

    private void getPlannedCourses(UserListCallback callback){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().getRoot()
                .child("Users").child("Students").child("utscStudents").child(userID);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student = new utscStudent(snapshot);
                callback.onCallback(student.getPlannedCourses());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void displayInfo(List<String> ordered_timeline, DataSnapshot snapshot){
        // create a list of course to take
        HashMap<String, Course> needToTake = new HashMap<>();
        for (String code : ordered_timeline) {
            try {
                Course course = new utscCourse(snapshot, code);
                System.out.println("----------");
                System.out.println(code);
                System.out.println(ordered_timeline.get(0));
                System.out.println(course);
                System.out.println("----------");
                needToTake.put(code, course);
            } catch (ExceptionMessage e) {
                e.printStackTrace();
            }
        }


        List<String> taken = new ArrayList<>(student.coursesTaken);
        List<String> newTaken = new ArrayList<>();

        int currYear = year;
        Semester currSemester = semester;

        // remove all the taken course
        List<String> keys = new ArrayList<>(needToTake.keySet());
        for (String code: keys) {
            if (taken.contains(code))
                needToTake.remove(code);
        }

        System.out.println(needToTake.size());
        while (needToTake.size() > 0) {
            System.out.println("shimakze"+ needToTake);

            for (Course course : needToTake.values()) {
                boolean hasAllPre = taken.containsAll(course.getPrerequisites());
                boolean hasOffer = course.getSemester().contains(currSemester);





                if (hasAllPre && hasOffer) {
                    newTaken.add(course.getCourseId());
                }
            }
            System.out.println(currYear + " " + currSemester + " " + newTaken.size());
            // add a new row to TABLE 2
            list.add(new courseline(currYear, currSemester, newTaken));
            // remove all the new taken courses from needTOTake
            for (String takenCode : newTaken)
                needToTake.remove(takenCode);
            taken.addAll(newTaken);
            newTaken.clear();
            // update to next session
            if (currSemester == Semester.FALL) {
                currSemester = Semester.WINTER;
                currYear += 1;
            }
            else if (currSemester == Semester.WINTER)
                currSemester = Semester.SUMMER;
            else if (currSemester == Semester.SUMMER)
                currSemester = Semester.FALL;
        }
        Adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courseline);

        sharedPreferences = getApplicationContext()
                .getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user","");

        recyclerView = findViewById(R.id.course);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        Adapter = new Adapter_view_courseline(this, list);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view_courseline.this));

        getPlannedCourses(new UserListCallback() {
            @Override
            public void onCallback(List<String> ordered_timeline) {
                FirebaseDatabase.getInstance().getReference("Courses")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                displayInfo(ordered_timeline, snapshot);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                System.out.println("poipoi" + ordered_timeline.size());
            }

        });

    }
}
