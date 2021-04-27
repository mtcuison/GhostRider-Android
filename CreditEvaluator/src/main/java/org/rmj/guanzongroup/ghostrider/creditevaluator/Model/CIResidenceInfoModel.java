/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

public class CIResidenceInfoModel {

    String TransNox;
    String LandMark;
    String Ownershp;
    String OwnOther;
    String HouseTyp;
    String Garagexx;
    String Latitude;
    String Longitud;
    String message;
    public CIResidenceInfoModel() {
    }

    public boolean isValidData(){
        return isLandmark() &&
            isOwnershp() &&
            isOtherOwner() &&
            isHouseTyp() &&
            isGaragexx() &&
            isLatLongg();
    }

    public String getMessage() { return message; }
    private boolean isLandmark(){
        if (this.LandMark.trim().isEmpty()){
            message = "Please enter landmark.";
            return false;
        }
        return true;
    }
    private boolean isOwnershp(){
        if (this.Ownershp == null){
            message = "Please select House ownership.";
            return false;
        }
        return true;
    }
    private boolean isOtherOwner(){
        if (this.OwnOther == null){
            message = "Please select household.";
            return false;
        }
        return true;
    }private boolean isHouseTyp(){
        if (this.HouseTyp == null){
            message = "Please select house type.";
            return false;
        }
        return true;
    }
    private boolean isGaragexx(){
        if (this.Garagexx == null){
            message = "Please select if available house garage.";
            return false;
        }
        return true;
    }

    private boolean isLatLongg(){
        if (this.Longitud == null || this.Latitude == null || this.Longitud == null || this.Latitude == null){
            message = "empty";
            return false;
        }
        return true;
    }





    public String getTransNox() {
        return this.TransNox;
    }

    public void setTransNox(String transNox) {
        this.TransNox = transNox;
    }

    public String getLandMark() {
        return this.LandMark;
    }

    public void setLandMark(String landMark) {
        this.LandMark = landMark;
    }

    public String getOwnershp() {
        return this.Ownershp;
    }

    public void setOwnershp(String ownershp) {
        this.Ownershp = ownershp;
    }

    public String getOwnOther() {
        return this.OwnOther;
    }

    public void setOwnOther(String ownOther) {
        this.OwnOther = ownOther;
    }

    public String getHouseTyp() {
        return this.HouseTyp;
    }

    public void setHouseTyp(String houseTyp) {
        this.HouseTyp = houseTyp;
    }

    public String getGaragexx() {
        return this.Garagexx;
    }

    public void setGaragexx(String garagexx) {
        this.Garagexx = garagexx;
    }

    public String getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(String latitude) {
        this.Latitude = latitude;
    }

    public String getLongitud() {
        return this.Longitud;
    }

    public void setLongitud(String longitud) {
        this.Longitud = longitud;
    }



}
