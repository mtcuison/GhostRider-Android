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

public class CIDisbursementInfoModel {

    private String ciDbmWater;
    private String ciDbmElectricity;
    private String ciDbmFood;
    private String ciDbmLoans;

    private String ciDbmEducation;
    private String ciDbmOthers;
    private String ciDbmTotalExpenses;
    private String message;
    public CIDisbursementInfoModel() {
    }

    public String getMessage() { return message; }

    public String getCiDbmWater() {
        return ciDbmWater;
    }

    public void setCiDbmWater(String ciDbmWater) {
        this.ciDbmWater = ciDbmWater;
    }

    public String getCiDbmElectricity() {
        return ciDbmElectricity;
    }

    public void setCiDbmElectricity(String ciDbmElectricity) {
        this.ciDbmElectricity = ciDbmElectricity;
    }

    public String getCiDbmFood() {
        return ciDbmFood;
    }

    public void setCiDbmFood(String ciDbmFood) {
        this.ciDbmFood = ciDbmFood;
    }

    public String getCiDbmLoans() {
        return ciDbmLoans;
    }

    public void setCiDbmLoans(String ciDbmLoans) {
        this.ciDbmLoans = ciDbmLoans;
    }

    public String getCiDbmEducation() {
        return ciDbmEducation;
    }

    public void setCiDbmEducation(String ciDbmEducation) {
        this.ciDbmEducation = ciDbmEducation;
    }

    public String getCiDbmOthers() {
        return ciDbmOthers;
    }

    public void setCiDbmOthers(String ciDbmOthers) {
        this.ciDbmOthers = ciDbmOthers;
    }

    public String getCiDbmTotalExpenses() {
        return ciDbmTotalExpenses;
    }

    public void setCiDbmTotalExpenses(String ciDbmTotalExpenses) {
        this.ciDbmTotalExpenses = ciDbmTotalExpenses;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
