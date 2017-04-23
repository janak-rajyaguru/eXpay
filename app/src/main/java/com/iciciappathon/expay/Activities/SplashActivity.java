package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iciciappathon.expay.Constants.Constants;
import com.iciciappathon.expay.R;
import com.iciciappathon.expay.gateway.ResponseData;
import com.iciciappathon.expay.gateway.Server;

import java.util.HashMap;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements Server.ServerOperationCompletion {

    public static HashMap<String, Object> mDataCache = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Server.getmInstance().callOperation(Constants.GET_ACCESS_TOKEN, this, null);
    }

    private void moveToNextScreen() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);

        // close this activity
        finish();
    }

    @Override
    public void onResponseReceived(List<ResponseData> responseData) {

        if (responseData != null && responseData.size() > 0) {
            mDataCache.put(Constants.ACCESS_TOKEN, responseData.get(0).getAccess_token());
            moveToNextScreen();
        }
    }

    @Override
    public void onError(String errorMessage) {
        moveToNextScreen();
    }
}
