package com.example.demoapp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.demoapp.Activities.LoginActivity;
import com.example.demoapp.CustomDialog;
import com.example.demoapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DocumentFragment extends Fragment {
    LinearLayout id_layout,certificate_layout,cast_layout;
    ImageView id_imageview,certificare_imageview,cast_imageview;
    int CAMERA_REQUEST;
    String image_type;
    Button upload;
    private static DatabaseReference databaseReference;
    private static DatabaseReference newChildRef;
    public static final String BASE64_ID = "ID";
    public static final String BASE64_CERTIFICATE = "Certificate";
    public static final String BASE64_CAST_CERTIFICATE = "Cast_Certificate";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_ROLL_No = "roll_no";
    String id_base,cert_base;
    CustomDialog customDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_layout,container,false);
        id_layout = view.findViewById(R.id.id_layout);
        certificate_layout = view.findViewById(R.id.certificate_layout);
        cast_layout = view.findViewById(R.id.cast_layout);
        cast_imageview = view.findViewById(R.id.cast_imageview);
        id_imageview = view.findViewById(R.id.id_imageview);
        certificare_imageview = view.findViewById(R.id.certificare_imageview);
        upload = view.findViewById(R.id.upload);
        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");
        customDialog = new CustomDialog(getContext());
        customDialog.setCancelable(false);
        clicks();
        return view;
    }


    private void clicks(){
        id_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Open your camera here.
                    image_type = "id";
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);

            }
        });

        certificate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Open your camera here.
                    image_type = "certificate";
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);

            }
        });

        cast_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_type = "castcertificate";
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.show();
                uploadDocuments();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST  && resultCode == Activity.RESULT_OK) {
            final Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if(image_type.equalsIgnoreCase("id")){
                id_imageview.setImageBitmap(photo);
                convertToString(photo);
            }else if(image_type.equalsIgnoreCase("certificate")){
                certificare_imageview.setImageBitmap(photo);
                convertToString(photo);
            }else{
                cast_imageview.setImageBitmap(photo);
                convertToString(photo);
            }

        }
    }

    private void convertToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        if(image_type.equalsIgnoreCase("id")){
            id_base = encoded;
        }else if(image_type.equalsIgnoreCase("certificate")){
            cert_base = encoded;
        }else{
            cert_base = encoded;
        }

    }

    private void uploadDocuments(){
        try {

            Map<String,Object> data = new HashMap<String, Object>();
            data.put(BASE64_ID,id_base);
            data.put(BASE64_CERTIFICATE,cert_base);
            data.put(BASE64_CAST_CERTIFICATE,cert_base);
            data.put(STUDENT_NAME, LoginActivity.user_name);
            data.put(STUDENT_ROLL_No,LoginActivity.roll_no);
            newChildRef = databaseReference.push();
            String key = newChildRef.getKey();
            databaseReference.child(key).setValue(data);
            customDialog.dismiss();
            Toast.makeText(getContext(), "Documents Uploaded Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
