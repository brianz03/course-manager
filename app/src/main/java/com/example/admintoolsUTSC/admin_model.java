package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class admin_model {

    private FirebaseAuth fAuth;
    private DatabaseReference userRef;
    private DatabaseReference courseRef;


    public admin_model() {
        fAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Students");
        courseRef = FirebaseDatabase.getInstance().getReference("Courses");
    }

    public void login(String email, String password, Consumer<String> callback) {

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    System.out.println(userID);
                    database.child("Users")
                            .child("Admin")
                            .child(userID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        callback.accept(fAuth.getUid());
                                    } else {
                                        callback.accept(null);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { } });
                }
            }
        });
    }
    public boolean isFound(String username) {
        return "admin@utsc.ca".equals(username);
    }
}