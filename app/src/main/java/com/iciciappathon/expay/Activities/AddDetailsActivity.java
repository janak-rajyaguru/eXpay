package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.R;

public class AddDetailsActivity extends AppCompatActivity {

    private EditText etDesc = null;
    private EditText etAmount = null;
    private TextInputLayout tilDesc = null;
    private TextInputLayout tilAmount = null;
    private Toolbar mToolbar;
    private Button mBtnAddExpense = null;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    private Group group;
    private AutoCompleteTextView autoCompleteTextViewPaidByWhom;

    private String strExpenseDesc,strExpenseAmout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        setUpToolBar();
        initComponents();
        addListeners();

        Bundle groupBundle = getIntent().getExtras();
        group = (Group) groupBundle.getSerializable("Group");

        String[] memberNamesList = databaseHandler.getMemberNames(group.getGroupId());
        ArrayAdapter<String> memberNameAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,memberNamesList);
        autoCompleteTextViewPaidByWhom.setAdapter(memberNameAdapter);
    }

    private void setUpToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addListeners() {
        etDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    tilDesc.setHint("Description");
                } else {
                    if(etDesc.getText().length() == 0){
                        tilDesc.setHint("eg. Travel, parking");
                    }
                    enableAddExpenseButton();
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
                    enableAddExpenseButton();
                }
            }
        });

        mBtnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense expense = new Expense(strExpenseDesc,strExpenseAmout,group.getGroupId());
                databaseHandler.addExpense(expense);

                expense.setMemberName(autoCompleteTextViewPaidByWhom.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("Expense",expense);
                Intent data = new Intent();
                data.putExtras(data);
                setResult(69,data);
                finish();
            }
        });

    }

    private void enableAddExpenseButton(){
        strExpenseDesc = etDesc.getText().toString().trim();
        strExpenseAmout = etAmount.getText().toString().trim();

        if(group != null &&  group.getGroupId() != null && strExpenseDesc != null && strExpenseDesc.length() > 0 && strExpenseAmout != null && strExpenseAmout.length() > 0) {
            mBtnAddExpense.setEnabled(true);
        }else{
            mBtnAddExpense.setEnabled(false);
        }
    }

    private void initComponents() {
        etDesc = (EditText) findViewById(R.id.etDescription);
        etAmount = (EditText) findViewById(R.id.etAmount);
        tilDesc = (TextInputLayout) findViewById(R.id.tilDescription);
        tilAmount = (TextInputLayout) findViewById(R.id.tilAmount);
        mBtnAddExpense = (Button) findViewById(R.id.btnAddExpense);
        autoCompleteTextViewPaidByWhom = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
    }
}
