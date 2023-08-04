package com.example.b07tut7grp3;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07tut7grp3.ui.login.Presenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Presenter presenter;

    TextView createnewAccount;
    TextView adminLogin;
    EditText inputEmail,inputPassword;
    Button LoginButton;
    String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button checkUser;
    Button checkPassword;
    //please make sure to enable email/password authentication on Firebase!!

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        LoginButton=findViewById(R.id.LoginButton);
        adminLogin = findViewById(R.id.adminLogin);
        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        createnewAccount=findViewById(R.id.createnewAccount);

        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();
            }
        });
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToAdminActivity();
            }

        });
    }

    private void PerformLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if(!email.matches(emailPattern))
        {
            inputEmail.setError("Please enter valid email address");
        }else if(password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Please enter proper password");
        }else
        {
            progressDialog.setMessage("Logging to the account...Please wait");
            progressDialog.setTitle("Logging");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        dataSave();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void dataSave() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", mUser.getUid());
        editor.commit();
    }
    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this, StudentHomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sendUserToAdminActivity() {
        Intent intent=new Intent(MainActivity.this, com.example.admintoolsUTSC.Admin_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    
    public void displayMessage(String message) {

    }

    public String getUsername() {
        EditText editText = findViewById(R.id.inputEmail);
        return editText.getText().toString();
    }

    public String getPassword() {
        EditText editText = findViewById(R.id.inputPassword);
        return editText.getText().toString();
    }

    public void handleClick(View view){
        presenter.checkUsername();
        presenter.checkPassword();
    }
}