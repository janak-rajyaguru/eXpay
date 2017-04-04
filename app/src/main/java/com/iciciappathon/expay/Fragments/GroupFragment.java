package com.iciciappathon.expay.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iciciappathon.expay.Activities.CreateGroupActivity;
import com.iciciappathon.expay.Activities.GroupDetailsActivity;
import com.iciciappathon.expay.Adapters.GroupsAdapter;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment{

    private FloatingActionButton mBtnCreateGroup;
    private ListView mGroupListView;
    private View mParentView;
    private GroupsAdapter mGroupsAdapter;
    private ArrayList<Group> mGroupItemsArrayList = new ArrayList<>();
    private Group groupItem = null;
    private Context mContext = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_group, container, false);
        mContext = getActivity().getApplicationContext();
        initializeUiElements(mParentView);
        setClickListeners();
        setDataToGroups();
        mGroupsAdapter = new GroupsAdapter(mContext,mGroupItemsArrayList);
        mGroupListView.setAdapter(mGroupsAdapter);

        mBtnCreateGroup.setBackgroundColor(mContext.getColor(R.color.colorPrimary));
        mBtnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(createGroupIntent);
            }
        });

        return mParentView;
    }

    private void setClickListeners() {
        mGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent createGroupIntent = new Intent(getActivity(), GroupDetailsActivity.class);
                startActivity(createGroupIntent);
            }
        });
    }

    private void setDataToGroups() {
        mGroupItemsArrayList.add(new Group("1","Trip"));
        mGroupItemsArrayList.add(new Group("2","Apartment"));
        mGroupItemsArrayList.add(new Group("3","Kirana"));
    }

    private void initializeUiElements(View view) {
        mBtnCreateGroup = (FloatingActionButton) view.findViewById(R.id.btn_create_group);
        mGroupListView = (ListView) view.findViewById(R.id.lv_grouplistView);
    }
}