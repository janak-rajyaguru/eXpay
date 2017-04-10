package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

/**
 * Created by HeRain on 4/9/2017.
 */

public class MembersListAdapter extends BaseAdapter {

    private MembersListAdapter.ViewHolder viewHolder = null;
    Context context =null;
    ArrayList<GroupMemberListItem> mGroupMemberListItems = null;
    private LayoutInflater inflater =null;
    private GroupMemberListItem memberListItem;

    public MembersListAdapter(Context context, ArrayList<GroupMemberListItem> groupMemberListItems){
        this.context = context;
        this.mGroupMemberListItems = groupMemberListItems;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mGroupMemberListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupMemberListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.group_member_list_item, null);
            viewHolder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            viewHolder.txtName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.txtVpa = (TextView) view.findViewById(R.id.txtVPA);
            viewHolder.txtMemberAmount = (TextView) view.findViewById(R.id.tvMemberAmount);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        memberListItem = mGroupMemberListItems.get(position);
        if(!memberListItem.getName().equals("") && !memberListItem.getVPA_Id().equals("")) {
            viewHolder.txtName.setText(memberListItem.getName());
            viewHolder.txtVpa.setText(memberListItem.getVPA_Id());
            viewHolder.txtMemberAmount.setText(memberListItem.getMemberAmount());
            viewHolder.txtVpa.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtName, txtVpa, txtMemberAmount;
    }
}
