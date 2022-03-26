package org.rmj.g3appdriver.lib.account;

import org.json.JSONObject;

public class AccountCredentials{
    private String sLastName = "";
    private String sFrstName = "";
    private String sMiddName = "";
    private String sEmailAdd = "";
    private String sPassword = "";
    private String sPasswrd2 = "";
    private String sMobileNo = "";

    private String message;

    public AccountCredentials() {
    }

    public String getMessage() {
        return message;
    }

    public String getsLastName() {
        return sLastName;
    }

    public void setLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getsFrstName() {
        return sFrstName;
    }

    public void setFrstName(String sFrstName) {
        this.sFrstName = sFrstName;
    }

    public String getsMiddName() {
        return sMiddName;
    }

    public void setMiddName(String sMiddName) {
        this.sMiddName = sMiddName;
    }

    public String getsEmailAdd() {
        return sEmailAdd;
    }

    public void setEmailAdd(String sEmailAdd) {
        this.sEmailAdd = sEmailAdd;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsPasswrd2() {
        return sPasswrd2;
    }

    public void setPasswrd2(String sPasswrd2) {
        this.sPasswrd2 = sPasswrd2;
    }

    public boolean isDataValid(){
        if(sLastName.isEmpty()){
            message = "Please enter last name";
            return false;
        } else if(sFrstName.isEmpty()){
            message = "Please enter first name";
            return false;
        } else if(sMiddName.isEmpty()){
            message = "Please enter middle name";
            return false;
        } else if(sEmailAdd.isEmpty()){
            message = "Please enter email";
            return false;
        } else if(sPassword.isEmpty()){
            message = "Please enter password";
            return false;
        } else if(!sPassword.equalsIgnoreCase(sPasswrd2)){
            message = "Passwords does not match";
            return false;
        } else if(sPassword.length() < 6){
            message = "Password is too short";
            return false;
        } else if(sMobileNo.isEmpty()){
            message = "Please enter mobile no";
            return false;
        } else if(!sMobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile number must start with '09'";
            return false;
        } else if(sMobileNo.length() != 11){
            message = "Mobile number must be 11 characters";
            return false;
        } else {
            return true;
        }
    }

    public String getParameters() throws Exception{
        JSONObject params = new JSONObject();
        params.put("name", getFullName());
        params.put("mail", sEmailAdd);
        params.put("pswd", sPassword);
        params.put("mobile", sMobileNo);
        return params.toString();
    }

    private String getFullName(){
        return sLastName + ", " + sFrstName + " " + sMiddName;
    }
}