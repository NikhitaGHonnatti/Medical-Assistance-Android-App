package com.example.testing.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.testing.ui.Patient;

import java.util.List;

public class PatientList extends ArrayAdapter<Patient> {
    Activity context;
    List<Patient> patients;

    public PatientList(Activity context, List<Patient> patients)
    {
        super(context, R.layout.list_view,patients);
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.list_view,null,true);
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvID = (TextView)v.findViewById(R.id.tvID);
        Patient p = patients.get(position);
        tvName.setText("Patient Name: " + p.getName());
        tvID.setText("Patient ID: "+ p.getPid());
        return v;
    }
}
