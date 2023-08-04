package com.example.admintoolsUTSC;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07tut7grp3.R;
import com.example.b07tut7grp3.Semester;
import com.example.b07tut7grp3.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class coursemodify_test extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String course_id;
    List<String> prerequisites;
    List<String> semester;
    List<String> subjects;
    String course_name;
    String course = "[a-zA-Z]{4}[0-9]{2}";
    Boolean checkSwitch1 = false;
    Boolean checkSwitch2= false;
    Boolean checkSwitch3 = false;
    List<String> courses;
    Button delete;
    Button back;
    utscCourseModifier courseMod;

    EditText courseCode, prereqs, courseName;
    Switch fallSW, winterSW,summerSW;
    Button AddPrereqBtn;
    Button addCourseBtn;
    Spinner spinner;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursemodify_test);

        courseCode = findViewById(R.id.courseCodeField);
        courseName = findViewById(R.id.courseNameField);
        prereqs = findViewById(R.id.prereqField);
        addCourseBtn = findViewById(R.id.change_button);
        AddPrereqBtn = findViewById(R.id.addPrereq);
        fallSW = findViewById(R.id.fall);
        winterSW = findViewById(R.id.winter);
        summerSW = findViewById(R.id.summer);
        spinner = findViewById(R.id.spinner);

        subjects = new ArrayList<String>();

        prerequisites = new ArrayList<>();
        semester = new ArrayList<>();
        courses = new ArrayList<>();


        for (Subject s : Subject.values()) {
            System.out.println(s.name().getClass().getName());
            subjects.add(s.name());
        }
        String[] arr = subjects.toArray(new String[subjects.size()]);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        AddPrereqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prerequisite;
                prerequisite = prereqs.getText().toString().toUpperCase();

                if  ((TextUtils.isEmpty(prerequisite)) || (!prerequisite.matches(course))){
                    prereqs.setError("Course must contain 4 letters followed by 2 numbers");
                    Toast.makeText(coursemodify_test.this, "Invalid Course", Toast.LENGTH_SHORT).show();
                    return;
                }else if (prerequisite.equals(courseCode.getText().toString().toUpperCase())) {
                    prereqs.setError("Prerequisite cannot be the same as course code");
                    Toast.makeText(coursemodify_test.this, "Prerequisite matches course code", Toast.LENGTH_SHORT).show();
                    return;
                }else if (prerequisites.contains(prerequisite)){
                    prereqs.setError("Prerequisite already exists");
                    Toast.makeText(coursemodify_test.this, "Prerequisite already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                prerequisites.add(prerequisite);
                prereqs.getText().clear();
                Toast.makeText(coursemodify_test.this, "Added Prerequisite", Toast.LENGTH_SHORT).show();
            }
        });

        fallSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.FALL.name());
                    checkSwitch1 = true;

                }
                else{
                    semester.remove(Semester.FALL.name());
                    checkSwitch1 = false;
                    System.out.println(semester);

                }
            }
        });

        winterSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.WINTER.name());
                    checkSwitch2 = true;

                }
                else{
                    semester.remove(Semester.WINTER.name());
                    checkSwitch2 = false;

                }
            }
        });

        summerSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.SUMMER.name());
                    checkSwitch3 = true;

                }
                else{
                    semester.remove(Semester.SUMMER.name());
                    checkSwitch3 = false;

                }
            }
        });

        delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursenm = getIntent().getStringExtra("courseName");
                courseMod.deleteData(coursenm);
                sendUserBack();
                Toast.makeText(coursemodify_test.this, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserBack();
            }
        });
        addCourseBtn = findViewById(R.id.change_button);
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_id = courseCode.getText().toString().toUpperCase();
                course_name = courseName.getText().toString();
                String subject = spinner.getSelectedItem().toString();
                courses.clear();

                        String name = getIntent().getStringExtra("courseName");
                        System.out.println(name);

                        System.out.println(courses.contains(course_id));

                        if (!course_id.matches(course)) {
                            courseCode.setError("Course must contain 4 letters followed by 2 numbers");
                            Toast.makeText(coursemodify_test.this, "Invalid Course Code", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (TextUtils.isEmpty(course_name)){
                            courseName.setError("Name cannot be empty");
                            Toast.makeText(coursemodify_test.this, "Invalid Course Name", Toast.LENGTH_SHORT).show();
                            return;
                        }if (!checkSwitch1 && !checkSwitch2 && !checkSwitch3){
                            Toast.makeText(coursemodify_test.this, "Select At Least One Semester", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(prerequisites.isEmpty()){
                            prerequisites.add(0, "*");
                        }
                        HashMap<String, Object> courseMap = new HashMap<>();
                        courseMap.put("Prerequisites", prerequisites);
                        courseMap.put("Subject", subject);
                        courseMap.put("Name", course_name);
                        courseMap.put("Semester", semester);
                        courseMap.put("courseName", course_id);
                if (course_id != name){
                    dbref.child(course_id).setValue(null);
                }

                        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
                        dbref.child(course_id).setValue(courseMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                prerequisites.clear();
                                semester.clear();
                                Toast.makeText(coursemodify_test.this, "Added Course", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), com.example.admintoolsUTSC.Admin_view_course.class));
                            }
                        });
                    }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinner.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void sendUserBack() {
        Intent intent=new Intent(coursemodify_test.this, Admin_view_course.class);
        //send user to personal info page and then to StudentHomePage.class.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //    Button back;
//    Button edit;
//    Button delete;
//    utscCourseModifier courseMod;
//
//    String course = "[a-zA-Z]{4}[0-9]{2}";
//
//    EditText courseCode,courseName,prereq1,prereq2,prereq3;
//    Switch fallSW, winterSW, summerSW;
//    Button AddPrereqBtn;
//    Spinner postspinner;
//
//    List<String> prerequisites;
//    DatabaseReference dbref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.coursemodify_test);
//
//        String name = getIntent().getStringExtra("courseName");
//
////        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
////        //find the fields
////        courseCode = findViewById(R.id.courseCode);
////        courseName = findViewById(R.id.courseName);
////        prereq1 = findViewById(R.id.prereq1);
////        prereq2 = findViewById(R.id.prereq2);
////        prereq3 = findViewById(R.id.prereq3);
////
////        fallSW = findViewById(R.id.fall);
////        winterSW = findViewById(R.id.winter);
////        summerSW = findViewById(R.id.summer);
////
////        //assign old values
////        String code = dbref.child(name).child("courseName").get().toString();
////        String title = dbref.child(name).child("Name").get().toString();
////        Log.e(code,"code");
////        Log.e(title,"title");
////        courseCode.setText(code, TextView.BufferType.EDITABLE);
////        courseName.setText(title, TextView.BufferType.EDITABLE);
//
//
//        courseCode = findViewById(R.id.courseCode);
//        courseName = findViewById(R.id.courseName);
//        prereq1 = findViewById(R.id.prereq1);
//        prereq2 = findViewById(R.id.prereq2);
//        prereq3 = findViewById(R.id.prereq3);
//
//        Switch fallSW = findViewById(R.id.fall);
//        Switch winterSW = findViewById(R.id.winter);
//        Switch summerSW = findViewById(R.id.summer);
//
//        Admin admin = null;
//        try {
//            admin = Admin.getInstance();
//        } catch (ExceptionMessage e) {
//            e.printStackTrace();
//        }
//        try {
//            courseMod = new utscCourseModifier(admin);
//        } catch (ExceptionMessage e) {
//            e.printStackTrace();
//        }
//
//        postspinner = findViewById(R.id.postspinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.POSt_list, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        postspinner.setAdapter(adapter);
//        postspinner.setOnItemSelectedListener(this);
//
//        //back button
//        back = findViewById(R.id.back_to_avc);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendUserBack();
//            }
//        });
//
//        //edit course info button
//        edit = findViewById(R.id.edit_button);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // to be implemented
//                String name = getIntent().getStringExtra("courseName");
//                String courseId = courseCode.getText().toString().toUpperCase();
//                String coursename = courseName.getText().toString();
//
//                prerequisites = new ArrayList<>();
//                if(prereq1.getText().toString().matches("")){ prerequisites.add(prereq1.getText().toString().toUpperCase()); }
//                if(prereq2.getText().toString().matches("")){ prerequisites.add(prereq2.getText().toString().toUpperCase()); }
//                if(prereq3.getText().toString().matches("")){ prerequisites.add(prereq3.getText().toString().toUpperCase()); }
//                if(prerequisites.size()==0){
//                    prerequisites.add("*");
//                }
//
//                List<Semester> semester = new ArrayList<Semester>(Arrays.asList(Semester.values()));
//                if(!(fallSW.isChecked())){ semester.remove(Semester.FALL); }
//                if(!(winterSW.isChecked())){ semester.remove(Semester.WINTER); }
//                if(!(summerSW.isChecked())){ semester.remove(Semester.SUMMER); }
//
//                String POStname=postspinner.getSelectedItem().toString();
//                Subject POSt=Subject.getProgram(POStname);
//
//                courseMod.setCourseID(courseId,name);
//                courseMod.setName(courseId,coursename);
//                courseMod.setPrereqs(prerequisites,courseId);
//                courseMod.setSemester(semester,courseId);
//                courseMod.setSubject(POSt,courseId);
//
//                courseMod.deleteData(name);
//
//                sendUserBack();
//            }
//        });
//
//        //delete button
//        delete = findViewById(R.id.delete_button);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String coursenm = getIntent().getStringExtra("courseName");
//                courseMod.deleteData(coursenm);
//                sendUserBack();
//                Toast.makeText(coursemodify_test.this, "Course Deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    public void displayPrereqs(View V){
//        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    prerequisites.clear();
//                    for(DataSnapshot dss:snapshot.getChildren()){
//                        String prrq = dss.getValue(String.class);
//                        prerequisites.add(prrq);
//                    }
//                    int i = prerequisites.size();
//                    if(i==1){
//                        prereq1.setText(prerequisites.get(0));
//                    }
//                    if(i==2){
//                        prereq1.setText(prerequisites.get(0));
//                        prereq2.setText(prerequisites.get(1));
//                    }else{
//                        prereq1.setText(prerequisites.get(0));
//                        prereq2.setText(prerequisites.get(1));
//                        prereq3.setText(prerequisites.get(2));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void sendUserBack() {
//        Intent intent=new Intent(coursemodify_test.this, Admin_view_course.class);
//        //send user to personal info page and then to StudentHomePage.class.
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}


