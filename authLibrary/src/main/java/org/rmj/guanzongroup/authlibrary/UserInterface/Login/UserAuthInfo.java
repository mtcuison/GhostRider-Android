package org.rmj.guanzongroup.authlibrary.UserInterface.Login;

public class UserAuthInfo {
    private final String Email;
    private final String Password;

    private String message;

    public UserAuthInfo(String email, String password, String mobileNo) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getMessage(){
        return message;
    }

    public boolean isAuthInfoValid(){
        if(!isEmailValid()){
            return false;
        }
        return isPasswordValid();
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
}
