package com.iciciappathon.expay.POJOBeans;

import org.json.JSONObject;

/**
 * Created by HP on 02-04-2017.
 */

public class GroupMemberListItem {
    String name;
    String VPA_Id;
    private String groupId;
    private String memberId;
    private String memberAmount;

    boolean isTitle = false;

    public GroupMemberListItem(String name, String VPA_Id) {
        this.name = name;
        this.VPA_Id = VPA_Id;
    }
    public GroupMemberListItem(){};
    //To insert item into db
    public GroupMemberListItem(String memberName,String vpaId,String memberAmount,String groupId){
        this.setName(memberName);
        this.setVPA_Id(vpaId);
        this.setMemberAmount(memberAmount);
        this.setGroupId(groupId);
    }

    //To get Data from Db
    public GroupMemberListItem(String memberId,String memberName,String vpaId,String memberAmount,String groupId){
        setMemberId(memberId);
        setName(memberName);
        setVPA_Id(vpaId);
        setMemberAmount(memberAmount);
        setGroupId(groupId);
    }

 /*   public GroupMemberListItem(JSONObject jsonObject) {
        setName(jsonObject.optString("name"));
        setVPA_Id(jsonObject.optString("VPA"));
        setTitle(jsonObject.optBoolean("isTitle"));
    }*/



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVPA_Id() {
        return VPA_Id;
    }

    public void setVPA_Id(String VPA_Id) {
        this.VPA_Id = VPA_Id;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(String memberAmount) {
        this.memberAmount = memberAmount;
    }
}
