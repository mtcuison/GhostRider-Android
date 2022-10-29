package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class Reference {

    private final String Fullname;
    private final String Address1;
    private final String TownCity;
    private final String ContactN;

    public Reference(String fullname, String address1, String townCity, String contactN) {
        Fullname = fullname;
        Address1 = address1;
        TownCity = townCity;
        ContactN = contactN;
    }

    private String message;

    public String getMessage() {
        return message;
    }
    public String getFullname() {
        return Fullname.trim();
    }

    public String getAddress1() {
        return Address1.trim();
    }

    public String getTownCity() {
        return TownCity;
    }

    public String getContactN() {
        return ContactN;
    }

    public boolean isDataValid(){
        return isFullname() &&
                isAddress1() &&
                isContactValid() &&
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
