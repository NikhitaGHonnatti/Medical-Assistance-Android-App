package com.example.testing.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptData extends AppCompatActivity {

    TextView ID;
    EditText etName, etAge, etWeight, etHeight,symp;
    Button btnSubmit;
    Spinner spGender;
    DatabaseReference DBPatients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_data);
        final String pid = getIntent().getStringExtra("pid");
        ID=findViewById(R.id.etpid);
        ID.setText(pid);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnSubmit = findViewById(R.id.btnAddPatient);
        spGender = findViewById(R.id.spGender);
        symp = findViewById(R.id.etsymptoms);
        //spType = findViewById(R.id.spType);
        etWeight = findViewById(R.id.etWeight);
        //btnViewPatients = findViewById(R.id.btnViewPatients);
        etHeight = findViewById(R.id.etHeight);
        DBPatients = FirebaseDatabase.getInstance().getReference("patients");
        //DBdoctor = FirebaseDatabase.getInstance().getReference("");// ADD DR USER NAME
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatient();
            }

        });
    }

    private void addPatient() {
        String name = etName.getText().toString().trim();
        float age = Float.parseFloat(etAge.getText().toString());
        String pid = ID.getText().toString();
        String gender = spGender.getSelectedItem().toString();
        String symptoms = symp.getText().toString().trim();
        //String height = spType.getSelectedItem().toString();
        float height = Float.parseFloat( etHeight.getText().toString());
        float weight = Float.parseFloat( etWeight.getText().toString());
        //String pd = pid.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(etAge.getText().toString()) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(etHeight.getText().toString())||TextUtils.isEmpty(etWeight.getText().toString())) {
            Toast.makeText(AcceptData.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        }
        else
        {

            String id = DBPatients.push().getKey();
            Patient p = new Patient(name,id,pid,gender,symptoms,age,height,weight,"Dr Roopa");
            if(DBPatients.child(id).setValue(p).isSuccessful()) {//PASS DR NAME
                Toast.makeText(AcceptData.this, "Patient Details Uploaded", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(AcceptData.this,"Patient Added Successfully",Toast.LENGTH_LONG).show();

        }
    }
}