package com.example.demoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demoapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText reg_username,reg_paswword,reg_conf_paswword,reg_age,reg_dept,reg_roll_no,reg_email;
    Button register_submit;
    private static DatabaseReference databaseReference;
    private static DatabaseReference newChildRef;
    public static DatabaseReference firebaseLogin;
    public static final String USERNAME = "name";
    public static final String PASSWORD = "password";
    public static final String AGE = "age";
    public static final String DEPARTMENT = "dept";
    public static final String ROLL_NO = "rollNo";
    public static final String EMAIL = "email";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        clicks();
    }

    private void init(){
        reg_username = findViewById(R.id.reg_username);
        reg_paswword = findViewById(R.id.reg_paswword);
        reg_conf_paswword = findViewById(R.id.reg_conf_paswword);
        reg_age = findViewById(R.id.reg_age);
        reg_dept = findViewById(R.id.reg_dept);
        reg_roll_no = findViewById(R.id.reg_roll_no);
        register_submit = findViewById(R.id.register_submit);
        reg_email = findViewById(R.id.reg_email);
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentRegistration");

    }

    private void clicks(){
        register_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    saveData();
                }
            }
        });
    }

    private boolean validation(){
        if(reg_username.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter the Username", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_paswword.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter the Password", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_conf_paswword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_roll_no.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter Roll No", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_dept.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter The Department", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_age.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Age", Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Email", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!reg_email.getText().toString().matches(emailPattern)){
            Toast.makeText(this, "Enter proper Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!reg_paswword.getText().toString().equalsIgnoreCase(reg_conf_paswword.getText().toString())){
            Toast.makeText(this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }


    public void saveData(){
        try {
            Map<String,Object> data = new HashMap<String, Object>();
            data.put(USERNAME,reg_username.getText().toString());
            data.put(PASSWORD,reg_paswword.getText().toString());
            data.put(AGE,reg_age.getText().toString());
            data.put(DEPARTMENT,reg_dept.getText().toString());
            data.put(ROLL_NO,reg_roll_no.getText().toString());
            data.put(EMAIL,reg_email.getText().toString());
            newChildRef = databaseReference.push();
            String Key =newChildRef.getKey();
            databaseReference.child(Key).setValue(data);
            Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class).putExtra("role","student"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
