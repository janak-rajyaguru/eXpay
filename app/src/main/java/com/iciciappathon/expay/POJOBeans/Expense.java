package com.iciciappathon.expay.POJOBeans;

/**
 * Created by HeRain on 4/9/2017.
 */

public class Expense {
    private String expenseId;
    private String groupId;
    private String expenseDesc;

    public Expense(){};

    Expense(String gId,String eDesc){
        this.expenseDesc = eDesc;
        this.groupId = gId;
    }

    Expense(String eId,String gId,String eDesc){
        this.expenseDesc = eDesc;
        this.expenseId = eId;
        this.groupId = gId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }
}
