package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.iciciappathon.expay.Constants.RequestConstants;
import com.iciciappathon.expay.R;
import com.iciciappathon.expay.gateway.GetAccessToken;
import com.iciciappathon.expay.gateway.RequestDispatcher;
import com.iciciappathon.expay.gateway.ResponseData;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    public static final String ACCESS_TOKEN = "access_token";
    public static HashMap<String, Object> mDataCache = new HashMap<String, Object>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RequestConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RequestDispatcher dispatcher = retrofit.create(RequestDispatcher.class);
        Call<List<ResponseData>> call = dispatcher.getAccessToken("janak.rajyaguru@gmail.com", "IQLOPLGV");
        call.enqueue(new Callback<List<ResponseData>>() {
            @Override
            public void onResponse(Call<List<ResponseData>> call, Response<List<ResponseData>> response) {
                List<ResponseData> responseData = response.body();
                if (responseData != null){
                    mDataCache.put(ACCESS_TOKEN, responseData.get(0).getToken());
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseData>> call, Throwable t) {

            }
        });
        /*new Handler().postDelayed(new Runnable() {

			*//*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 *//*

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 2000);*/
    }
}
