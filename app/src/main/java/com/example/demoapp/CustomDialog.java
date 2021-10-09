package com.example.demoapp;

import android.app.Dialog;
import android.content.Context;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;

public class CustomDialog extends Dialog {
    private TextView tv_message, tv_header;

    public CustomDialog( Context context) {
        super(context);
        setContentView(R.layout.custom_dialog);
        setCancelable(true);
        initializeViews();
    }

    public CustomDialog( Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initializeViews() {
        tv_message = findViewById(R.id.tv_message);
        tv_header = findViewById(R.id.tv_header);
    }

    public void setMessage(String message) {
        tv_message.setText(message);
        // show();
    }

    public void setMessageDialog(String message) {
        tv_header.setVisibility(View.VISIBLE);
        tv_header.setText(message);
        // show();
    }
}
