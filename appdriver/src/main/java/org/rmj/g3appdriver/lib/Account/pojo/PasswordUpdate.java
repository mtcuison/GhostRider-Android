package org.rmj.g3appdriver.lib.Account.pojo;

public class PasswordUpdate {
    private final String sOldPwrdx;
    private final String sNewPwrdx;
    private final String sPwrdCfrm;

    private String message;

    public PasswordUpdate(String OldPassword, String NewPassword, String ConfirmPassword) {
        this.sOldPwrdx = OldPassword;
        this.sNewPwrdx = NewPassword;
        this.sPwrdCfrm = ConfirmPassword;
    }

    public String getMessage() {
        return message;
    }

    public String getOldPassword() {
        return sOldPwrdx;
    }

    public String getNewPassword() {
        return sNewPwrdx;
    }

    public boolean isDataValid(){
        if(sOldPwrdx.isEmpty()){
            message = "Please enter old password";
            return false;
        }
        if(sNewPwrdx.isEmpty()){
            message = "Please enter new password";
            return false;
        }
        if(sPwrdCfrm.isEmpty()){
            message = "Please confirm new password";
            return false;
        }
        if(!sNewPwrdx.equalsIgnoreCase(sPwrdCfrm)){
            message = "New password does not match";
            return false;
        }
        if(sNewPwrdx.length() < 8){
            message = "Please enter atleast 8 characters password";
            return false;
        }
        if(sNewPwrdx.length() > 15){
            message = "Password is too long. Must be less than 15 characters";
            return false;
        }
        return true;
    }
}
