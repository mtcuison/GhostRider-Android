package org.rmj.g3appdriver.lib.Account.Model;

public interface iAccount {
    boolean Login(Object args);
    boolean CreateAccount(Object args);
    boolean ForgotPassword(Object args);

    String getMessage();
}
