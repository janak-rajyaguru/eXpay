package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

/**
 * Created by HP on 02-04-2017.
 */

public class GroupMemberListAdapter extends BaseAdapter {

    private ViewHolder viewHolder = null;
    Context context =null;
    ArrayList<GroupMemberListItem> listItemArrayList = null;
    private LayoutInflater inflater =null;
    private GroupMemberListItem listItem;

    public GroupMemberListAdapter(Context context, ArrayList<GroupMemberListItem> listItemArrayList) {
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return listItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.group_member_list_item, null);
            viewHolder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            viewHolder.txtName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.txtVpa = (TextView) view.findViewById(R.id.txtVPA);
            viewHolder.llAmount = (LinearLayout) view.findViewById(R.id.llAmount);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        listItem = listItemArrayList.get(i);
        if(listItem.getName().equals("") && listItem.getVPA_Id().equals("")){
            viewHolder.txtName.setText("Add member to Group");
            viewHolder.txtName.setTextSize(20);
            viewHolder.txtName.setTextColor(context.getResources().getColor(R.color.green));
            viewHolder.txtName.setPadding(120,0,0,0);
            viewHolder.txtVpa.setVisibility(View.GONE);
            viewHolder.imgAvatar.setVisibility(View.GONE);
            viewHolder.llAmount.setVisibility(View.GONE);
        } else {
            viewHolder.txtName.setText(listItem.getName());
            viewHolder.txtVpa.setText(listItem.getVPA_Id());
            viewHolder.txtVpa.setVisibility(View.VISIBLE);
            viewHolder.imgAvatar.setVisibility(View.VISIBLE);
            setAvatar(viewHolder.imgAvatar, listItem.getName());
            viewHolder.llAmount.setVisibility(View.VISIBLE);

        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtName, txtVpa;
        LinearLayout llAmount;
    }


    private void setAvatar(ImageView imageView, String name){
        GradientDrawable gradientDrawable = new GradientDrawable();
        int sum = 12;
        int color = sum % name.length();
        Integer[] avatarBck =  { R.color.green, R.color.red, R.color.blue, R.color.purple, R.color.yellow};
        gradientDrawable.setColor(avatarBck[color]);
        imageView.setBackground(gradientDrawable);
    }
}
