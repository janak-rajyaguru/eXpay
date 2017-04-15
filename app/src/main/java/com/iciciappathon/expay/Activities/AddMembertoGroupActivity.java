package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

public class AddMembertoGroupActivity extends AppCompatActivity {

    private EditText etMemberName,etMemberAccountNo,etMemberIFSC,etMemberVPA;
    String strMembername,strMemberAccountNo,strMemberIFSC,strMemberVPA;
    private Button btnAddMember;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memberto_group);

        setUpToolbar();
        initializeComponents();
        AddedTextWatchers();
    }

    private void AddedTextWatchers() {
        etMemberName.addTextChangedListener(editTextTextWatcher);
        etMemberAccountNo.addTextChangedListener(editTextTextWatcher);
        etMemberIFSC.addTextChangedListener(editTextTextWatcher);
        etMemberVPA.addTextChangedListener(editTextTextWatcher);
    }

    private void initializeComponents() {
        etMemberName = (EditText) findViewById(R.id.et_Member_Name);
        etMemberAccountNo = (EditText) findViewById(R.id.etMemberAccoutNo);
        etMemberIFSC = (EditText) findViewById(R.id.etMemberIFSC);
        etMemberVPA = (EditText) findViewById(R.id.etMemberVPA);
        btnAddMember = (Button) findViewById(R.id.btnAddMemberToGroup);

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberListItem member = new GroupMemberListItem();
                member.setName(strMembername);
                member.setMemberAccoutno(strMemberAccountNo);
                member.setMemberIFSC(strMemberIFSC);
                member.setVPA_Id(strMemberVPA);
                member.setMemberAmount("0");

                Intent memberdataIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("memberToGroup",member);
                memberdataIntent.putExtras(bundle);
                setResult(15,memberdataIntent);
                finish();
            }
        });
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(Color.WHITE);
            mToolbar.setTitle(getString(R.string.app_name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private TextWatcher editTextTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!etMemberName.getText().toString().trim().equals("")){strMembername = etMemberName.getText().toString().trim();}
            if(!etMemberAccountNo.getText().toString().trim().equals("")){strMemberAccountNo = etMemberAccountNo.getText().toString().trim();}
            if(!etMemberIFSC.getText().toString().trim().equals("")){strMemberIFSC = etMemberIFSC.getText().toString().trim();}
            if(!etMemberVPA.getText().toString().trim().equals("")){strMemberVPA = etMemberVPA.getText().toString().trim();}
            enableAddMemberButton();
        }
    };

    private void enableAddMemberButton() {
        if(strMembername!=null && strMembername.length()>0 &&
           strMemberAccountNo!=null && strMemberAccountNo.length()>0 &&
           strMemberIFSC!=null && strMemberIFSC.length()>0 &&
           strMemberVPA!=null && strMemberVPA.length()>0){
            btnAddMember.setEnabled(true);
        }else{
            btnAddMember.setEnabled(false);
        }
    }
}
