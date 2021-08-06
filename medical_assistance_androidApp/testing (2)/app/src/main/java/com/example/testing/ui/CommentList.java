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


    public class CommentList extends ArrayAdapter<DocComment> {
        Activity context;
        List<DocComment> comments;

        public CommentList (Activity context, ArrayList<DocComment> comments)
        {
            super(context, R.layout.list_view,comments);
            this.context = context;
            this.comments = comments;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View v = inflater.inflate(R.layout.list_view,null,true);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            TextView tvID = (TextView)v.findViewById(R.id.tvID);
            DocComment dc = comments.get(position);
            tvID.setText(dc.getDate());
            tvName.setText(dc.getText());
            return v;
        }
    }


