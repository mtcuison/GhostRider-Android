package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

import java.util.ArrayList;
import java.util.List;

public class OtherReference {

    private String transNox;
    private String sUnitUser;
    private String sUsr2Buyr;
    private String sPurposex;
    private String sUnitPayr;
    private String sPyr2Buyr;
    private String source;
    private String companyInfoSource;

    private String message;

    private List<Reference> poRefInfo;


    public OtherReference(){
        poRefInfo = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public String getTransNox() {
        return this.transNox;
    }

    public void setTransNox(String transNox) {
        this.transNox = transNox;
    }

    public void AddReference(Reference args){
        poRefInfo.add(args);
    }

    public void clear(){
        poRefInfo.clear();
    }

    public List<Reference> getReferences() {
        return poRefInfo;
    }

    public String getsUnitUser() {
        return sUnitUser;
    }

    public void setsUnitUser(String sUnitUser) {
        this.sUnitUser = sUnitUser;
    }

    public String getsUsr2Buyr() {
        return sUsr2Buyr;
    }

    public void setsUsr2Buyr(String sUsr2Buyr) {
        this.sUsr2Buyr = sUsr2Buyr;
    }

    public String getsPurposex() {
        return sPurposex;
    }

    public void setsPurposex(String sPurposex) {
        this.sPurposex = sPurposex;
    }

    public String getsUnitPayr() {
        return sUnitPayr;
    }

    public void setsUnitPayr(String sUnitPayr) {
        this.sUnitPayr = sUnitPayr;
    }

    public String getsPyr2Buyr() {
        return sPyr2Buyr;
    }

    public void setsPyr2Buyr(String sPyr2Buyr) {
        this.sPyr2Buyr = sPyr2Buyr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCompanyInfoSource() {
        return companyInfoSource;
    }

    public void setCompanyInfoSource(String companyInfoSource) {
        this.companyInfoSource = companyInfoSource;
    }

    public boolean isDataValid(){
        return isUnitUserModel() &&
                isUnitPurposeModel() &&
                isSourceModel() &&
                isReferencesValid();
    }

    private boolean isUnitUserModel(){
        if(sUnitUser == null){
            message = "Please select unit user";
            return false;
        } else if(Integer.parseInt(sUnitUser) == 1){
            return isUserBuyerModel();
        }
        return true;
    }
    private boolean isUserBuyerModel(){
        if(sUsr2Buyr == null){
            message = "Please select other user";
            return false;
        }
        return true;
    }

    private boolean isUnitPurposeModel(){
        if(sPurposex == null){
            message = "Please select unit purpose";
            return false;
        }
        return true;
    }
    private boolean isPayer2BuyerModel(){
        if(sPyr2Buyr == null){
            message = "Please select other payer";
            return false;
        }
        return true;
    }
    private boolean isSourceModel(){
        if(source.equalsIgnoreCase("Others")){
            return isCompanyInfoSourceModel();

        }else if(source.trim().isEmpty()){
            message = "Please select source info";
            return false;
        }
        return true;
    }
    private boolean isCompanyInfoSourceModel(){
        if(companyInfoSource == null){
            message = "Please select company information source";
            return false;
        }
        return true;
    }

    private boolean isReferencesValid(){
        if(poRefInfo.size() < 3){
            message = "Please provide 3 personal references";
            return false;
        }
        return true;
    }
}
