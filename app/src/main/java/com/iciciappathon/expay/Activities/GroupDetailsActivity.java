package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.iciciappathon.expay.R;

public class GroupDetailsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        initComponents();


        setClickListeners();
    }

    private void setClickListeners() {
        fabAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddData = new Intent(GroupDetailsActivity.this, AddDetailsActivity.class);
                startActivity(iAddData);
            }
        });
    }

    private void initComponents() {
        fabAddDetails = (FloatingActionButton) findViewById(R.id.fabAdd);
    }
}
