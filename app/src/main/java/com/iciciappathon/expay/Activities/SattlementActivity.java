package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class SattlementActivity extends AppCompatActivity {

    private ArrayList<GroupMemberListItem> mMemberItemsArrayList = new ArrayList<>();
    private ArrayList<Expense> mExpenseArrayList = new ArrayList<>();
    private Toolbar mToolbar;
    private DatabaseHandler databaseHandler;
    private Group group;
    private LinearLayout mllDynamicHisab;
    private RelativeLayout mrlHisab;
    private TextView tvDenevala,tvLenevala,tvAmount;
    private Button btnsattle;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private ArrayList<GroupMemberListItem> denevaloKiList = new ArrayList<>();
    private ArrayList<GroupMemberListItem> lenevaloKiList = new ArrayList<>();
    private ArrayList<GroupMemberListItem> freeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sattlement);

        Intent intent = getIntent();
        if(intent != null) {
            mMemberItemsArrayList = (ArrayList<GroupMemberListItem>) intent.getSerializableExtra("memberlist");
            mExpenseArrayList = (ArrayList<Expense>) intent.getSerializableExtra("expenseList");
            group = (Group) intent.getSerializableExtra("group");
            //group.setGroupTotal(databaseHandler.getGroupTotal(group.getGroupId()));
        }

        setupToolbar();
        initComponents();

        adjustMemberAmount();
        //DoMaathAndUpdateUi();

        btnsattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SattlementActivity.this, "Request for money has been sent", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(SattlementActivity.this,HomeActivity.class);
                startActivity(home);
            }
        });
    }

    private void DoMaathAndUpdateUi() {
        int i=0,j=0;
        while(totalAmount.compareTo(BigDecimal.ZERO) < 0){
            BigDecimal denevalaAmount = new BigDecimal(denevaloKiList.get(i).getMemberAdjustedAmount());
            BigDecimal lenevalaAmount = new BigDecimal(lenevaloKiList.get(j).getMemberAdjustedAmount());

            tvLenevala.setText(lenevaloKiList.get(i).getName());
            tvDenevala.setText(denevaloKiList.get(j).getName());

            if(lenevalaAmount.compareTo(denevalaAmount) >0 ){
                tvAmount.setText(denevalaAmount.toString());
                i++;
                mrlHisab.setVisibility(View.VISIBLE);
                mllDynamicHisab.addView(mrlHisab);
            }else if (lenevalaAmount.compareTo(denevalaAmount) <0){
                tvAmount.setText(denevalaAmount.toString());
                j++;
                mrlHisab.setVisibility(View.VISIBLE);
                mllDynamicHisab.addView(mrlHisab);
            }else if(lenevalaAmount.compareTo(denevalaAmount) == 0){
                tvAmount.setText(denevalaAmount.toString());
                i++;
                mrlHisab.setVisibility(View.VISIBLE);
                mllDynamicHisab.addView(mrlHisab);
                break;
            }

            totalAmount=totalAmount.subtract(new BigDecimal(denevaloKiList.get(i).getMemberAdjustedAmount()));
        }
    }

    private void adjustMemberAmount() {
        for(int i=0 ; i < mMemberItemsArrayList.size() ; i++){
            GroupMemberListItem member = mMemberItemsArrayList.get(i);
            BigDecimal memberExpenseTotal = new BigDecimal(member.getMemberExpenseTotal()!=null ? member.getMemberExpenseTotal() :"0");
            BigDecimal memberAmount = new BigDecimal(member.getMemberAmount()!=null ? member.getMemberAmount() : "0");

            BigDecimal memberAdjustedAmount = memberAmount.subtract(memberExpenseTotal);
            mMemberItemsArrayList.get(i).setMemberAdjustedAmount(String.valueOf(memberAdjustedAmount));

            if(BigDecimal.ZERO.compareTo(memberAdjustedAmount) < 0){
                denevaloKiList.add(mMemberItemsArrayList.get(i));
            }else if(BigDecimal.ZERO.compareTo(memberAdjustedAmount) > 0){
                lenevaloKiList.add(mMemberItemsArrayList.get(i));
                totalAmount = totalAmount.add(memberAdjustedAmount);
            }else{
                freeList.add(mMemberItemsArrayList.get(i));
            }
        }
    }

    private void initComponents() {
        databaseHandler = new DatabaseHandler(this);
        mllDynamicHisab = (LinearLayout) findViewById(R.id.llDynamicHisab);
        mrlHisab = (RelativeLayout) findViewById(R.id.hisab);
        tvDenevala = (TextView) findViewById(R.id.tvDenewala);
        tvLenevala = (TextView) findViewById(R.id.tvLenewala);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        btnsattle = (Button) findViewById(R.id.btnsattle);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitleTextColor(Color.WHITE);
        }
    }
}
