package com.iciciappathon.expay.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iciciappathon.expay.Adapters.TransactionListAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Transaction;
import com.iciciappathon.expay.R;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    private ListView lvTransactionList;
    private View mParentView;
    private TransactionListAdapter transactionListAdapter;
    private ArrayList<Transaction> mTransactionArrayList = new ArrayList<>();
    private Context mContext = null;
    DatabaseHandler databaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_transaction, container, false);

        initializeUi();

        setdata();

        transactionListAdapter = new TransactionListAdapter(mContext,mTransactionArrayList);
        lvTransactionList.setAdapter(transactionListAdapter);

        return mParentView;
    }

    private void setdata() {
        mTransactionArrayList.clear();
        mTransactionArrayList = (ArrayList<Transaction>) databaseHandler.getTransactions();
    }

    private void initializeUi() {
        mContext = getActivity().getApplicationContext();
        databaseHandler = new DatabaseHandler(mContext);
        lvTransactionList = (ListView) mParentView.findViewById(R.id.lvTransactionList);
    }

}
