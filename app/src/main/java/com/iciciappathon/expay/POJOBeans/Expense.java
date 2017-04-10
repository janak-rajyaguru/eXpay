package com.iciciappathon.expay.POJOBeans;

import java.io.Serializable;

/**
 * Created by HeRain on 4/9/2017.
 */

public class Expense implements Serializable {
    private String expenseId;
    private String groupId;
    private String expenseDesc;
    private String expenseAmount;
    private String memberId;
    private String memberName;

    public Expense(){};

    public Expense(String eDesc,String eAmount,String gId){
        this.expenseDesc = eDesc;
        this.expenseAmount = eAmount;
        this.groupId = gId;
    }

    Expense(String eId,String eDesc,String eAmount,String gId){
        this.expenseDesc = eDesc;
        this.expenseId = eId;
        this.expenseAmount = eAmount;
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

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
