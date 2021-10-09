package com.example.demoapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.demoapp.Fragments.DocumentFragment;
import com.example.demoapp.Fragments.FormsFragments;
import com.example.demoapp.Fragments.HelpFragment;
import com.example.demoapp.Fragments.ProfileFragment;
import com.example.demoapp.Fragments.Student_profile;
import com.example.demoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentDashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ActionBar actionBar;
    private String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    String READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    String WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    int CAMERA_REQUEST;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        init();
        menu();
    }


    private void init(){
        bottomNavigationView = findViewById(R.id.student_bottom_menu);
        frameLayout = findViewById(R.id.student_frame_layout);
        actionBar = getSupportActionBar();
        loadFragments(new Student_profile());
        if(!hasPermissions(this,PERMISSIONS)){
            checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS,StudentDashboard.this);
        }
    }

    private void menu(){
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.student_documents:
                        fragment = new DocumentFragment();
                        loadFragments(fragment);
                        return true;
                    case R.id.student_profile:
                        fragment = new Student_profile();
                        loadFragments(fragment);
                        return true;
                    case R.id.student_forms:
                        fragment = new FormsFragments();
                        loadFragments(fragment);
                        return true;

                }
                return false;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void loadFragments(Fragment fr){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.student_frame_layout,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void checkMultiplePermissions(int permissionCode, Context context) {

        String[] PERMISSIONS = {CAMERA_PERMISSION,READ_EXTERNAL_STORAGE_PERMISSION,WRITE_EXTERNAL_STORAGE_PERMISSION};
        if (!hasPermissions(context,PERMISSIONS)) {
            ActivityCompat.requestPermissions(StudentDashboard.this,PERMISSIONS,permissionCode);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAMERA_REQUEST);
            // Open your camera here.
        }
    }

    String[] PERMISSIONS = {CAMERA_PERMISSION,READ_EXTERNAL_STORAGE_PERMISSION,WRITE_EXTERNAL_STORAGE_PERMISSION};
    private boolean hasPermissions(android.content.Context context,String[] permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentDashboard.this,UserActivity.class));
        finish();
    }
}
