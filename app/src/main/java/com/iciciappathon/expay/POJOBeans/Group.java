package com.iciciappathon.expay.POJOBeans;

/**
 * Created by HeeRain on 4/4/2017.
 */

public class Group {
    private String groupId;
    private String groupName;

    Group(){};
    public Group(String id, String name){
        groupId = id;
        groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
