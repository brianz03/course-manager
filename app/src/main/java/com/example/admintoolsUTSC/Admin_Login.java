package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.b07tut7grp3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Login extends AppCompatActivity{
    EditText admin_name, password;
    Button Login;
    FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private admin_presenter presenter;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        presenter = new admin_presenter(new admin_model(), this);
        admin_name = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        fAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.LoginButton);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkLogin();
            }
        });
    }

    public String getEmail(){return admin_name.getText().toString();}

    public void goToStudentPage(String userID) {

                Intent intent = new Intent(Admin_Login.this, Admin_view_course.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }

            public void displayError(String str) {
                Toast.makeText(Admin_Login.this, str, Toast.LENGTH_LONG).show();
            }

    public String getPw() { return password.getText().toString();}
}


