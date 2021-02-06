package org.rmj.guanzongroup.onlinecreditapplication.Model;

import java.util.Objects;

public class OtherInfoModel {
    private String Fullname;
    private String Address1;
    private String TownCity;
    private String ContactN;
    private String message;

    //Spinner Values
    private String unitUser;
    private String userBuyer;
    private String unitPurpose;
    private String monthlyPayer;
    private String payer2Buyer;
    private String source;
    private String companyInfoSource;


    public OtherInfoModel(){

    }


//    public OtherInfoModel(String unitUser,
//                          String userBuyer,
//                          String unitPurpose,
//                          String monthlyPayer,
//                          String payer2Buyer,
//                          String source,
//                          String companyInfoSource){
//
//        this.unitUser = unitUser;
//        this.userBuyer = userBuyer;
//        this.unitPurpose = unitPurpose;
//        this.monthlyPayer = monthlyPayer;
//        this.payer2Buyer = payer2Buyer;
//        this.source = source;
//        this.companyInfoSource = companyInfoSource;
//
//    }

    public OtherInfoModel(String fullname,
                              String address1,
                              String townCity,
                              String contactN){
        this.Fullname = fullname;
        this.Address1 = address1;
        this.TownCity = townCity;
        this.ContactN = contactN;
    }

    public String getMessage() {
        return message;
    }
    public String getFullname() {
        return Fullname;
    }

    public String getAddress1() {
        return Address1;
    }

    public String getTownCity() {
        return TownCity;
    }

    public String getContactN() {
        return ContactN;
    }
//  Spinner Set Value
    public void setUnitUserModel(String type) {
         this.unitUser = type;
    }

    public void setUserBuyerModel(String type) {
        this.userBuyer = type;
    }

    public void setUserUnitPurposeModel(String type) {
        this.unitPurpose = type;
    }

    public void setMonthlyPayerModel(String type) {
        this.monthlyPayer = type;
    }

    public void setPayer2BuyerModel(String type) {
        this.payer2Buyer = type;
    }

    public void setSourceModel(String type) {
        this.source = type;
    }

    public void setCompanyInfoSourceModel(String type) {
         this.companyInfoSource = type;
    }

    //Spinner get Values
    public String getUnitUserModel() {
        return unitUser;
    }

    public String getUserBuyerModel() {
        return userBuyer;
    }

    public String getUserUnitPurposeModel() {
        return unitPurpose;
    }

    public String getMonthlyPayerModel() {
        return monthlyPayer;
    }

    public String getPayer2BuyerModel() {
        return payer2Buyer;
    }

    public String getSourceModel() {
        return source;
    }

    public String getCompanyInfoSourceModel() {
        return companyInfoSource;
    }
    public boolean isValidSpinner(){
        return isUnitUserModel() &&
                isUnitPurposeModel() &&
                isMonthlyPayerModel() &&
                isSourceModel();
    }

    private boolean isUnitUserModel(){
        if(Integer.parseInt(unitUser) < 0){
            message = "Please select unit user";
            return false;
        }
        else if(Integer.parseInt(unitUser) == 1){
            return isUserBuyerModel();
        }
        return true;
    }
    private boolean isUserBuyerModel(){
        if(Integer.parseInt(userBuyer) < 0){
            message = "Please select other user";
            return false;
        }
        return true;
    }

    private boolean isUnitPurposeModel(){
        if(Integer.parseInt(unitPurpose) < 0 ){
            message = "Please select unit purpose";
            return false;
        }
        return true;
    }
    private boolean isMonthlyPayerModel(){
        if(Integer.parseInt(monthlyPayer) < 0){
            message = "Please select monthly";
            return false;
        }else if(Integer.parseInt(monthlyPayer) == 1){
            return isPayer2BuyerModel();
        }
        return true;
    }
    private boolean isPayer2BuyerModel(){
        if(Integer.parseInt(payer2Buyer) < 0){
            message = "Please select other payer";
            return false;
        }
        return true;
    }
    private boolean isSourceModel(){
        if(source.equalsIgnoreCase("Others")){
            return isCompanyInfoSourceModel();

        }else if(source.equalsIgnoreCase("Select company information source")){
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


    public boolean isValidReferences(){
        return isFullname() &&
                isContactValid() &&
                isAddress1() &&
                isTownCity();
    }

    private boolean isFullname(){
        if(Fullname.trim().isEmpty()){
            message = "Please enter reference fullname!";
            return false;
        }
        return true;
    }
    private boolean isAddress1(){
        if(Address1.trim().isEmpty()){
            message = "Please enter Address!";
            return false;
        }
        return true;
    }
    private boolean isTownCity(){
        if(TownCity.trim().isEmpty()){
            message = "Please enter Municipality!";
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        if(ContactN.trim().isEmpty()){
            message = "Please enter Contact Number!";
            return false;
        } else if (!ContactN.trim().substring(0,2).equalsIgnoreCase("09")){
            message = "Contact number must start with " + ContactN.trim().substring(0,2);
            return false;
        } else if(ContactN.length() != 11){
            message = "Please provide valid contact no!";
            return false;
        }
        return true;
    }

}
