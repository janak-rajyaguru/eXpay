package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.iciciappathon.expay.R;

public class GroupDetailsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddDetails = null;
    private TextView mGroupName = null;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        setUpToolbar();

        initComponents();
        setClickListeners();

        Intent groupIntent = getIntent();
        String groupName = groupIntent!=null ? groupIntent.getStringExtra("GroupName") : "Group Name" ;
        mGroupName.setText(groupName);
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitleTextColor(Color.WHITE);
        }
    }

    private void initComponents() {
        fabAddDetails = (FloatingActionButton) findViewById(R.id.fabAdd);
        mGroupName = (TextView) findViewById(R.id.tvGroupName);
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


}
