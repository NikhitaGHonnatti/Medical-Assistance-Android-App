package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_Prescriptions extends AppCompatActivity {

    ListView lvAllPres;
    DatabaseReference DBPres;
    ArrayList<Prescription> pres = new ArrayList<Prescription>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__prescriptions);

        lvAllPres = findViewById(R.id.lvAllPres);
        final String pidIntent = getIntent().getStringExtra("pid");

        DBPres = FirebaseDatabase.getInstance().getReference("prescriptions");

        DBPres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pres.clear();
                for(DataSnapshot PresSS : dataSnapshot.getChildren()){
                    //String symp = PresSS.getValue(Prescription.class).getSymp();
                    String medname = PresSS.getValue(Prescription.class).getMedname();
                    int perDay = PresSS.getValue(Prescription.class).getPerDay();
                    int days = PresSS.getValue(Prescription.class).getDays();
                    String id = PresSS.getValue(Prescription.class).getPid();
                    String date = PresSS.getValue(Prescription.class).getDate();

                    if(id.equals(pidIntent)) {
                        Prescription p = new Prescription(date,medname,perDay,days,id,PresSS.getValue(Prescription.class).getPresid(),PresSS.getValue(Prescription.class).getPickedUp(),PresSS.getValue(Prescription.class).getRunsout(),PresSS.getValue(Prescription.class).getPudate());
                        pres.add(p);
                    }
                }
                PrescriptionList adapter = new PrescriptionList(All_Prescriptions.this,pres);
                lvAllPres.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(All_Prescriptions.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
