package com.example.testing.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class PatientDetails extends AppCompatActivity {

    DatabaseReference DBComments;
    TextView  tvPID, tvName,tvAge, tvGender, tvWeight, tvHeight,tvsymp;
    EditText etComment;
    Button btnAddComment, btnAllComments, btnAddPres, btnAllPres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final Patient pclicked = (Patient) i.getSerializableExtra("Patient");
        setContentView(R.layout.activity_patient_details);
        //tvDID = findViewById(R.id.tvID);
        tvPID = findViewById(R.id.tvPID);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvWeight = findViewById(R.id.tvWeight);
        tvHeight = findViewById(R.id.tvHeight);
        tvName = findViewById(R.id.tvName);
        tvsymp = findViewById(R.id.tvSymptoms);
        etComment = findViewById(R.id.etComment);
        btnAddComment = findViewById(R.id.btnAddComment);
        btnAllComments = findViewById(R.id.btnAllComments);
        btnAddPres = findViewById(R.id.btnAddPres);
        btnAllPres = findViewById(R.id.btnAllPres);
        btnAddPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDetails.this, Add_Prescription.class);
                i.putExtra("pid", pclicked.getPid());
                startActivity(i);
            }
        });
        btnAllPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDetails.this, All_Prescriptions.class);
                i.putExtra("pid",pclicked.getPid());
                startActivity(i);
            }
        });


        DBComments = FirebaseDatabase.getInstance().getReference("comments");
        //DBPatients.child(pclicked.getId()).child("comment");
        //tvDID.setText("ID : " + pclicked.getId());
        tvPID.setText("PID : " + pclicked.getPid());
        tvName.setText("Name : " + pclicked.getName());
        tvHeight.setText("Height : " + pclicked.getHeight());
        tvWeight.setText("Weight : " + pclicked.getWeight());
        tvAge.setText("Age : " + pclicked.getAge());
        tvGender.setText("Gender : " + pclicked.getGender());
        tvsymp.setText("Symptoms : "+ pclicked.getSymptoms());


        btnAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iac = new Intent(PatientDetails.this, AllComments.class);
                iac.putExtra("pid",pclicked.getPid());
                startActivity(iac);
            }
        });

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etComment.getText().toString().trim();
                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance().format(calendar.getTime());
               // String time = (String) calendar.getTime();
                String id = DBComments.push().getKey();
                String uid = pclicked.getPid();
                 DocComment dc = new DocComment(comment,date,uid);
                //ArrayList<DocComment> dclist = new ArrayList<>();
                //dclist.add(dc);
                DBComments.child(id).setValue(dc);
                    Toast.makeText(PatientDetails.this,"Comment added successfully",Toast.LENGTH_SHORT).show();


            }
        });
    }
}
