package org.rmj.guanzongroup.authlibrary.UserInterface.CreateAccount;

interface CreateAccountCallBack {
    void OnAccountLoad(String Title, String Message);
    void OnSuccessRegistration();
    void OnFailedRegistration(String message);
}
