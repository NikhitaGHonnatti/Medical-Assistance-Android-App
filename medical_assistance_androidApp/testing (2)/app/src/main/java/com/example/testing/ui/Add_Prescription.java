package com.example.testing.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class Add_Prescription extends AppCompatActivity {

    EditText etMedName, etPerDay, etDays;
    Button btnAddPrescription;
    DatabaseReference DBPres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__prescription);
        final String pid = getIntent().getStringExtra("pid");
        //final String symp = getIntent().getStringExtra("symp");
        etMedName = findViewById(R.id.etMedName);
        etPerDay = findViewById(R.id.etPerDay);
        etDays = findViewById(R.id.etDays);
        btnAddPrescription = findViewById(R.id.btnAddPrescription);
        DBPres = FirebaseDatabase.getInstance().getReference("prescriptions");

        btnAddPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medname = etMedName.getText().toString().trim();
                int days = Integer.parseInt(etDays.getText().toString().trim());
                int perday = Integer.parseInt(etPerDay.getText().toString().trim());
                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance().format(calendar.getTime());

                String id = DBPres.push().getKey();
                Prescription p = new Prescription(date,medname,perday,days,pid,id,"false","N/A","N/A");
                DBPres.child(id).setValue(p);
                Toast.makeText(Add_Prescription.this,"Prescription added successfully",Toast.LENGTH_SHORT).show();


            }
        });

    }
}
