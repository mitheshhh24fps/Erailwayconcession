package com.example.demoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.AppConstant;
import com.example.SharedPereference;
import com.example.demoapp.Activities.LoginActivity;
import com.example.demoapp.Models.LoginModel;
import com.example.demoapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Student_profile extends Fragment {

    private ImageView id_card_image,image_documents;
    private TextView profile_username_tv,profile_id_no_tv,profile_student_name,profile_student_faculty,profile_student_age,profile_student_rollNo;
    public static DatabaseReference firebaseLogin;
    private ArrayList<LoginModel> loginModelsList;
    public static LoginModel loginModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profile,container,false);
        init(view);
        getData();
        return view;
    }

    private void init(View view){
        id_card_image = view.findViewById(R.id.id_card_image);
        image_documents = view.findViewById(R.id.image_documents);
        profile_student_name = view.findViewById(R.id.profile_student_name);
        profile_student_age = view.findViewById(R.id.profile_student_age);
        profile_student_rollNo = view.findViewById(R.id.profile_student_rollNo);
        profile_student_faculty = view.findViewById(R.id.profile_student_faculty);
        firebaseLogin = FirebaseDatabase.getInstance().getReference("Registration");
    }

    private void getData(){
        profile_student_name.setText("Student Name :" + LoginActivity.Name);
        profile_student_age.setText("Student Age :" +LoginActivity.age);
        profile_student_rollNo.setText("Student RollNo :"+LoginActivity.rollNo);
        profile_student_faculty.setText("Student Faculty :"+LoginActivity.faculty);
    }

}
