//package com.example.b07tut7grp3;
//
//
//import androidx.annotation.NonNull;
//import androidx.core.util.Consumer;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class FireBaseModel {
//
//        private FirebaseAuth mAuth;
//        private DatabaseReference userRef;
//        private DatabaseReference courseRef;
//
//
//        public FireBaseModel() {
//            mAuth = FirebaseAuth.getInstance();
//            userRef = FirebaseDatabase.getInstance().getReference("Students");
//            courseRef = FirebaseDatabase.getInstance().getReference("Courses");
//        }
//
//        public void login(String email, String password, Consumer<String> callback) {
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        callback.accept(mAuth.getUid());
//                    } else {
//                        callback.accept(null);
//                    }
//                }
//            });
//        }
//
//        public void register(String email, String password, Consumer<String> callback) {
//            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    callback.accept(task.isSuccessful() ? mAuth.getUid() : null);
//                }
//            });
//        }
//
//        public void saveStudent(String userID, Student student, Consumer<Boolean> callback) {
//            userRef.child(userID).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    callback.accept(task.isSuccessful());
//                }
//            });
//        }
//
//
//        public void getCourses(Consumer<HashMap<String, utscCourse>> callback) {
//            courseRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    HashMap<String, utscCourse> courses = new HashMap<>();
//                    for (DataSnapshot courseSnp: snapshot.getChildren()) {
//                        utscCourse course = courseSnp.getValue(utscCourse.class);
//                        courses.put(course.course_id, course);
//                    }
//                    callback.accept(courses);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {}
//            });
//        }
//
//        public void saveCourse(Course course, Consumer<Boolean> callback) {
//            courseRef.child(course.code).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    callback.accept(task.isSuccessful());
//                }
//            });
//        }
//
//        public void getStudent(String userID, Consumer<Student> callback) {
//            userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Student student = snapshot.getValue(Student.class);
//                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + student.takenCourses.size());
//                    callback.accept(student);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {}
//            });
//        }
//
//    }
//
//}
