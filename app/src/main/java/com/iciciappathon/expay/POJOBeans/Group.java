package com.iciciappathon.expay.POJOBeans;

import java.io.Serializable;

/**
 * Created by HeeRain on 4/4/2017.
 */

public class Group implements Serializable {
    private String groupId;
    private String groupName;

    public Group(){};

    public Group(String name){
        this.groupName = name;
    }

    public Group(String id, String name){
        groupId = id;
        groupName = name;
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
