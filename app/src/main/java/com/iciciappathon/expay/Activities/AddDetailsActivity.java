package com.iciciappathon.expay.Activities;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.iciciappathon.expay.R;

public class AddDetailsActivity extends AppCompatActivity {

    private EditText etDesc = null;
    private EditText etAmount = null;
    private TextInputLayout tilDesc = null;
    private TextInputLayout tilAmount = null;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        setUpToolBar();
        initComponents();
        addFocusListeners();
    }

    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addFocusListeners() {
        etDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    tilDesc.setHint("Description");
                } else {
                    if(etDesc.getText().length() == 0){
                        tilDesc.setHint("eg. Travel, parking");
                    }
                }
            }
        });

        etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    tilAmount.setHint("Amount");
                } else {
                    if(etAmount.getText().length() == 0){
                        tilAmount.setHint("0.00");
                    }
                }
            }
        });
    }

    private void initComponents() {
        etDesc = (EditText) findViewById(R.id.etDescription);
        etAmount = (EditText) findViewById(R.id.etAmount);
        tilDesc = (TextInputLayout) findViewById(R.id.tilDescription);
        tilAmount = (TextInputLayout) findViewById(R.id.tilAmount);
    }
}
