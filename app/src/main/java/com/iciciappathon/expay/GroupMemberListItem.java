package com.iciciappathon.expay;

import org.json.JSONObject;

/**
 * Created by HP on 02-04-2017.
 */

public class GroupMemberListItem {
    String name;
    String VPA_Id;

    boolean isTitle = false;


    public GroupMemberListItem(JSONObject jsonObject) {
        setName(jsonObject.optString("name"));
        setVPA_Id(jsonObject.optString("VPA"));
        setTitle(jsonObject.optBoolean("isTitle"));
    }

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
}
