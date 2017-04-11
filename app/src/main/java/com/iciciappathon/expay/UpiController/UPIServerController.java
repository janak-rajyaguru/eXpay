package com.iciciappathon.expay.UpiController;

import com.iciciappathon.expay.Interfaces.OnUPAPaymentListener;
import com.iciciappathon.expay.Model.ServerResponse;

import java.util.ArrayList;

/**
 * Created by Kavita on 4/4/2017.
 */

public class UPIServerController {

    private static UPIServerController mUpiServerController = null;

    public UPIServerController() {}

    public static UPIServerController getInstance(){
        if(mUpiServerController == null){
            mUpiServerController = new UPIServerController();
        }
        return mUpiServerController;
    }

    public ServerResponse PayUPIVpaToVpaPayment(ArrayList<Object> list, OnUPAPaymentListener listener) {

        return null;
    }
    public ServerResponse PayUPIVpaToAccountPayment(ArrayList<Object> list, OnUPAPaymentListener listener){

        return null;
    }
    public ServerResponse CreateVPA(String vpa, OnUPAPaymentListener listener){

        return null;
    }
    public ServerResponse DeleteVA(String vpa, OnUPAPaymentListener listener){

        return null;
    }



}
