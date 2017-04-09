package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.R;

import java.lang.reflect.Member;
import java.util.ArrayList;

/**
 * Created by HeeRain on 4/9/2017.
 */

public class ExpenseListAdapter extends BaseAdapter {

    private ViewHolder viewHolder = null;
    Context context =null;
    ArrayList<Expense> mExpenselistItemArrayList = null;
    private LayoutInflater inflater =null;
    private Expense expense = null;

    public ExpenseListAdapter(Context context, ArrayList<Expense> listItemArrayList) {
        this.context = context;
        this.mExpenselistItemArrayList = listItemArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mExpenselistItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mExpenselistItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            viewHolder = new ExpenseListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.expense_list_item, null);
            viewHolder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            viewHolder.txtExpensedesc = (TextView) view.findViewById(R.id.txtExpenseDesc);
            viewHolder.txtExpenseAmount = (TextView) view.findViewById(R.id.txtExpenseAmount);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ExpenseListAdapter.ViewHolder) view.getTag();
        }

        expense = mExpenselistItemArrayList.get(position);
        if(expense !=null && !expense.getExpenseDesc().equals(null) && !expense.getExpenseAmount().equals(null)) {
            viewHolder.txtExpensedesc.setText(expense.getExpenseDesc());
            viewHolder.txtExpenseAmount.setText(expense.getExpenseAmount());
        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtExpensedesc, txtExpenseAmount;
    }

}
