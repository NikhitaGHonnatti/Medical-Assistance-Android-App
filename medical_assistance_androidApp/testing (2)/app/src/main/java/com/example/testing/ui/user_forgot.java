package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class user_forgot extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email=(EditText)findViewById(R.id.email_id);
        reset=(Button)findViewById(R.id.but_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
        progressBar=(ProgressBar)findViewById(R.id.pg);
        auth=FirebaseAuth.getInstance();
    }

    private void resetpassword() {
        String email5=email.getText().toString().trim();

        if(email5.isEmpty()){
            email.setError("Please provide email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email5).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(user_forgot.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(user_forgot.this,"Try Again! Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
