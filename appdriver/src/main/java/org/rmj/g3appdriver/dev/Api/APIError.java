package org.rmj.g3appdriver.dev.Api;

public class APIError {

    public static String getErrorMessage(String args){
        switch (args){
            case "40020":
                return "Session expired. Please re-login your account.";
            default:
                return null;
        }
    }
}
