package com.iciciappathon.expay.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
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
 * Created by HeeRain on 4/4/2017.
 */

public class GroupsAdapter extends BaseAdapter {

    private GroupsAdapter.ViewHolder viewHolder = null;
    Context context =null;
    ArrayList<Group> grouplistItemArrayList = null;
    private LayoutInflater inflater =null;
    private Group grouplistItem;

    public GroupsAdapter(Context context, ArrayList<Group> listItemArrayList) {
        this.context = context;
        this.grouplistItemArrayList = listItemArrayList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grouplistItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return grouplistItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.group_item, null);
            viewHolder.imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            viewHolder.txtGrpName = (TextView) view.findViewById(R.id.txtName);
            view.setTag(viewHolder);
        }else{
            viewHolder = (GroupsAdapter.ViewHolder) view.getTag();
        }

        grouplistItem = grouplistItemArrayList.get(i);
        if(grouplistItem.getGroupName()!=null){
            viewHolder.txtGrpName.setText(grouplistItem.getGroupName());
        }
        return view;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView txtGrpName, txtGrpId;
    }
}
