package com.iciciappathon.expay.gateway;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by ADMIN on 22/04/2017.
 */

public class ResponseData {

    private static final String TOKEN = "token";
    private static final String CODE = "code";
    private static final String CUSTOMER_ID = "custid";
    private static final String CUSTOMER_ACCOUNT_NUMBER = "accountno";
    private static final String CUSTOMER_ACCOUNT_BALANCE = "balance";


    @SerializedName(TOKEN)
    public String access_token;

    @SerializedName(CODE)
    public String code;

    @SerializedName(CUSTOMER_ID)
    public String customer_id;

    @SerializedName(CUSTOMER_ACCOUNT_NUMBER)
    public String account_number;

    @SerializedName(CUSTOMER_ACCOUNT_BALANCE)
    public String account_balance;



    public String getAccess_token() {
        return access_token;
    }

    public String getCustomerId() {
        return customer_id;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public String getAccountBalance() {
        return account_balance;
    }
}
