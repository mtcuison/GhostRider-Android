/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 10:18 AM
 * project file last modified : 6/9/21 10:18 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class CashCountSubmitInfoModel {

    private String transNox;
    private String tranDate;
    private String orNoxxxx;
    private String siNoxxxx;
    private String prNoxxxx;
    private String crNoxxxx;
    private String entryTme;
    private String received;
    private String reqstdId;
    private String reqstdNm;
    private String message;
    public CashCountSubmitInfoModel() {
    }
    public String getMessage() {
        return message;
    }
    public String getTransNox() {
        return transNox;
    }

    public void setTransNox(String transNox) {
        this.transNox = transNox;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getOrNoxxxx() {
        return orNoxxxx;
    }

    public void setOrNoxxxx(String orNoxxxx) {
        this.orNoxxxx = orNoxxxx;
    }

    public String getSiNoxxxx() {
        return siNoxxxx;
    }

    public void setSiNoxxxx(String siNoxxxx) {
        this.siNoxxxx = siNoxxxx;
    }

    public String getPrNoxxxx() {
        return prNoxxxx;
    }

    public void setPrNoxxxx(String prNoxxxx) {
        this.prNoxxxx = prNoxxxx;
    }

    public String getCrNoxxxx() {
        return crNoxxxx;
    }

    public void setCrNoxxxx(String crNoxxxx) {
        this.crNoxxxx = crNoxxxx;
    }

    public String getEntryTme() {
        return entryTme;
    }

    public void setEntryTme(String entryTme) {
        this.entryTme = entryTme;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getReqstdId() {
        return reqstdId;
    }

    public void setReqstdId(String reqstdId) {
        this.reqstdId = reqstdId;
    }

    public String getReqstdNm() {
        return reqstdNm;
    }

    public void setReqstdNm(String reqstdNm) {
        this.reqstdNm = reqstdNm;
    }

    /**
     * Request Name/ID validations..
     * returns false if input field is empty...*/
    private boolean isRequestNameValid(){
        if(reqstdId.isEmpty()){
            message = "Please provide input on this field.";
            return false;
        }
        return true;
    }

    /**
     * All other validations returns if the fields are null...*/
    private boolean isORValid(){
        if(orNoxxxx.isEmpty()){
            message = "Please provide Official Receipt.";
            return false;
        }
        return true;
    }
    private boolean isSIValid(){
        if(siNoxxxx.isEmpty()){
            message = "Please provide Sales Invoice";
            return false;
        }
        return true;
    }
    private boolean isPRValid(){
        if(prNoxxxx.isEmpty()){
            message = "Please provide Provisional Receipt.";
            return false;
        }
        return true;
    }
    private boolean isCRValid(){
        if(crNoxxxx.isEmpty()){
            message = "Please provide Collection Receipt.";
            return false;
        }
        return true;
    }
}
