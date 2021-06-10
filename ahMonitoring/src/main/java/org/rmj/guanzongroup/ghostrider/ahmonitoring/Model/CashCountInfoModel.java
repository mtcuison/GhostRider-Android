/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/8/21 9:56 AM
 * project file last modified : 6/8/21 9:56 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class CashCountInfoModel {
    private String transNox;
    private String tranDate;
    private String nCn0001cx;
    private String nCn0005cx;
    private String nCn0025cx;
    private String nCn0001px;
    private String nCn0005px;
    private String nCn0010px;
    private String nNte0020p;
    private String nNte0050p;
    private String nNte0100p;
    private String nNte0200p;
    private String nNte0500p;
    private String nNte1000p;
    private String orNoxxxx;
    private String siNoxxxx;
    private String prNoxxxx;
    private String crNoxxxx;
    private String entryTme;
    private String reqstdId;
    private String reqstdNm;
    private String message;

    public CashCountInfoModel() {
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

    public String getnCn0001cx() {
        return nCn0001cx;
    }

    public void setnCn0001cx(String nCn0001cx) {
        this.nCn0001cx = nCn0001cx;
    }

    public String getnCn0005cx() {
        return nCn0005cx;
    }

    public void setnCn0005cx(String nCn0005cx) {
        this.nCn0005cx = nCn0005cx;
    }

    public String getnCn0025cx() {
        return nCn0025cx;
    }

    public void setnCn0025cx(String nCn0025cx) {
        this.nCn0025cx = nCn0025cx;
    }

    public String getnCn0001px() {
        return nCn0001px;
    }

    public void setnCn0001px(String nCn0001px) {
        this.nCn0001px = nCn0001px;
    }

    public String getnCn0005px() {
        return nCn0005px;
    }

    public void setnCn0005px(String nCn0005px) {
        this.nCn0005px = nCn0005px;
    }

    public String getnCn0010px() {
        return nCn0010px;
    }

    public void setnCn0010px(String nCn0010px) {
        this.nCn0010px = nCn0010px;
    }

    public String getnNte0020p() {
        return nNte0020p;
    }

    public void setnNte0020p(String nNte0020p) {
        this.nNte0020p = nNte0020p;
    }

    public String getnNte0050p() {
        return nNte0050p;
    }

    public void setnNte0050p(String nNte0050p) {
        this.nNte0050p = nNte0050p;
    }

    public String getnNte0100p() {
        return nNte0100p;
    }

    public void setnNte0100p(String nNte0100p) {
        this.nNte0100p = nNte0100p;
    }

    public String getnNte0200p() {
        return nNte0200p;
    }

    public void setnNte0200p(String nNte0200p) {
        this.nNte0200p = nNte0200p;
    }

    public String getnNte0500p() {
        return nNte0500p;
    }

    public void setnNte0500p(String nNte0500p) {
        this.nNte0500p = nNte0500p;
    }

    public String getnNte1000p() {
        return nNte1000p;
    }

    public void setnNte1000p(String nNte1000p) {
        this.nNte1000p = nNte1000p;
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
    public boolean isDataValid(){
        return isORValid() &&
                isSIValid() &&
                isPRValid() &&
                isCRValid();
    }

    private boolean isRequestNameValid(){
        if(reqstdId == null || reqstdId.isEmpty()){
            message = "Please provide input on this field.";
            return false;
        }
        return true;
    }

    /**
     * All other validations returns if the fields are null...*/
    private boolean isORValid(){
        if(orNoxxxx == null || orNoxxxx.isEmpty()){
            message = "Please provide Official Receipt.";
            return false;
        }
        return true;
    }
    private boolean isSIValid(){
        if(siNoxxxx == null || siNoxxxx.isEmpty()){
            message = "Please provide Sales Invoice";
            return false;
        }
        return true;
    }
    private boolean isPRValid(){
        if(prNoxxxx == null || prNoxxxx.isEmpty()){
            message = "Please provide Provisional Receipt.";
            return false;
        }
        return true;
    }
    private boolean isCRValid(){
        if(crNoxxxx == null || crNoxxxx.isEmpty()){
            message = "Please provide Collection Receipt.";
            return false;
        }
        return true;
    }
}
