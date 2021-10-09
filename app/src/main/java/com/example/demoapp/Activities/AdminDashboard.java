package com.example.demoapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.demoapp.Fragments.FormsFragments;
import com.example.demoapp.Fragments.HelpFragment;
import com.example.demoapp.Fragments.ListOfDocuments;
import com.example.demoapp.Fragments.ProfileFragment;
import com.example.demoapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminDashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        init();
        menu();
    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottom_menu);
        frameLayout = findViewById(R.id.frame_layout);
        actionBar = getSupportActionBar();
        loadFragments(new ProfileFragment());
          }

    private void menu(){
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.help:
                        fragment = new HelpFragment();
                        loadFragments(fragment);
                        return true;
                    case R.id.forms:
                        fragment = new ProfileFragment();
                        loadFragments(fragment);
                        return true;
                    case R.id.documents:
                        fragment = new ListOfDocuments();
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
        fragmentTransaction.replace(R.id.frame_layout,fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminDashboard.this,UserActivity.class));
        finish();
        super.onBackPressed();

    }
}
