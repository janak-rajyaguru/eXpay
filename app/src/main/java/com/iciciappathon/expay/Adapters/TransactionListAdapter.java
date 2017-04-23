package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Transaction;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

/**
 * Created by HeRain on 4/10/2017.
 */

public class TransactionListAdapter extends BaseAdapter {

    private ViewHolder viewHolder = null;
    private Context context =null;
    private ArrayList<Transaction> mTransactionlistItemArrayList = null;
    private LayoutInflater inflater =null;
    private Transaction transaction = null;

    public TransactionListAdapter(Context context, ArrayList<Transaction> listItemArrayList) {
        this.context = context;
        this.mTransactionlistItemArrayList = listItemArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTransactionlistItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransactionlistItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            viewHolder = new TransactionListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.transaction_list_item, null);
            viewHolder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            viewHolder.txtTranDesc = (TextView) view.findViewById(R.id.txtTransactionDesc);
            viewHolder.txtTransAmount = (TextView) view.findViewById(R.id.txtTranAmount);
            viewHolder.txtTransGroupName = (TextView) view.findViewById(R.id.txtExpenseGroupname);
            viewHolder.txtAvatar = (TextView) view.findViewById(R.id.txtAvatar);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TransactionListAdapter.ViewHolder) view.getTag();
        }

        transaction = mTransactionlistItemArrayList.get(position);
        if(transaction !=null && !transaction.getExpenseDesc().equals(null) && !transaction.getExpenseAmount().equals(null) && !transaction.getGroupName().equals(null)) {
            viewHolder.txtTranDesc.setText(transaction.getExpenseDesc());
            viewHolder.txtTransAmount.setText(transaction.getExpenseAmount()+ " ₹");
            viewHolder.txtTransGroupName.setText(transaction.getGroupName());
            setAvatar(viewHolder.imgAvatar, viewHolder.txtAvatar, transaction.getExpenseAmount(), transaction.getExpenseDesc());
        }

        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtTranDesc, txtTransAmount, txtTransGroupName, txtAvatar;
    }

    private void setAvatar(ImageView imageView, TextView textView, String amount, String name){
        GradientDrawable gradientDrawable = new GradientDrawable();
        int sum = 0;
        String amountValue = amount.trim();
        for(int i=1; i< amount.length() + 1;i++){
            if(!amountValue.substring(i-1, i).equals(".")) {
                sum = sum + Integer.valueOf(amountValue.substring(i - 1, i));
            }
        }
        int color = sum % amount.length();
        Integer[] avatarBck =  { R.color.green, R.color.red, R.color.blue, R.color.purple, R.color.yellow};
        gradientDrawable.setColor(avatarBck[color]);
        imageView.setBackground(gradientDrawable);
        textView.setText(name.substring(0,1).toUpperCase());
    }

}
