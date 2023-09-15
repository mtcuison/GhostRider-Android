package org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo;

public class OBApplication {

    private String TransNox;
    private String Transact;
    private String EmployID;
    private String DateFrom;
    private String DateThru;
    private String Destinat;
    private String Remarksx;
    private String Approved;
    private String DateAppv;
    private String AppldFrx;
    private String AppldTox;
    private String TranStat;
    private String Modifier;
    private String Modified;

    private String message;

    public OBApplication(){

    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getEmployID() {
        return EmployID;
    }

    public void setEmployID(String employID) {
        EmployID = employID;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateThru() {
        return DateThru;
    }

    public void setDateThru(String dateThru) {
        DateThru = dateThru;
    }

    public String getDestinat() {
        return Destinat;
    }

    public void setDestinat(String destinat) {
        Destinat = destinat;
    }

    public String getRemarksx() {
        return Remarksx.trim();
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getDateAppv() {
        return DateAppv;
    }

    public void setDateAppv(String dateAppv) {
        DateAppv = dateAppv;
    }

    public String getAppldFrx() {
        return AppldFrx;
    }

    public void setAppldFrx(String appldFrx) {
        AppldFrx = appldFrx;
    }

    public String getAppldTox() {
        return AppldTox;
    }

    public void setAppldTox(String appldTox) {
        AppldTox = appldTox;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getModifier() {
        return Modifier;
    }

    public void setModifier(String modifier) {
        Modifier = modifier;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDataValid(){
        return isDateFrom() &&
                isDateFrom();
    }

    private boolean isDateFrom(){
        if(DateFrom.trim().isEmpty()){
            message = "Please enter Date From";
            return false;
        }
        return true;
    }
    private boolean isDateThru(){
        if(DateThru.trim().isEmpty()){
            message = "Please enter Date Thru";
            return false;
        }
        return true;
    }

}