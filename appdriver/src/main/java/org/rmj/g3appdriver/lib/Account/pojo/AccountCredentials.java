package org.rmj.g3appdriver.lib.Account.pojo;

public class AccountCredentials{
    private String sUserName = "";
    private String sEmailAdd = "";
    private String sPassword = "";
    private String sPasswrd2 = "";
    private String sMobileNo = "";
    private String cAgreeTnC = "";

    private String message;

    public AccountCredentials() {
    }

    public String getMessage() {
        return message;
    }

    public String getsUserName() {
        return sUserName;
    }

    public void setUserName(String sLastName) {
        this.sUserName = sLastName;
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

    public String getcAgreeTnC() {
        return cAgreeTnC;
    }

    public void setcAgreeTnC(String cAgreeTnC) {
        this.cAgreeTnC = cAgreeTnC;
    }

    public boolean isDataValid(){
        if(sUserName.isEmpty()){
            message = "Please enter username";
            return false;
        } else if(sEmailAdd.isEmpty()){
            message = "Please enter email address";
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
        } else if(sPassword.isEmpty()){
            message = "Please enter password";
            return false;
        }  else if(sPassword.length() < 6){
            message = "Password is too short";
            return false;
        } else if(sPasswrd2.isEmpty()) {
            message = "Please re-type password";
            return false;
        } else if(!sPassword.equals(sPasswrd2)){
            message = "Passwords does not match";
            return false;
        } else if(!cAgreeTnC.equalsIgnoreCase("1")){
            message = "Please indicate that you have agree to the Terms & Conditions and Privacy Policy";
            return false;
        } else {
            return true;
        }
    }
}