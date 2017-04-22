package com.iciciappathon.expay.POJOBeans;

import java.io.Serializable;

/**
 * Created by HeRain on 4/22/2017.
 */

public class Settlement implements Serializable{
    private GroupMemberListItem denewala;
    private GroupMemberListItem Lenewala;
    private String amount;

    public Settlement(GroupMemberListItem debtor, GroupMemberListItem creditor, String amount){
        setDenewala(debtor);
        setLenewala(creditor);
        setAmount(amount);
    }

    public GroupMemberListItem getDenewala() {
        return denewala;
    }

    public void setDenewala(GroupMemberListItem denewala) {
        this.denewala = denewala;
    }

    public GroupMemberListItem getLenewala() {
        return Lenewala;
    }

    public void setLenewala(GroupMemberListItem lenewala) {
        Lenewala = lenewala;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
