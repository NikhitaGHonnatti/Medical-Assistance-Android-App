package com.example.testing.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SlotList extends ArrayAdapter<Slot> {
    Activity context;
    List<Slot> slots;

    public SlotList (Activity context, ArrayList<Slot> slots)
    {
        super(context, R.layout.lv_slot_item,slots);
        this.context = context;
        this.slots = slots;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.lv_slot_item,null,true);
        TextView tvSlotNo = (TextView) v.findViewById(R.id.tvSlotNo);
        TextView tvSlotTiming = (TextView)v.findViewById(R.id.tvSlotTiming);
        TextView tvPtID = (TextView)v.findViewById(R.id.tvPtId);
       // Button btnBookNow = (Button) v.findViewById(R.id.btnBook);
        Slot s = slots.get(position);
        String no = String.valueOf(s.getNo());
        tvSlotNo.setText("Slot Number: "+no);
        tvSlotTiming.setText("Slot Timings: "+s.getTiming());
        tvPtID.setText("Click here to book slot");
        /*btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        return v;


    }
}
