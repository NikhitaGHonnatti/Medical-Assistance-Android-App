package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class My_Prescriptions extends AppCompatActivity {

    ListView lvMyPres;
    DatabaseReference DBPres, DBPU;
    ArrayList<Prescription> pres = new ArrayList<Prescription>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__prescriptions);
        createNotificationChannel();
        lvMyPres = findViewById(R.id.lvMyPres);
        final String pid = getIntent().getStringExtra("pid");

        DBPres = FirebaseDatabase.getInstance().getReference("prescriptions");

        DBPres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pres.clear();
                for(DataSnapshot PresSS : dataSnapshot.getChildren()){
                    String date = PresSS.getValue(Prescription.class).getDate();
                    //String symp = PresSS.getValue(Prescription.class).getSymp();
                    String medname = PresSS.getValue(Prescription.class).getMedname();
                    int perDay = PresSS.getValue(Prescription.class).getPerDay();
                    int days = PresSS.getValue(Prescription.class).getDays();
                    String id = PresSS.getValue(Prescription.class).getPid();
                    if(id.equals(pid)) {
                        Prescription p = new Prescription(date,medname,perDay,days,id,PresSS.getValue(Prescription.class).getPresid(),PresSS.getValue(Prescription.class).getPickedUp(),PresSS.getValue(Prescription.class).getRunsout(),PresSS.getValue(Prescription.class).getPudate());
                        pres.add(p);
                    }
                }
                PatientPrescriptionList adapter = new PatientPrescriptionList(My_Prescriptions.this,pres);
                lvMyPres.setAdapter(adapter);
                lvMyPres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Intent i = new Intent(My_Prescriptions.this, PatientDetails.class);
                        Prescription pclicked = (Prescription) pres.get(position);
                        String presid = pclicked.getPresid();
                        Calendar calendar = Calendar.getInstance();
                        String date = DateFormat.getDateInstance().format(calendar.getTime());
                        pclicked.setPickedUp("true");
                        pclicked.setPudate(date);
                        calendar.add(Calendar.DAY_OF_YEAR,pclicked.getDays());
                        String runoutdate = DateFormat.getDateInstance().format(calendar.getTime());
                        pclicked.setRunsout(runoutdate);
                        DBPU = FirebaseDatabase.getInstance().getReference("prescriptions");
                        DBPU.child(pclicked.getPresid()).removeValue();
                        DBPU.child(pclicked.getPresid()).setValue(pclicked);
                        Intent pi = new Intent(My_Prescriptions.this, RunsOutReminder.class);
                        PendingIntent rurpi = PendingIntent.getBroadcast(My_Prescriptions.this,0,pi,0);
                        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

                        Calendar rur = Calendar.getInstance();
                        rur.add(Calendar.DAY_OF_YEAR,(pclicked.getDays()-2));
                        am.set(AlarmManager.RTC_WAKEUP,rur.getTimeInMillis()+10000,rurpi);


                    }});

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(My_Prescriptions.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

        }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            CharSequence name = "Medapp Reminder Channel";
            String description = "Reminds you when your meds are about to run out";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel nfc = new NotificationChannel("rur",name,importance);
            nfc.setDescription(description);

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(nfc);


        }
    }
}
