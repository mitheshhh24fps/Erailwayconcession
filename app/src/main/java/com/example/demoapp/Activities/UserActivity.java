package com.example.demoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;

import com.example.demoapp.R;

public class UserActivity extends AppCompatActivity {

    ImageView admin_image,student_image;
    Button identity_submit;
    LinearLayout admin_layout,student_layout;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
        clicks();
        
    }

    private void init(){
        admin_image = findViewById(R.id.admin_image);
        student_image = findViewById(R.id.student_image);
        identity_submit = findViewById(R.id.identity_submit);
        admin_layout = findViewById(R.id.admin_layout);
        student_layout = findViewById(R.id.student_layout);
    }

    private void clicks(){
        admin_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              admin_image.setBackgroundColor(Color.YELLOW);
              student_image.setBackgroundColor(Color.WHITE);
              role = "admin";
            }
        });

        student_image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                student_image.setBackgroundColor(Color.YELLOW);
                admin_image.setBackgroundColor(Color.WHITE);
                role = "student";
            }
        });

        identity_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this,LoginActivity.class).putExtra("role",role)) ;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
