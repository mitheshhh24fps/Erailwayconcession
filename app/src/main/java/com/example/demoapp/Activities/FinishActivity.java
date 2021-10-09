package com.example.demoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.demoapp.R;

public class FinishActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    Button btn_dashboard;
    public static String finish = "finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        lottieAnimationView = findViewById(R.id.lottie);
        btn_dashboard = findViewById(R.id.btn_dashboard);

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FinishActivity.this,AdminDashboard.class));
                finish();
            }
        });
    }
}
