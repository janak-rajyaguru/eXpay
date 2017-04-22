package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iciciappathon.expay.POJOBeans.Settlement;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

/**
 * Created by HeRain on 4/22/2017.
 */

public class SettlementAdapter extends BaseAdapter {

    private ViewHolder viewHolder = null;
    private Context mContext =null;
    private ArrayList<Settlement> mSettlementArrayList = null;
    private LayoutInflater inflater =null;
    private Settlement mSettlement;

    public SettlementAdapter(Context context, ArrayList<Settlement> settlementArrayList){
        this.mContext = context;
        this.mSettlementArrayList = settlementArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSettlementArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSettlementArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.settlement_item,null);
            viewHolder = new SettlementAdapter.ViewHolder();
            viewHolder.tvDenewala = (TextView) convertView.findViewById(R.id.tvDenewala);
            viewHolder.tvLenewala = (TextView) convertView.findViewById(R.id.tvLenewala);
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
        }else{
            viewHolder = (SettlementAdapter.ViewHolder) convertView.getTag();
        }

        mSettlement = mSettlementArrayList.get(position);
        if(mSettlement!=null && mSettlement.getDenewala()!=null && mSettlement.getLenewala()!=null) {
            if(mSettlement.getDenewala().getName().toString()!=null ) {viewHolder.tvDenewala.setText(mSettlement.getDenewala().getName().toString());}
            if(mSettlement.getLenewala().getName().toString()!=null ) {viewHolder.tvLenewala.setText(mSettlement.getLenewala().getName().toString());}
            if(mSettlement.getAmount().toString()!=null){viewHolder.tvAmount.setText(mSettlement.getAmount().toString());}
        }

        return convertView;
    }

    public class ViewHolder{
        TextView tvDenewala, tvLenewala, tvAmount;
    }
}
