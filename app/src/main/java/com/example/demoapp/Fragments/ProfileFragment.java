package com.example.demoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.AdapterClass;
import com.example.demoapp.CustomDialog;
import com.example.demoapp.Models.FormModel;
import com.example.demoapp.Models.Model;
import com.example.demoapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterClass adapterClass;
    public static DatabaseReference firebaseLogin;
    ArrayList<Model> models = new ArrayList<>();
    ArrayList<FormModel> formModels;
    FormModel formModel;
    CustomDialog customDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout,container,false);
        customDialog = new CustomDialog(getContext());
        customDialog.setCancelable(false);
        getValues(view);
        return view;
    }

    private void getValues(final View view){
        customDialog = new CustomDialog(getContext());
        customDialog.setCancelable(false);
        customDialog.show();
        firebaseLogin = FirebaseDatabase.getInstance().getReference("SUBMITFORM");
        formModels = new ArrayList<>();
        firebaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    formModels.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        formModel = new FormModel(snapshot.getValue(FormModel.class).getForm_name(),
                                snapshot.getValue(FormModel.class).getForm_roll_no()
                        ,snapshot.getValue(FormModel.class).getForm_faculty(),
                                snapshot.getValue(FormModel.class).getForm_dest(),
                                snapshot.getValue(FormModel.class).getForm_source(),snapshot.getValue(FormModel.class).getForm_id(),
                                snapshot.getValue(FormModel.class).getEmail());

                        formModels.add(formModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recyclerView = view.findViewById(R.id.profilr_recyler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapterClass = new AdapterClass(getContext(),formModels);
                recyclerView.setAdapter(adapterClass);
                customDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
