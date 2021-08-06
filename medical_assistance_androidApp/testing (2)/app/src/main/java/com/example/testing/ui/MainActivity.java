package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView login,pat_register;
    TextView forgot;
    EditText useremail, userpass;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useremail = (EditText) findViewById(R.id.useremail1);
        userpass = (EditText) findViewById(R.id.userpass1);
        forgot=(TextView)findViewById(R.id.forgot1);
        forgot.setOnClickListener(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        mAuth = FirebaseAuth.getInstance();
        login=(TextView) findViewById(R.id.login1);
        login.setOnClickListener(this);
        pat_register=(TextView) findViewById(R.id.patient_reg);



        pat_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register_Activity.class));
                finish();
            }
        });



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login1:
                userlogin();
                break;

            case R.id.forgot1:
                startActivity(new Intent(this,user_forgot.class));
                break;

        }


    }

    private void userlogin() {
        String email3 = useremail.getText().toString().trim();
        String password3 = userpass.getText().toString().trim();

        if (email3.isEmpty()) {
            useremail.setError("Email is required!");
            useremail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email3).matches()) {
            useremail.setError("Please provide a valid email!");
            useremail.requestFocus();
            return;
        }
        if (password3.isEmpty()) {
            userpass.setError("Email is required!");
            userpass.requestFocus();
            return;
        }
        if (password3.length() < 6) {
            userpass.setError("Min password length must be 6");
            userpass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email3, password3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String U = user.getUid();
                    if (user.isEmailVerified()) {
                        if(email3.equals("1by18is074@bmsit.in") && password3.equals("654321")){
                            startActivity(new Intent(MainActivity.this,Dashboard.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            //String usermail = useremail.getText().toString();
                            Intent i = new Intent(new Intent(MainActivity.this, Patient_Dashboard.class));
                            i.putExtra("pid",U);
                            //i.putExtra("mail",usermail);
                            startActivity(i);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    }


                } else {
                    Toast.makeText(MainActivity.this, "Login failed! Please check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


    }
}
