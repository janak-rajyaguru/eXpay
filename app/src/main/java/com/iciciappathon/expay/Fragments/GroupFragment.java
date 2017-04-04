package com.iciciappathon.expay.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iciciappathon.expay.Activities.CreateGroupActivity;
import com.iciciappathon.expay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment{

    private FloatingActionButton mBtnCreateGroup;
    private ListView mGroupListView;
    private View mParentView;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_group, container, false);

        initializeUiElements(mParentView);

        mBtnCreateGroup.setBackgroundColor(getActivity().getColor(R.color.colorPrimary));
        mBtnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(createGroupIntent);
            }
        });

        return mParentView;
    }

    private void initializeUiElements(View view) {
        mBtnCreateGroup = (FloatingActionButton) view.findViewById(R.id.btn_create_group);
        mGroupListView = (ListView) view.findViewById(R.id.lv_grouplistView);
    }
}