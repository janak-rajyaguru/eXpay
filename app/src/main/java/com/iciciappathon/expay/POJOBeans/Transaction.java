package com.iciciappathon.expay.POJOBeans;

/**
 * Created by HeRain on 4/10/2017.
 */

public class Transaction {
    private String expenseId;
    private String expenseDesc;
    private String expenseAmount;
    private String groupName;

    public Transaction() {};

    public Transaction(String eId,String eDesc,String eAmount,String eGroupName){
        this.expenseId = eId;
        this.expenseDesc = eDesc;
        this.expenseAmount = eAmount;
        this.groupName = eGroupName;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
