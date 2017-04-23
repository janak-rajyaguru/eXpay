package com.iciciappathon.expay.gateway;

import com.iciciappathon.expay.Activities.SplashActivity;
import com.iciciappathon.expay.Constants.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.iciciappathon.expay.Framework.ExPay.mDataCache;

/**
 * Created by ADMIN on 22/04/2017.
 */

public class Server {

    private static Server mInstance;
    private static Retrofit.Builder retrofit_builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create());

    private Server() {

    }

    public static Server getmInstance() {
        if (mInstance == null) {
            mInstance = new Server();
        }
        return mInstance;
    }

    public void callOperation(String url, final ServerOperationCompletion listener, Object data) {

        Retrofit retrofit = retrofit_builder.baseUrl(url).build();
        RequestDispatcher dispatcher = retrofit.create(RequestDispatcher.class);
        Call<List<ResponseData>> call = null;

        if (url.equals(Constants.GET_ACCESS_TOKEN)) {

            call = dispatcher.getAccessToken(Constants.PARTICIPANT_ID, Constants.PARTICIPANT_PASSWORD);
        } else if (url.equals(Constants.GET_ACCOUNT_DETAILS)) {

            call = dispatcher.getAccountDetails(Constants.PARTICIPANT_ID,
                    (String) mDataCache.get(Constants.ACCESS_TOKEN), Constants.CUSTOMER_ID, Constants.ACCOUNT_NUMBER);
        }

        call.enqueue(new Callback<List<ResponseData>>() {
            @Override
            public void onResponse(Call<List<ResponseData>> call, Response<List<ResponseData>> response) {
                if (response != null) {
                    listener.onResponseReceived(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseData>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public static interface ServerOperationCompletion {

        public void onResponseReceived(List<ResponseData> responseData);

        public void onError(String errorMessage);
    }
}
