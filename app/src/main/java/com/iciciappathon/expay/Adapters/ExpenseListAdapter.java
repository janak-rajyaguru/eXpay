package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
            viewHolder.txtExpensePerson = (TextView) view.findViewById(R.id.txtExpensePerson);
            viewHolder.txtAvatar = (TextView) view.findViewById(R.id.txtAvatar);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ExpenseListAdapter.ViewHolder) view.getTag();
        }

        expense = mExpenselistItemArrayList.get(position);
        if(expense !=null && !expense.getExpenseDesc().equals(null) && !expense.getExpenseAmount().equals(null)) {
            viewHolder.txtExpensedesc.setText(expense.getExpenseDesc());
            viewHolder.txtExpensePerson.setText(expense.getMemberName());
            viewHolder.txtExpenseAmount.setText(expense.getExpenseAmount());
            setAvatar(viewHolder.imgAvatar, viewHolder.txtAvatar, expense.getExpenseAmount(), expense.getExpenseDesc());
        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtExpensedesc, txtExpenseAmount, txtAvatar, txtExpensePerson;
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
