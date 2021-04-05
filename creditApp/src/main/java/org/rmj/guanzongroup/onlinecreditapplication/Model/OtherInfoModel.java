package org.rmj.guanzongroup.onlinecreditapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class OtherInfoModel {

    //Spinner Values
    private String unitUser;
    private String transNox;
    private String unitPayr;
    private String unitPrps;
    private String payrRltn;
    private String source;
    private String companyInfoSource;

    private List<PersonalReferenceInfoModel> poRefInfo;

    private String message;

    public OtherInfoModel(){
        poRefInfo = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setPersonalReferences(List<PersonalReferenceInfoModel> poRefInfo){
        this.poRefInfo = poRefInfo;
    }

    public String getTransNox() {
        return this.transNox;
    }

    public void setTransNox(String transNox) {
        this.transNox = transNox;
    }

    public String getUnitUser() {
        return unitUser;
    }

    public void setUnitUser(String unitUser) {
        this.unitUser = unitUser;
    }

    public String getUnitPayr() {
        return unitPayr;
    }

    public void setUnitPayr(String unitPayr) {
        this.unitPayr = unitPayr;
    }

    public String getUnitPrps() {
        return unitPrps;
    }

    public void setUnitPrps(String unitPrps) {
        this.unitPrps = unitPrps;
    }

    public String getPayrRltn() {
        return payrRltn;
    }

    public void setPayrRltn(String payrRltn) {
        this.payrRltn = payrRltn;
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
        if(unitUser == null){
            message = "Please select unit user";
            return false;
        } else if(Integer.parseInt(unitUser) == 1){
            return isUserBuyerModel();
        }
        return true;
    }
    private boolean isUserBuyerModel(){
        if(unitPayr == null){
            message = "Please select other user";
            return false;
        }
        return true;
    }

    private boolean isUnitPurposeModel(){
        if(unitPrps == null){
            message = "Please select unit purpose";
            return false;
        }
        return true;
    }
    private boolean isPayer2BuyerModel(){
        if(payrRltn == null){
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
        if(companyInfoSource == null || companyInfoSource.isEmpty()){
            message = "Please select company information source";
            return false;
        }
        return true;
    }

    private boolean isReferencesValid(){
        if(poRefInfo.size() < 2){
            message = "Please provide 3 personal references";
            return false;
        }
        return true;
    }
}
