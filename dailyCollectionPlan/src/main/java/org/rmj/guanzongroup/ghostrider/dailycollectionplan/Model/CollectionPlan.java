/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

public class CollectionPlan {

    private String AcctNoxxx;
    private String DCPNumber;
    private String ClientNme;
    private String Addressxx;
    private String HouseNoxx;
    private String TownNamex;
    private String Contactxx;
    private String DCPCountx;
    private String Statusxxx;
    private String Balancexx;
    private String AmntDuexx;

    public CollectionPlan() {

    }

    public void setAcctNoxxx(String acctNoxxx) {
        AcctNoxxx = acctNoxxx;
    }

    public void setDCPNumber(String DCPNumber) {
        this.DCPNumber = DCPNumber;
    }

    public void setClientNme(String clientNme) {
        ClientNme = clientNme;
    }

    public void setAddressxx(String addressxx) {
        Addressxx = addressxx;
    }

    public void setContactxx(String contactxx) {
        Contactxx = contactxx;
    }

    public void setDCPCountx(String DCPCountx) {
        this.DCPCountx = DCPCountx;
    }

    public void setStatusxxx(String statusxxx) {
        Statusxxx = statusxxx;
    }

    public void setBalancexx(String balancexx) {
        Balancexx = balancexx;
    }

    public void setAmntDuexx(String amntDuexx) {
        AmntDuexx = amntDuexx;
    }

    public String getAcctNoxxx() {
        return AcctNoxxx;
    }

    public String getDCPNumber() {
        return "Entry No. : " + DCPNumber;
    }

    public String getClientNme() {
        return ClientNme;
    }

    public String getAddressxx() {
        return HouseNoxx +", " + Addressxx +", "+ TownNamex;
    }

    public void setHouseNoxx(String houseNoxx) {
        HouseNoxx = houseNoxx;
    }

    public void setTownNamex(String townNamex) {
        TownNamex = townNamex;
    }

    public String getContactxx() {
        return Contactxx;
    }

    public String getStatusxxx() {
        return Statusxxx;
    }

    public String getBalancexx() {
        return Balancexx;
    }

    public String getAmntDuexx() {
        return FormatUIText.getCurrencyUIFormat(AmntDuexx);
    }
}
