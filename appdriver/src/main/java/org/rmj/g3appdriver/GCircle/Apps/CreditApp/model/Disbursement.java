package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class Disbursement {
    private String TransNox = "";
    private double Electric = 0;
    private double WaterExp = 0;
    private double FoodExps = 0;
    private double LoanExps = 0;
    private String BankName = "";
    private String AcctType = "";
    private String CrdtBank = "";
    private double CrdtLimt = 0;
    private int CrdtYear = 0;

    private String message = "";

    public Disbursement() {
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getMessage() {
        return message;
    }

    public double getElectric() {
        return Electric;
    }

    public void setElectric(double electric) {
        Electric = electric;
    }

    public double getWaterExp() {
        return WaterExp;
    }

    public void setWaterExp(double waterExp) {
        WaterExp = waterExp;
    }

    public double getFoodExps() {
        return FoodExps;
    }

    public void setFoodExps(double foodExps) {
        FoodExps = foodExps;
    }

    public double getLoanExps() {
        return LoanExps;
    }

    public void setLoanExps(double loanExps) {
        LoanExps = loanExps;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getAcctType() {
        return AcctType;
    }

    public void setAcctType(String acctType) {
        AcctType = acctType;
    }

    public String getCrdtBank() {
        return CrdtBank;
    }

    public void setCrdtBank(String crdtBank) {
        CrdtBank = crdtBank;
    }

    public double getCrdtLimt() {
        return CrdtLimt;
    }

    public void setCrdtLimt(double crdtLimt) {
        CrdtLimt = crdtLimt;
    }

    public int getCrdtYear() {
        return CrdtYear;
    }

    public void setCrdtYear(int crdtYear) {
        CrdtYear = crdtYear;
    }


    public boolean isDataValid(){
        return isBankN() &&
                isCCBank();
    }
    private boolean isBankN(){
        if(!BankName.trim().isEmpty() || !BankName.equalsIgnoreCase("")){
            return  isTypeX();
        }
        return true;
    }
    private boolean isTypeX(){
        if(AcctType.trim().isEmpty() || AcctType.equalsIgnoreCase("" )){
            message = "Please select account type";
            return false;
        }
        return true;
    }
    private boolean isCCBank(){
        if(!CrdtBank.trim().isEmpty() || !CrdtBank.equalsIgnoreCase("")){
            return isAmount() && isYear();
        }
        return true;
    }
    private boolean isAmount(){
        if(CrdtLimt == 0 ){
            message = "Please enter Amount";
            return false;
        }
        return true;
    }
    private boolean isYear(){
        if(CrdtYear == 0 ){
            message = "Please enter Length of Use";
            return false;
        }
        return true;
    }
}
