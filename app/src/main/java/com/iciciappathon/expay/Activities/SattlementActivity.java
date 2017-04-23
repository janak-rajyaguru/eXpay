package com.iciciappathon.expay.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.Adapters.SettlementAdapter;
import com.iciciappathon.expay.Constants.Constants;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.Fragments.settlePayment;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.POJOBeans.Settlement;
import com.iciciappathon.expay.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import static com.iciciappathon.expay.Framework.ExPay.mDataCache;

public class SattlementActivity extends AppCompatActivity {

    private ArrayList<GroupMemberListItem> mMemberItemsArrayList = new ArrayList<>();
    ArrayList<Settlement> settlementsList = new ArrayList<>();
    private ArrayList<Expense> mExpenseArrayList = new ArrayList<>();
    private Toolbar mToolbar;
    private DatabaseHandler databaseHandler;
    private Group group;
    ListView lvSettlement;
    private SettlementAdapter settlementAdapter;
    private LinearLayout llSettlepaymet;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private ArrayList<GroupMemberListItem> denevaloKiList = new ArrayList<>();
    private ArrayList<GroupMemberListItem> lenevaloKiList = new ArrayList<>();
    private ArrayList<GroupMemberListItem> freeList = new ArrayList<>();

    /*private TextView tvDebtorVPA,tvAmount;
    private EditText etCreditorVPA;*/
    private Button btnSettlepayment;

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

        lvSettlement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Settlement settlement = settlementsList.get(position);
                if(settlement.getDenewala().getIsMainMember() == 1){
                    llSettlepaymet.setVisibility(View.GONE);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SattlementActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.fragment_settle_payment,null);
                    dialogBuilder.setView(dialogView);

                    TextView tvDebtorVPA = (TextView) dialogView.findViewById(R.id.tv_debtorVPA);
                    TextView tvAmount = (TextView) dialogView.findViewById(R.id.et_amount);
                    EditText etCreditorVPA = (EditText) dialogView.findViewById(R.id.et_creditor);
                    Button btnSettlepayment = (Button) dialogView.findViewById(R.id.btnsattle);
                    Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);

                    tvDebtorVPA.setText("Your UPI : " + settlement.getDenewala().getVPA_Id());
                    tvAmount.setText(settlement.getAmount()+ " ₹");
                    etCreditorVPA.setText(settlement.getLenewala().getVPA_Id());
                    etCreditorVPA.setSelection(etCreditorVPA.getText().length());

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    btnSettlepayment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Float balance = Float.valueOf((String) mDataCache.get(Constants.BALANCE));
                            Float amountSettle = Float.valueOf(settlement.getAmount());
                            mDataCache.put(Constants.BALANCE, balance - amountSettle);*/
                            alertDialog.dismiss();
                            Toast.makeText(SattlementActivity.this, "You have settled amount " +settlement.getAmount()+ " ₹"+
                                     " with "+settlement.getLenewala().getName() , Toast.LENGTH_SHORT).show();
                           /* Intent intent = new Intent(SattlementActivity.this, HomeActivity.class);
                            startActivity(intent);*/
                        }
                    });
                }else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SattlementActivity.this);
                    builder.setTitle("Reminder");
                    builder.setMessage("Do you want to send reminder to " + settlement.getDenewala().getName()  + " for send " + settlement.getAmount() + " ₹ to " + settlement.getLenewala().getName() + ".");
                    builder.setPositiveButton("Remind", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
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
        llSettlepaymet = (LinearLayout) findViewById(R.id.ll_settle_payment);
        /*tvDebtorVPA = (TextView) findViewById(R.id.tv_debtorVPA);
        tvAmount = (TextView) findViewById(R.id.tv_amount);
        etCreditorVPA = (EditText) findViewById(R.id.et_creditorVPA);*/
        btnSettlepayment = (Button) findViewById(R.id.btnSettle);
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


