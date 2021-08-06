package com.example.testing.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.w3c.dom.Comment;

import java.util.ArrayList;

public class AllComments extends AppCompatActivity {

    ListView lvAllComms;
    DatabaseReference DBcomments;
    ArrayList<DocComment> comments = new ArrayList<DocComment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments);
        lvAllComms = findViewById(R.id.lvAllComs);
        final String pid = getIntent().getStringExtra("pid");
        //String id = p.getId();

        DBcomments = FirebaseDatabase.getInstance().getReference("comments");

        DBcomments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                comments.clear();
                for(DataSnapshot CommentSS : dataSnapshot.getChildren()){
                    String date = CommentSS.getValue(DocComment.class).getDate();
                    String text = CommentSS.getValue(DocComment.class).getText();
                    String id = CommentSS.getValue(DocComment.class).getPid();
                    if(id.equals(pid)) {
                        DocComment dc = new DocComment(text, date, pid);
                        comments.add(dc);
                    }
                }
                CommentList adapter = new CommentList(AllComments.this,comments);
                lvAllComms.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllComments.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });



    }

    }

