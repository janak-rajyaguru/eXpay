package com.iciciappathon.expay.UpiController;

import com.iciciappathon.expay.Interfaces.OnUPAPaymentListener;
import com.iciciappathon.expay.Model.ServerResponse;

import java.util.ArrayList;

/**
 * Created by Kavita on 4/4/2017.
 */

public class UPIServerController {

    private static UPIServerController mUpiServerController = null;

    private NPCICallImplementation mNpciCallImplementation = new NPCICallImplementation();;
    public UPIServerController() {}

    public static UPIServerController getInstance(){
        if(mUpiServerController == null){
            mUpiServerController = new UPIServerController();
        }

        return mUpiServerController;
    }

    public ServerResponse PayUPIVpaToVpaPayment(ArrayList<Object> list, OnUPAPaymentListener listener) {
        mNpciCallImplementation.PayUPIVpaToVpaPayment();
        return null;
    }
    public ServerResponse PayUPIVpaToAccountPayment(ArrayList<Object> list, OnUPAPaymentListener listener){
        mNpciCallImplementation.PayUPIVpaToAccountPayment();
        return null;
    }
    public ServerResponse CreateVPA(String vpa, OnUPAPaymentListener listener){
        mNpciCallImplementation.CreateVPA();
        return null;
    }
    public ServerResponse DeleteVA(String vpa, OnUPAPaymentListener listener){
        mNpciCallImplementation.DeleteVA();
        return null;
    }



}
