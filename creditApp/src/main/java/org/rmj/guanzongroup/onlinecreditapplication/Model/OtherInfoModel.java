/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class OtherInfoModel {

    //Spinner Values
    private String transNox;
    private String sUnitUser;
    private String sUsr2Buyr;
    private String sPurposex;
    private String sUnitPayr;
    private String sPyr2Buyr;
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
        if(poRefInfo.size() < 2){
            message = "Please provide 3 personal references";
            return false;
        }
        return true;
    }
}
