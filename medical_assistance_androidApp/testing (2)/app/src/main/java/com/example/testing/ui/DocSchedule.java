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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DocSchedule extends AppCompatActivity {

    ListView lvDocSche;
    DatabaseReference DBSlots, DBBooked;
    ArrayList<Slot> slots = new ArrayList<Slot>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_schedule);

        lvDocSche = findViewById(R.id.lvDocSch);
        //Intent iac = getIntent();
        //final String pid = (String) iac.getSerializableExtra("pid");
        //String id = p.getId();
        //final String pid = getIntent().getStringExtra("pid");

        DBSlots = FirebaseDatabase.getInstance().getReference("Dr Roopa Appointments");

        DBSlots.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                slots.clear();
                Calendar calendar = Calendar.getInstance();
                final String date = DateFormat.getDateInstance().format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                final String tomorrow = DateFormat.getDateInstance().format(calendar.getTime());

                for (DataSnapshot SlotSS : dataSnapshot.getChildren()) {
                    String day = SlotSS.getValue(Slot.class).getDay();
                    if (day.equals(date) || day.equals(tomorrow)) {
                        String timing = SlotSS.getValue(Slot.class).getTiming();
                        String id = SlotSS.getValue(Slot.class).getId();
                        String sid = SlotSS.getValue(Slot.class).getSid();
                        String name = SlotSS.getValue(Slot.class).getName();
                        //String sid = SlotSS.getValue(Slot.class).getSid();
                        int no = SlotSS.getValue(Slot.class).getNo();
                        if (!(id.equals("N/A"))) {
                            Slot s = new Slot(timing, no, day, sid);
                            s.setName(name);
                            s.setId(id);
                            slots.add(s);
                        }
                    }
                }
                DocSlots adapter = new DocSlots(DocSchedule.this, slots);
                lvDocSche.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DocSchedule.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
