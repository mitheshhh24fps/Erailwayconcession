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
import com.example.demoapp.Models.DocumentListModel;
import com.example.demoapp.Models.FormModel;
import com.example.demoapp.Models.Model;
import com.example.demoapp.R;
import com.example.demoapp.document_list_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfDocuments extends Fragment {
    RecyclerView recyclerView;
    document_list_adapter  list_adapter;
    CustomDialog customDialog;
    public static DatabaseReference firebaseLogin;
    ArrayList<DocumentListModel> formModels;
    DocumentListModel documentListModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.documents_list,container,false);
        getValues(view);
        return view;
    }
    private void getValues(final View view){
        customDialog = new CustomDialog(getContext());
        customDialog.setCancelable(false);
        customDialog.show();
        firebaseLogin = FirebaseDatabase.getInstance().getReference("Documents");
        formModels = new ArrayList<>();
        firebaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    formModels.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        documentListModel = new DocumentListModel(snapshot.getValue(DocumentListModel.class).getName(),
                                snapshot.getValue(DocumentListModel.class).getID(),
                                snapshot.getValue(DocumentListModel.class).getCertificate(),
                                snapshot.getValue(DocumentListModel.class).getRoll_no());

                        formModels.add(documentListModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recyclerView = view.findViewById(R.id.document_recyler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                list_adapter = new document_list_adapter(getContext(),formModels);
                recyclerView.setAdapter(list_adapter);
                customDialog.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
