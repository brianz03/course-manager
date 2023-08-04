package com.example.b07tut7grp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentInfopage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView title;
    EditText first_name;
    EditText last_name;
    EditText currentyear;
    Spinner POStspinner;
    EditText email;
    Button getstarted;
    utscStudent student;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private SharedPreferences sharedPreferences;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_infopage);

        title=findViewById(R.id.infopagetitle);
        first_name=findViewById(R.id.firstname);
        last_name=findViewById(R.id.lastname);
        currentyear=findViewById(R.id.currentyear);

        POStspinner=findViewById(R.id.POStspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.POSt_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        POStspinner.setAdapter(adapter);
        POStspinner.setOnItemSelectedListener(this);

        email=findViewById(R.id.email);

        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref",
                Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("user","");

        getstarted=findViewById(R.id.button);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FirstName=first_name.getText().toString();
                String LastName=last_name.getText().toString();
                String Email=email.getText().toString();
                String userID=userid;
                String Currentyear = currentyear.getText().toString();
                if (Currentyear != null && !Currentyear.equals("")){
                    Currentyear = currentyear.getText().toString();
                }else{
                    Currentyear = "1";
                }
                int Year=Integer.parseInt(Currentyear);

                String POStname=POStspinner.getSelectedItem().toString();
                Subject currentpost=Subject.getProgram(POStname);

                student = new utscStudent(FirstName, LastName, Year,
                        currentpost, Email, userID);
                dataSave();
                sendUserToNextActivity();
            }
        });

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(StudentInfopage.this, StudentHomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void dataSave() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", mUser.getUid());
        editor.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}