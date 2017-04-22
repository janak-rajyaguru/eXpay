package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.Adapters.SettlementAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.POJOBeans.Settlement;
import com.iciciappathon.expay.R;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class SattlementActivity extends AppCompatActivity {

    private ArrayList<GroupMemberListItem> mMemberItemsArrayList = new ArrayList<>();
    ArrayList<Settlement> settlementsList = new ArrayList<>();
    private ArrayList<Expense> mExpenseArrayList = new ArrayList<>();
    private Toolbar mToolbar;
    private DatabaseHandler databaseHandler;
    private Group group;
    ListView lvSettlement;
    private SettlementAdapter settlementAdapter;
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
            //count = (int) intent.getIntExtra("count",1);
            //group.setGroupTotal(databaseHandler.getGroupTotal(group.getGroupId()));
        }

        setupToolbar();
        initComponents();

        adjustMemberAmount();
        try {
            DoMaathAndUpdateUi();
        }catch (NumberFormatException ne){
            ne.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        settlementAdapter = new SettlementAdapter(this,settlementsList);
        lvSettlement.setAdapter(settlementAdapter);

        /*btnsattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SattlementActivity.this, "Request for money has been sent", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(SattlementActivity.this,HomeActivity.class);
                startActivity(home);
            }
        });*/
    }

    private void DoMaathAndUpdateUi() {
        int denewala=0,lenewala=0;
        while(totalAmount.floatValue() > 0 && denevaloKiList.size() > denewala && lenevaloKiList.size() > lenewala && denevaloKiList.get(denewala)!=null && lenevaloKiList.get(lenewala)!=null){
            BigDecimal denevalaAmount = new BigDecimal(denevaloKiList.get(denewala).getMemberAdjustedAmount());
            BigDecimal lenevalaAmount = new BigDecimal(lenevaloKiList.get(lenewala).getMemberAdjustedAmount());
            BigDecimal subAmount = new BigDecimal(BigInteger.ZERO);

            //tvLenevala.setText(lenevaloKiList.get(denewala).getName());
            //tvDenevala.setText(denevaloKiList.get(lenewala).getName());

            if(lenevalaAmount.compareTo(denevalaAmount) == 0){
                subAmount  = new BigDecimal(denevaloKiList.get(denewala).getMemberAdjustedAmount());
                //addView(denevaloKiList.get(denewala).getName(),lenevaloKiList.get(lenewala).getName(),String.valueOf(subAmount));
                settlementsList.add(new Settlement(denevaloKiList.get(denewala),lenevaloKiList.get(lenewala),String.valueOf(subAmount.setScale(2,BigDecimal.ROUND_DOWN))));
                lenewala++;
                denewala++;
            }else if(lenevalaAmount.compareTo(denevalaAmount) > 0 ){
                subAmount = new BigDecimal(denevaloKiList.get(denewala).getMemberAdjustedAmount());
                BigDecimal newAmount = new BigDecimal(lenevaloKiList.get(lenewala).getMemberAdjustedAmount()).subtract(subAmount);
                //addView(denevaloKiList.get(denewala).getName(),lenevaloKiList.get(lenewala).getName(),String.valueOf(subAmount));
                lenevaloKiList.get(lenewala).setMemberAdjustedAmount(String.valueOf(newAmount));
                settlementsList.add(new Settlement(denevaloKiList.get(denewala),lenevaloKiList.get(lenewala),String.valueOf(subAmount.setScale(2,BigDecimal.ROUND_DOWN))));
                denewala++;
            }else if (lenevalaAmount.compareTo(denevalaAmount) < 0){
                subAmount = new BigDecimal(lenevaloKiList.get(lenewala).getMemberAdjustedAmount());
                BigDecimal newAmount = new BigDecimal(denevaloKiList.get(denewala).getMemberAdjustedAmount()).subtract(subAmount);
                //addView(denevaloKiList.get(denewala).getName(),lenevaloKiList.get(lenewala).getName(),String.valueOf(subAmount));
                denevaloKiList.get(denewala).setMemberAdjustedAmount(String.valueOf(newAmount));
                settlementsList.add(new Settlement(denevaloKiList.get(denewala),lenevaloKiList.get(lenewala),String.valueOf(subAmount.setScale(2,BigDecimal.ROUND_DOWN))));
                lenewala++;
            }
            totalAmount = totalAmount.subtract(subAmount);
            totalAmount.setScale(2,BigDecimal.ROUND_DOWN);
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
                mMemberItemsArrayList.get(i).setMemberAdjustedAmount(mMemberItemsArrayList.get(i).getMemberAdjustedAmount().substring(1));
                lenevaloKiList.add(mMemberItemsArrayList.get(i));
                totalAmount = totalAmount.add(new BigDecimal(memberAdjustedAmount.doubleValue() * -1));
            }else{
                freeList.add(mMemberItemsArrayList.get(i));
            }
        }
    }

    private void initComponents() {
        databaseHandler = new DatabaseHandler(this);
        lvSettlement = (ListView) findViewById(R.id.lv_settlement);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setTitleTextColor(Color.WHITE);
        }
    }

   /* private void addView(String denewala,String lenewala,String amount){
        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View dynamicView = layoutInflater.inflate(R.layout.settlement_item, null);
        TextView tvDenewala = (TextView) dynamicView.findViewById(R.id.tvDenewala);
        TextView tvlenewala = (TextView) dynamicView.findViewById(R.id.tvLenewala);
        TextView tvAmount = (TextView) dynamicView.findViewById(R.id.tvAmount);
        tvDenewala.setText(denewala);
        tvlenewala.setText(lenewala);
        tvAmount.setText(amount);
        ll_container.addView(dynamicView);
    }*/

}
