package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.Adapters.ExpenseListAdapter;
import com.iciciappathon.expay.Adapters.MembersListAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.Framework.ExPay;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class GroupDetailsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddDetails = null;
    private TextView mGroupName = null;
    private Toolbar mToolbar;
    private Group group;
    private ListView mLvMemberListView;
    private ListView mLvExpenseListView;
    private ArrayList<GroupMemberListItem> mMemberItemsArrayList = new ArrayList<>();
    private ArrayList<Expense> mExpenseArrayList = new ArrayList<>();
    private MembersListAdapter membersListAdapter;
    private ExpenseListAdapter expenseListAdapter;
    private Bundle groupBundle;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    private TextView tvExpenseLable;
    private Expense expense;
    private Float individualExpense = 0F;
    public static int groupCount = 0;
    private Button btnSattlement;
    private static int sattlementCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        Intent groupIntent = getIntent();
        if(groupIntent!=null){
            groupBundle = groupIntent.getExtras();
            group = (Group) groupBundle.getSerializable("Group");
            //mGroupName.setText(group!=null ? group.getGroupName() : "Group Name");
        }

        setUpToolbar();
        initComponents();
        setClickListeners();
        setDataToMemberList();
        setDataToExpenseList();
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(group.getGroupName());
            mToolbar.setTitleTextColor(Color.WHITE);
        }
    }

    private void initComponents() {
        fabAddDetails = (FloatingActionButton) findViewById(R.id.fabAddExpense);
        //mGroupName = (TextView) findViewById(R.id.tvGroupName);
        mLvMemberListView = (ListView) findViewById(R.id.lvMemberListView);
        mLvExpenseListView = (ListView) findViewById(R.id.lvExpenseListView);
        tvExpenseLable = (TextView) findViewById(R.id.tvExpenseLable);
        btnSattlement = (Button) findViewById(R.id.btnSattlement);
    }

    private void setClickListeners() {
        fabAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iAddData = new Intent(GroupDetailsActivity.this, AddDetailsActivity.class);
                iAddData.putExtras(groupBundle);
                startActivityForResult(iAddData,1);
            }
        });

        btnSattlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sattlementCount++;
                Intent sattlementActivityIntent = new Intent(GroupDetailsActivity.this,SattlementActivity.class);
                sattlementActivityIntent.putExtra("expenseList", mExpenseArrayList);
                sattlementActivityIntent.putExtra("memberlist",mMemberItemsArrayList);
                sattlementActivityIntent.putExtra("group",group);
                sattlementActivityIntent.putExtra("count",sattlementCount);
                startActivity(sattlementActivityIntent);
            }
        });
    }

    private void setDataToMemberList() {
        mMemberItemsArrayList.clear();
        mMemberItemsArrayList = (ArrayList<GroupMemberListItem>) databaseHandler.getMembers(group.getGroupId());
        membersListAdapter = new MembersListAdapter(this,mMemberItemsArrayList);
        mLvMemberListView.setAdapter(membersListAdapter);
        groupCount = mMemberItemsArrayList.size();
    }

    private void setDataToExpenseList() {
        mExpenseArrayList.clear();
        mExpenseArrayList = (ArrayList<Expense>) databaseHandler.getExpenses(group.getGroupId());
        if(mExpenseArrayList.size() == 0){
            tvExpenseLable.setVisibility(View.INVISIBLE);
        }else{
            tvExpenseLable.setVisibility(View.VISIBLE);
        }

        expenseListAdapter = new ExpenseListAdapter(this,mExpenseArrayList);
        mLvExpenseListView.setAdapter(expenseListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 69){
            ExPay.transactionRefresh = true;
            expense = (Expense) data.getExtras().getSerializable("Expense");
            individualExpense =(Float.parseFloat(expense.getExpenseAmount()) / groupCount ) ;
            addExpenseToMembers();
            refreshExpenseList();
        }
    }

    private void addExpenseToMembers() {
        for (GroupMemberListItem member:mMemberItemsArrayList) {
            //if(!member.getMemberId().equals(expense.getMemberId())){
                String preMemberAmount = databaseHandler.getMemberAmount(member.getMemberId(),member.getGroupId());
                Float totalAmount = (Float.parseFloat(preMemberAmount!=null?preMemberAmount:"0") + individualExpense);
                BigDecimal total = new BigDecimal(String.valueOf(totalAmount)).setScale(2,BigDecimal.ROUND_UP);
                member.setMemberAmount(String.valueOf(total));
                databaseHandler.updateMemberTotal(member.getMemberAmount(),member.getMemberId(),member.getGroupId());
            //}
        }
    }

    private void refreshExpenseList() {
        setDataToMemberList();
        setDataToExpenseList();
    }
}
