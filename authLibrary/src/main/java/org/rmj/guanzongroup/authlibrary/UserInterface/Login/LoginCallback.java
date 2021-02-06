package org.rmj.guanzongroup.authlibrary.UserInterface.Login;

public interface LoginCallback {
    void OnAuthenticationLoad(String Title, String Message);
    void OnSuccessLoginResult();
    void OnFailedLoginResult(String message);
}
