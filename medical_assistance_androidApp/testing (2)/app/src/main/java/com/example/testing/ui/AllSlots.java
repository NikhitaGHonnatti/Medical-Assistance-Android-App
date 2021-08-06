package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AllSlots extends AppCompatActivity {

    ListView lvAllSlots;
    DatabaseReference DBSlots, DBBooked;
    ArrayList<Slot> slots = new ArrayList<Slot>();
    String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_slots);

            lvAllSlots = findViewById(R.id.lvAllSlots);
            Intent iac = getIntent();
            //final String pid = (String) iac.getSerializableExtra("pid");
            //String id = p.getId();
            pid = getIntent().getStringExtra("pid");
            //final String pname = getIntent().getStringExtra("pname");

            DBSlots = FirebaseDatabase.getInstance().getReference("Dr Roopa Appointments");

            DBSlots.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                    slots.clear();
                    Calendar calendar = Calendar.getInstance();
                    final String date = DateFormat.getDateInstance().format(calendar.getTime());
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    final String tomorrow = DateFormat.getDateInstance().format(calendar.getTime());

                    for(DataSnapshot SlotSS : dataSnapshot.getChildren()){
                        String day = SlotSS.getValue(Slot.class).getDay();
                        if(day.equals(date) || day.equals(tomorrow))
                        {
                        String timing = SlotSS.getValue(Slot.class).getTiming();
                        String id = SlotSS.getValue(Slot.class).getId();
                        String sid = SlotSS.getValue(Slot.class).getSid();
                        int no = SlotSS.getValue(Slot.class).getNo();
                        if(id.equals("N/A")){
                            Slot s = new Slot(timing,no,day,sid);
                            slots.add(s);
                        }}
                    }
                    SlotList adapter = new SlotList(AllSlots.this,slots);
                    lvAllSlots.setAdapter(adapter);
                    lvAllSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Intent i = new Intent(Display_Patients.this, PatientDetails.class);
                            Slot sclicked = (Slot)slots.get(position);
                            //sclicked.setId(pid);
                            Toast.makeText(AllSlots.this,"Slot Booked!", Toast.LENGTH_SHORT).show();

                            DBBooked = FirebaseDatabase.getInstance().getReference("Dr Roopa Appointments/"+sclicked.getSid()+"/id");
                            DBBooked.setValue(pid);
                            //DBBooked = FirebaseDatabase.getInstance().getReference("Dr Saroyan Appointments/"+sclicked.getSid()+"/name");
                            //DBBooked.setValue(pname);
                            //i.putExtra("Patient",pclicked);
                            //startActivity(i);

                        }});
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AllSlots.this, "ERROR!", Toast.LENGTH_SHORT).show();
                }

            });
    }
}
