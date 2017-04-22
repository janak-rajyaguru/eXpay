package com.iciciappathon.expay.gateway;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 22/04/2017.
 */

public class ResponseData {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    public String token;
}
