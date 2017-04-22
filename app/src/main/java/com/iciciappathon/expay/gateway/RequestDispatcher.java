package com.iciciappathon.expay.gateway;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ADMIN on 22/04/2017.
 */

public interface RequestDispatcher {

    @GET("/corporate_banking/mybank/authenticate_client?")
    public Call<List<ResponseData>> getAccessToken(@Query("client_id") String participant_id, @Query("password") String participant_access_code);
}
