package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView login;
    TextView name, email, password, phone, address;
    ProgressBar bar;
    Button register;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(this);
        name = (TextView) findViewById(R.id.hospital);
        email = (TextView) findViewById(R.id.doc);
        password = (TextView) findViewById(R.id.mobile);
        phone = (TextView) findViewById(R.id.special);
        address = (TextView) findViewById(R.id.date);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String name1=name.getText().toString().trim();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("name",name1);
                startActivity(i);
                break;

            case R.id.register:
                reg();
                break;
        }

    }

    private void reg() {
        String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        String phone1 = phone.getText().toString().trim();
        String address1 = address.getText().toString().trim();

        if (name1.isEmpty()) {
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }
        if (email1.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if (password1.isEmpty()) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }

        if (password1.length() < 6) {
            password.setError("Min Password length should be 6 characters");
            password.requestFocus();
            return;
        }
        if (phone1.isEmpty()) {
            phone.setError("Phone number is required");
            phone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone1).matches() || phone1.length()!=10) {
            phone.setError("Please provide valid phone number");
            phone.requestFocus();
            return;
        }
        if (address1.isEmpty()) {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }


        bar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dataholder db = new dataholder(name1,email1,phone1,address1);
                            FirebaseDatabase.getInstance().getReference("Patients Registered")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(db).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_Activity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        bar.setVisibility(View.GONE);


                                    } else {
                                        Toast.makeText(Register_Activity.this, "Failed to register!Try again!",Toast.LENGTH_LONG).show();
                                        bar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register_Activity.this, "Failed to register!Try again!", Toast.LENGTH_LONG).show();
                            bar.setVisibility(View.GONE);

                        }
                    }
                });


    }
}







