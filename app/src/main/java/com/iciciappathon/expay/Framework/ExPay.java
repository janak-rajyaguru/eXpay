package com.iciciappathon.expay.Framework;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by HeRain on 4/9/2017.
 */

public class ExPay extends Application implements Application.ActivityLifecycleCallbacks{

    public static boolean transactionRefresh = false;
    public static HashMap<String, Object> mDataCache = new HashMap<String, Object>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
