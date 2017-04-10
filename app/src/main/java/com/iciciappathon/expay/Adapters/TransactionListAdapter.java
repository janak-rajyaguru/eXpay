package com.iciciappathon.expay.Adapters;

import android.content.Context;
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
            view.setTag(viewHolder);
        } else {
            viewHolder = (TransactionListAdapter.ViewHolder) view.getTag();
        }

        transaction = mTransactionlistItemArrayList.get(position);
        if(transaction !=null && !transaction.getExpenseDesc().equals(null) && !transaction.getExpenseAmount().equals(null) && !transaction.getGroupName().equals(null)) {
            viewHolder.txtTranDesc.setText(transaction.getExpenseDesc());
            viewHolder.txtTransAmount.setText(transaction.getExpenseAmount());
            viewHolder.txtTransGroupName.setText(transaction.getGroupName());
        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtTranDesc, txtTransAmount, txtTransGroupName;
    }

}
