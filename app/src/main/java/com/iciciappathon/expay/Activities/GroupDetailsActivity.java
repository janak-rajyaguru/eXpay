package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.Adapters.MembersListAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

public class GroupDetailsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddDetails = null;
    private TextView mGroupName = null;
    private Toolbar mToolbar;
    private Group group;
    private ListView mLvMemberListView;
    private ArrayList<GroupMemberListItem> mMemberItemsArrayList = new ArrayList<>();
    private MembersListAdapter membersListAdapter;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        setUpToolbar();

        initComponents();
        setClickListeners();

        Intent groupIntent = getIntent();

        if(groupIntent!=null){
            Bundle groupBundle = groupIntent.getExtras();
            group = (Group) groupBundle.getSerializable("Group");
            mGroupName.setText(group!=null ? group.getGroupName() : "Group Name");
        }

        setDataToMemberList();
        membersListAdapter = new MembersListAdapter(this,mMemberItemsArrayList);
        mLvMemberListView.setAdapter(membersListAdapter);
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
        fabAddDetails = (FloatingActionButton) findViewById(R.id.fabAddExpense);
        mGroupName = (TextView) findViewById(R.id.tvGroupName);
        mLvMemberListView = (ListView) findViewById(R.id.lvMemberListView);
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

    private void setDataToMemberList() {
        mMemberItemsArrayList.clear();
        mMemberItemsArrayList = (ArrayList<GroupMemberListItem>) databaseHandler.getMembers(group.getGroupId());
    }

}
