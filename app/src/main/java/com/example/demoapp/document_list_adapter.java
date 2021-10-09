package com.example.demoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Models.DocumentListModel;

import java.util.ArrayList;

public class document_list_adapter extends RecyclerView.Adapter<document_list_adapter.MyViewHolder> {

    Context context;
    ArrayList<DocumentListModel> documentListModel;

    public document_list_adapter(Context context, ArrayList<DocumentListModel> documentListModel) {
        this.context = context;
        this.documentListModel = documentListModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.document_list_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DocumentListModel model = documentListModel.get(position);
        holder.document_tv_name.setText(model.getName());
        holder.document_tv_roll_no.setText(model.getID());
        holder.imageView_id.setImageBitmap(convertBase64(model.getRoll_no()));
        holder.imageView_certificate.setImageBitmap(convertBase64(model.getCertificate()));


    }

    @Override
    public int getItemCount() {
        return documentListModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView document_tv_name,document_tv_roll_no;
        ImageView imageView_certificate,imageView_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            document_tv_name = itemView.findViewById(R.id.document_tv_name);
            document_tv_roll_no = itemView.findViewById(R.id.document_tv_roll_no);
            imageView_certificate = itemView.findViewById(R.id.imageView_certificate);
            imageView_id = itemView.findViewById(R.id.imageView_id);
        }
    }

    private Bitmap convertBase64(String base64){
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
