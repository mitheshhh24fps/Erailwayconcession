package com.example.demoapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demoapp.Activities.LoginActivity;
import com.example.demoapp.Activities.RegistrationActivity;
import com.example.demoapp.CustomDialog;
import com.example.demoapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FormsFragments extends Fragment {

    private TextInputEditText form_name, form_roll_no, form_faculty, form_source, form_dest, form_id_card,form_email;
    private Button form_submit;
    private static DatabaseReference databaseReference;
    private static DatabaseReference newChildRef;
    public static DatabaseReference firebaseLogin;
    public static final String FORM_NAME = "form_name";
    public static final String FORM_ROLL_NO = "form_roll_no";
    public static final String FORM_FACULTY = "form_faculty";
    public static final String FORM_SOURCE = "form_source";
    public static final String FORM_DEST = "form_dest";
    public static final String FORM_ID_CARD= "form_id";
    public static final String EMAIL_ID = "email";
    CustomDialog customDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forms_layout, container, false);
        form_name = view.findViewById(R.id.form_name);
        form_roll_no = view.findViewById(R.id.form_roll_no);
        form_faculty = view.findViewById(R.id.form_faculty);
        form_source = view.findViewById(R.id.form_source);
        form_dest = view.findViewById(R.id.form_dest);
        form_id_card = view.findViewById(R.id.form_id_card);
        form_submit = view.findViewById(R.id.form_submit);
        form_email = view.findViewById(R.id.form_email);
        databaseReference = FirebaseDatabase.getInstance().getReference("SUBMITFORM");
        customDialog = new CustomDialog(getContext());
        customDialog.setCancelable(false);
        clicks();
        setData();
        return view;
    }

    private boolean validation() {
        customDialog.show();
        if (form_name.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter the Username", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_roll_no.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter the Password", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_faculty.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_source.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter Roll No", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_dest.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter The Department", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_id_card.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter your Age", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else if (form_email.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter Email Id", Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
            return false;
        } else {
            return true;
        }
    }

    private void clicks(){
        form_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    saveData();
                }
            }
        });

    }

    public void clearAll(){
        form_name.setText("");
        form_roll_no.setText("");
        form_faculty.setText("");
        form_source.setText("");
        form_dest.setText("");
        form_id_card.setText("");
        form_email.setText("");
    }
    public void setData(){
        try {
            form_name.setText(LoginActivity.Name);
            form_roll_no.setText(LoginActivity.roll_no);
            form_faculty.setText(LoginActivity.faculty);
            form_id_card.setText(LoginActivity.id);
            form_email.setText(LoginActivity.email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveData(){
        try {
            Map<String,Object> data = new HashMap<String, Object>();
            data.put(FORM_NAME,form_name.getText().toString());
            data.put(FORM_ROLL_NO,form_roll_no.getText().toString());
            data.put(FORM_FACULTY,form_faculty.getText().toString());
            data.put(FORM_SOURCE,form_source.getText().toString());
            data.put(FORM_DEST,form_dest.getText().toString());
            data.put(FORM_ID_CARD,form_id_card.getText().toString());
            data.put(EMAIL_ID,form_email.getText().toString());
            newChildRef = databaseReference.push();
            String Key =newChildRef.getKey();
            databaseReference.child(Key).setValue(data);
            customDialog.dismiss();
            clearAll();
            Toast.makeText(getContext(), "Form submitted successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
