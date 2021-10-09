package com.example.demoapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AppConstant;
import com.example.SharedPereference;
import com.example.demoapp.CustomDialog;
import com.example.demoapp.MainActivity;
import com.example.demoapp.Models.LoginModel;
import com.example.demoapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextView tv_registration;
    Button login_submit;
    Bundle bundle;
    public static DatabaseReference firebaseLogin;
    private ArrayList<LoginModel> loginModelsList;
    TextInputEditText et_userName,et_passWord;
    public static LoginModel loginModel;
    public boolean valid = false;
    public static String user_name,roll_no;
    SharedPereference sharedPereference;
    public static String  Name,age,rollNo,faculty,id,email;
    CustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        clicks();
    }

    private void init(){
        tv_registration = findViewById(R.id.tv_registration);
        login_submit = findViewById(R.id.login_submit);
        et_userName = findViewById(R.id.et_userName);
        et_passWord = findViewById(R.id.et_passWord);
        bundle = getIntent().getExtras();
        if(bundle.getString("role").equalsIgnoreCase("admin")){
            tv_registration.setVisibility(View.GONE);
        }else{
            tv_registration.setVisibility(View.VISIBLE);
        }
        customDialog = new CustomDialog(this);

    }

    private void clicks(){
        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.getString("role").equalsIgnoreCase("admin")){
                    startActivity(new Intent(LoginActivity.this,AdminDashboard.class));
                }else{
                    validateuser();
                }

            }
        });
    }

    private void validateuser(){
        customDialog.show();
        firebaseLogin = FirebaseDatabase.getInstance().getReference("StudentRegistration");
        final String userName = et_userName.getText().toString();
        final String passWord = et_passWord.getText().toString();
        loginModelsList = new ArrayList<>();
        firebaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        loginModelsList.clear();
                        loginModel = new LoginModel(dataSnapshot1.getValue(LoginModel.class).getName(),
                                dataSnapshot1.getValue(LoginModel.class).getPassword(),
                                dataSnapshot1.getValue(LoginModel.class).getAge(),
                                dataSnapshot1.getValue(LoginModel.class).getDept(),
                                dataSnapshot1.getValue(LoginModel.class).getRollNo(),
                                dataSnapshot1.getValue(LoginModel.class).getEmail(),
                                dataSnapshot1.getValue(LoginModel.class).getId());
                        loginModelsList.add(loginModel);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                for(int i = 0;i<loginModelsList.size();i++){
                    try{
                        String fire_userName = loginModelsList.get(i).getName();
                        String fire_password = loginModelsList.get(i).getPassword();
                        if(fire_userName.equalsIgnoreCase(userName) && fire_password.equalsIgnoreCase(passWord)){
                            Name = loginModelsList.get(i).getName();
                            age = loginModelsList.get(i).getAge();
                            rollNo = loginModelsList.get(i).getRollNo();
                            faculty = loginModelsList.get(i).getDept();
                            id = loginModelsList.get(i).getId();
                            email = loginModelsList.get(i).getEmail();
                            valid = true;
                            user_name = loginModelsList.get(i).getName();
                            roll_no = loginModelsList.get(i).getRollNo();
                            customDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this,StudentDashboard.class);
                            startActivity(intent);
                            finish();
                            break;
                        }else{
                            customDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Incorrect User", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        e.getStackTrace();
                    }
                }

                if(valid){
                   //Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
