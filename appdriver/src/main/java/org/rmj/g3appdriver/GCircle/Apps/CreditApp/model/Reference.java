package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class Reference {

    private String Fullname = "";
    private String Address1 = "";
    private String TownCity = "";
    private String TownName = "";
    private String ContactN = "";
    public Reference(){

    }
    public Reference(String fullname, String address1, String townCity, String townName, String contactN) {
        Fullname = fullname;
        Address1 = address1;
        TownCity = townCity;
        TownName = townName;
        ContactN = contactN;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getTownCity() {
        return TownCity;
    }

    public void setTownCity(String townCity) {
        TownCity = townCity;
    }

    public String getTownName() {
        return TownName;
    }

    public void setTownName(String townName) {
        TownName = townName;
    }

    public String getContactN() {
        return ContactN;
    }

    public void setContactN(String contactN) {
        ContactN = contactN;
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
