package com.example.demoapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.fonts.Font;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demoapp.AdapterClass;
import com.example.demoapp.CustomDialog;
import com.example.demoapp.Models.FormModel;
import com.example.demoapp.Models.Model;
import com.example.demoapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FinalSubmit extends AppCompatActivity {

    private TextInputEditText final_form_name, final_form_roll_no, final_form_faculty, final_form_source, final_form_dest, final_form_id_card,final_form_email;
    private Button final_form_submit;
    CustomDialog customDialog;
    public static DatabaseReference firebaseLogin;
    ArrayList<Model> models = new ArrayList<>();
    ArrayList<FormModel> formModels;
    FormModel formModel;
    Bundle bundle;
    public static final int REQUEST_WRITE_STORAGE = 112;
    String REQUEST_SEND_MAIL = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_submit);
        requestPermission();
        init();
        getValues();
    }

    private void init() {
        final_form_name = findViewById(R.id.final_form_name);
        final_form_roll_no = findViewById(R.id.final_form_roll_no);
        final_form_faculty = findViewById(R.id.final_form_faculty);
        final_form_source = findViewById(R.id.final_form_source);
        final_form_dest = findViewById(R.id.final_form_dest);
        final_form_id_card = findViewById(R.id.final_form_id_card);
        final_form_email = findViewById(R.id.final_form_email);
        final_form_submit = findViewById(R.id.final_form_submit);

        final_form_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.show();
                createandDisplayPdf();

            }
        });
    }

    private void getValues() {
        bundle = getIntent().getExtras();
        final String username = bundle.getString("usernam");
        final String roll_no = bundle.getString("roll_no");
        customDialog = new CustomDialog(this);
        customDialog.setCancelable(false);
        customDialog.show();
        firebaseLogin = FirebaseDatabase.getInstance().getReference("SUBMITFORM");
        formModels = new ArrayList<>();
        firebaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    formModels.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        formModel = new FormModel(snapshot.getValue(FormModel.class).getForm_name(),
                                snapshot.getValue(FormModel.class).getForm_roll_no()
                                ,snapshot.getValue(FormModel.class).getForm_faculty(),
                                snapshot.getValue(FormModel.class).getForm_dest(),
                                snapshot.getValue(FormModel.class).getForm_source(),
                                snapshot.getValue(FormModel.class).getForm_id(),
                                snapshot.getValue(FormModel.class).getEmail());

                        formModels.add(formModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < formModels.size(); i++) {
                    if (username.equalsIgnoreCase(formModels.get(i).getForm_name()) && roll_no.equalsIgnoreCase(formModels.get(i).getForm_roll_no())) {
                        final_form_name.setText(formModels.get(i).getForm_name());
                        final_form_source.setText(formModels.get(i).getForm_source());
                        final_form_dest.setText(formModels.get(i).getForm_dest());
                        final_form_roll_no.setText(formModels.get(i).getForm_roll_no());
                        final_form_faculty.setText(formModels.get(i).getForm_faculty());
                        final_form_id_card.setText(formModels.get(i).getForm_id());
                        final_form_email.setText(formModels.get(i).getEmail());
                    }
                }
                customDialog.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void requestPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        document.add(new Paragraph("Student Name : " +final_form_name.getText().toString()));
        document.add(new Paragraph("Source : " +final_form_source.getText().toString()));
        document.add(new Paragraph("Destination : " +final_form_dest.getText().toString()));
        document.add(new Paragraph("Student Id : " +final_form_id_card.getText().toString()));
        document.add(new Paragraph("Student Roll No: " +final_form_roll_no.getText().toString()));
        document.add(new Paragraph("Student Faculty : " +final_form_faculty.getText().toString()));
        document.close();
        customDialog.dismiss();
    }

    public void createandDisplayPdf() {

        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
            String text= ("\n" +
                    "Western railways \n" +
                    "School certificate to be issued only to ******** than 25 years of age\n" +
                    "\n" +
                    "I hereby certify that * -------------------------------- regularly attend he’s attend school for the purpose of receiving education, the institution of which I am the Principal/Head Master and his/her age this day, according to my belief and form enquires I have made, is ------------------ years----------months,                                                                                  his/her date of birth as entered in the school register being-------------------  he/she is therefore, entitled to the season ticket as detailed below at half the full rate charged for adult");
            String text2 = "the student at present holds_________ class season ticket no. __________\n" +
                    "From_________________________ to ______________________________\n" +
                    "For the half year/quarter/month ending.\n" +
                    "\n" +
                    "Date___________________\t\t\t\t\tsignature of principal/Headmaster\n" +
                    "Name of the college/school (School name stamp)___________________ Locality\n" +
                    "*Enter the name of the student in full.\n" +
                    "*available only between station nearest to student’s residence and station nearest school.\n" +
                    "*this column should be filled in by the station issuing the season ticket.\n" +
                    "*if no season ticket is held the word ‘NIL’ should be inserted.\n" +
                    "\n" +
                    "Note: - this certificate will be valid for three days including the date of issue and if not made use of          \n" +
                    "            Within that time must be returned by the issued for cancellation.";
            Phrase elements = new Phrase(text);
            Phrase elements1 = new Phrase(text2);
            Paragraph p1 = new Paragraph();
            p1.add(elements);
            Paragraph line = new Paragraph("----------------------------------------------------------------------------------------------");
            Paragraph p2 = new Paragraph("Student Name : " + final_form_name.getText().toString());
            Paragraph p3 = new Paragraph("Source : " + final_form_source.getText().toString());
            Paragraph p4 = new Paragraph("Destination : " + final_form_dest.getText().toString() );
            Paragraph p5 = new Paragraph("Roll No : " + final_form_roll_no.getText().toString());
            Paragraph p6 = new Paragraph("Faculty : " + final_form_faculty.getText().toString());
            Paragraph p7 = new Paragraph("ID Card : " + final_form_id_card.getText().toString());
            Paragraph line2 = new Paragraph("----------------------------------------------------------------------------------------------");
            Paragraph p8 = new Paragraph();
            p8.add(elements1);

            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            p5.setAlignment(Paragraph.ALIGN_CENTER);
            p6.setAlignment(Paragraph.ALIGN_CENTER);
            p7.setAlignment(Paragraph.ALIGN_CENTER);
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            line.setAlignment(Paragraph.ALIGN_CENTER);
            line2.setAlignment(Paragraph.ALIGN_CENTER);

            //add paragraph to document
            doc.add(p1);
            doc.add(line);
            doc.add(p2);
            doc.add(p3);
            doc.add(p4);
            doc.add(p5);
            doc.add(p6);
            doc.add(p7);
            doc.add(line2);
            doc.add(p8);



        } catch (DocumentException de) {
            customDialog.dismiss();
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            customDialog.dismiss();
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            customDialog.dismiss();
            doc.close();
            ShareViaEmail("Dir","newFile.pdf");
           // viewPdf("newFile.pdf","Dir");
        }

        //viewPdf("newFile.pdf", "Dir");
    }

    private void viewPdf(String file, String directory) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent intent = Intent.createChooser(pdfIntent, "Open File");
        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
           // Toast.makeText(TableActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShareViaEmail(String directory, String file) {
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
            Uri path = Uri.fromFile(pdfFile);
            String to = final_form_email.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            String message="File to be shared is " + file + ".";
            intent.setType("vnd.android.cursor.dir/email");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_STREAM,path);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(Intent.createChooser(intent, "Send e-mail to"),0);
        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            startActivity(new Intent(FinalSubmit.this,FinishActivity.class));
            finish();
        }
    }
}
