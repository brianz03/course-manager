package com.example.b07tut7grp3.ui.login;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Model{

    List<String> usernames;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Emails");

    public Model(){
        usernames = new ArrayList<>();
//        usernames.clear();
//        usernames.add("anExistingEmail");
//        usernames.add("sampleUserEmail1");
//        usernames.add("sampleUserEmail2");
//        usernames.add("sampleUserEmail3");
//        usernames.add("sampleUserEmail4");
//        usernames.add("sampleUserEmail114514");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usernames.clear();
                    for(DataSnapshot dss:snapshot.getChildren()){
                        usernames.add(dss.getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public boolean isFound(String username) {
        return usernames.contains(username);
    }

    public boolean goodPassword(String password){
        return (password.length()>8);
    }
}
