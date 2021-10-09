package com.example.demoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.demoapp.Activities.FinalSubmit;
import com.example.demoapp.Activities.FinishActivity;
import com.example.demoapp.Models.FormModel;
import com.example.demoapp.Models.Model;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    Context context;
    ArrayList<FormModel> models;
    CustomDialog customDialog;

    public AdapterClass(Context context, ArrayList<FormModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public AdapterClass.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view,parent,false);
        customDialog = new CustomDialog(context);
        customDialog.setCancelable(false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.MyViewHolder holder, int position) {
        customDialog.show();
        FormModel formModel = models.get(position);
        holder.tv_name.setText(formModel.getForm_name());
        holder.tv_faculty.setText(formModel.getForm_faculty());
        holder.tv_roll_no.setText(formModel.getForm_roll_no());
        holder.tv_age.setText(formModel.getForm_id());
        holder.tv_email.setText(formModel.getForm_name());
        holder.tv_student_faculty.setText(formModel.getForm_faculty());
        customDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void removeItem(int position) {
        models.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_roll_no,tv_age,tv_email,tv_faculty,tv_student_faculty,more_details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_student_name);
            tv_email = itemView.findViewById(R.id.tv_student_email);
            tv_age = itemView.findViewById(R.id.tv_student_age);
            tv_faculty = itemView.findViewById(R.id.student_faculty);
            tv_student_faculty = itemView.findViewById(R.id.tv_student_faculty);
            tv_roll_no = itemView.findViewById(R.id.tv_student_roll_no);
            more_details = itemView.findViewById(R.id.more_details);
            final FoldingCell foldingCell = itemView.findViewById(R.id.folding_cell);

            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foldingCell.toggle(false);
                }
            });

            more_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, FinalSubmit.class).putExtra("usernam",tv_name.getText()).putExtra("roll_no",tv_roll_no.getText()));
                }
            });
        }
    }
}
