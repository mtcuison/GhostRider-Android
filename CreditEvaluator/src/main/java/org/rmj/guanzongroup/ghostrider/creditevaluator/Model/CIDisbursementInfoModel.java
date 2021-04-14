package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

public class CIDisbursementInfoModel {
    private String dbmWater;
    private String dbmElectricity;
    private String dbmFood;
    private String dbmLoans;

    public String getDbmWater() {
        return dbmWater;
    }

    public void setDbmWater(String dbmWater) {
        this.dbmWater = dbmWater;
    }

    public String getDbmElectricity() {
        return dbmElectricity;
    }

    public void setDbmElectricity(String dbmElectricity) {
        this.dbmElectricity = dbmElectricity;
    }

    public String getDbmFood() {
        return dbmFood;
    }

    public void setDbmFood(String dbmFood) {
        this.dbmFood = dbmFood;
    }

    public String getDbmLoans() {
        return dbmLoans;
    }

    public void setDbmLoans(String dbmLoans) {
        this.dbmLoans = dbmLoans;
    }

    public String getDbmEducation() {
        return dbmEducation;
    }

    public void setDbmEducation(String dbmEducation) {
        this.dbmEducation = dbmEducation;
    }

    public String getDbmOthers() {
        return dbmOthers;
    }

    public void setDbmOthers(String dbmOthers) {
        this.dbmOthers = dbmOthers;
    }

    public String getDbmTotalExpenses() {
        return dbmTotalExpenses;
    }

    public void setDbmTotalExpenses(String dbmTotalExpenses) {
        this.dbmTotalExpenses = dbmTotalExpenses;
    }

    private String dbmEducation;
    private String dbmOthers;
    private String dbmTotalExpenses;
    private String message;
    public CIDisbursementInfoModel() {
    }

    public String getMessage() { return message; }
}
