package org.rmj.g3appdriver.lib.Account.pojo;

public class UserAuthInfo {
    private final String Email;
    private final String Password;
    private final String MobileNo;
    private String message;

    public UserAuthInfo(String email, String password, String mobileNo) {
        Email = email;
        Password = password;
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getMessage(){
        return message;
    }

    public boolean isAuthInfoValid(){
        if(!isEmailValid()){
            return false;
        }
        if(!isPasswordValid()){
            return false;
        }
        return isMobileNoValid();
    }

    private boolean isEmailValid(){
        if(Email.isEmpty()){
            message = "Please enter your email";
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(){
        if(Password.isEmpty()) {
            message = "Please enter password";
            return false;
        }
        return true;
    }

    private boolean isMobileNoValid(){
        if(MobileNo.isEmpty()){
            message = "Please enter mobile no.";
            return false;
        }
        if(MobileNo.length()!=11){
            message = "Mobile number must be 11 characters";
            return false;
        }
        if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile number must start with '09'";
            return false;
        }
        return true;
    }
}