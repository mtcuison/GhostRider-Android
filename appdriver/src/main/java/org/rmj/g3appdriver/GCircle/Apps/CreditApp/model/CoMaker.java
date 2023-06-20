package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class CoMaker {

    private String TransNox = "";
    private String Relation = "";
    private String Incomexx = "";
    private String LastName = "";
    private String FrstName = "";
    private String MiddName = "";
    private String Suffix = "";
    private String NickName = "";
    private String BrthDate = "";
    private String BrthPlce = "";
    private String BirthPlc; //This holds the data for preview of Town, Province names.

    private MobileNo mobileNo1;
    private MobileNo mobileNo2;
    private MobileNo mobileNo3;

    private String EmailAdd = "";
    private String FbAccntx = "";
    private String VbrAccnt = "";

    //for save instance state
    private String ProvNme;
    private String TownNme;

    private String message;

    public CoMaker() {
    }

    public String getMessage(){
        return message;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getIncomexx() {
        return Incomexx;
    }

    public void setIncomexx(String incomexx) {
        Incomexx = incomexx;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFrstName() {
        return FrstName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public String getMiddName() {
        return MiddName;
    }

    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public String getSuffix() {
        return Suffix;
    }

    public void setSuffix(String suffix) {
        Suffix = suffix;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getBrthDate() {
        return BrthDate;
    }

    /**
     *
     * @return Use this get method to retrieve the UI preview of birthdate.
     */
    public String getBirthDte() {
        return BrthDate;
    }

    public void setBrthDate(String brthDate) {
        BrthDate = brthDate;
    }

    /***
     *
     * @param brthPlce set the actual ID of Town and Province using this method...
     */
    public void setBrthPlce(String brthPlce){
        BrthPlce = brthPlce;
    }

    /**
     *
     * @return get the actual ID of town which
     * will be the birthdate of applicant using this method...
     */
    public String getBrthPlce() {
        return BrthPlce;
    }

    /**
     *
     * @return get the town and
     * province name of the applicant birthplace using this method...
     */
    public String getBirthPlc() {
        return BirthPlc;
    }

    /**
     *
     * @param birthPlc set the town and
     *                 province name of applicant birthplace using this method...
     */
    public void setBirthPlc(String birthPlc) {
        BirthPlc = birthPlc;
    }

    public MobileNo getMobileNo1(){
        return mobileNo1;
    }

    public MobileNo getMobileNo2() {
        return mobileNo2;
    }

    public MobileNo getMobileNo3() {
        return mobileNo3;
    }

    public void setMobileNo1(MobileNo mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public void setMobileNo2(MobileNo mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public void setMobileNo3(MobileNo mobileNo3) {
        this.mobileNo3 = mobileNo3;
    }

    public String getEmailAdd() {
        return EmailAdd.trim();
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getFbAccntx() {
        return FbAccntx.trim();
    }

    public void setFbAccntx(String fbAccntx) {
        FbAccntx = fbAccntx;
    }

    public String getVbrAccnt() {
        return VbrAccnt;
    }

    public void setVbrAccnt(String vbrAccnt) {
        VbrAccnt = vbrAccnt;
    }

    public boolean isDataValid(){
        if(LastName.trim().isEmpty() || LastName.trim().equalsIgnoreCase("")){
            message = "Please enter last name";
            return false;
        }

        if(FrstName.trim().isEmpty() || FrstName.trim().equalsIgnoreCase("")){
            message = "Please enter first name";
            return false;
        }

//        if(BrthDate.trim().isEmpty() || BrthDate.trim().equalsIgnoreCase("")){
//            message = "Please enter birth date";
//            return false;
//        }

        if(BrthPlce == null || BrthPlce.equalsIgnoreCase("")){
            message = "Please enter birth place";
            return false;
        }

        if (mobileNo1 != null) {
            return isPrimaryContactValid() &&
                    isSecondaryContactValid() &&
                    isTertiaryContactValid();
        }else {
            message = "Please enter primary contact number";
            return false;
        }
    }

    private boolean isPrimaryContactValid(){
        if(mobileNo1 == null) {
            message = "Please enter primary contact number";
            return false;
        }

        if(!mobileNo1.isDataValid()){
            message = mobileNo1.getMessage();
            return false;
        }
        return true;
    }

    private boolean isSecondaryContactValid(){
        if(mobileNo2 != null) {
            if(!mobileNo2.isDataValid()){
                message = mobileNo2.getMessage();
                return false;
            }

            if (mobileNo2.getMobileNo().equalsIgnoreCase(mobileNo1.getMobileNo())) {
                message = "Contact numbers are duplicated";
                return false;
            }

            if(mobileNo3 != null) {
                if (mobileNo2.getMobileNo().equalsIgnoreCase(mobileNo3.getMobileNo())) {
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTertiaryContactValid(){
        if(mobileNo3 != null) {
            if(!mobileNo3.isDataValid()){
                message = mobileNo3.getMessage();
                return false;
            }

            if (mobileNo3.getMobileNo().equalsIgnoreCase(mobileNo1.getMobileNo())) {
                message = "Contact numbers are duplicated";
                return false;
            }

            if(mobileNo2 != null) {
                if (mobileNo3.getMobileNo().equalsIgnoreCase(mobileNo2.getMobileNo())) {
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isEqual(CoMaker val){
        if(!val.getLastName().equalsIgnoreCase(LastName)){
            return false;
        } else if(!val.getFrstName().equalsIgnoreCase(FrstName)){
            return false;
        } else if(!val.getMiddName().equalsIgnoreCase(MiddName)){
            return false;
        } else if(!val.getSuffix().equalsIgnoreCase(Suffix)){
            return false;
        } else if(!val.getNickName().equalsIgnoreCase(NickName)){
            return false;
        } else if(!val.getBirthDte().equalsIgnoreCase(BrthDate)){
            return false;
        } else if(!val.getBirthPlc().equalsIgnoreCase(BrthPlce)){
            return false;
        } else if(!val.getBirthPlc().equalsIgnoreCase(BirthPlc)){
            return false;
        } else if(!val.getEmailAdd().equalsIgnoreCase(EmailAdd)){
            return false;
        } else if(!val.getFbAccntx().equalsIgnoreCase(FbAccntx)){
            return false;
        } else if(!val.getVbrAccnt().equalsIgnoreCase(VbrAccnt)){
            return false;
        } else if(val.getMobileNo1() != null){
            if(!val.getMobileNo1().getMobileNo().equalsIgnoreCase(mobileNo1.getMobileNo())){
                return false;
            }
            if(val.getMobileNo1().getIsPostPd() != mobileNo1.getIsPostPd()){
                return false;
            }
            if(val.getMobileNo1().getPostYear() != mobileNo1.getPostYear()){
                return false;
            }
        } else if(val.getMobileNo2() != null){
            if(!val.getMobileNo2().getMobileNo().equalsIgnoreCase(mobileNo2.getMobileNo())){
                return false;
            }
            if(val.getMobileNo2().getIsPostPd() != mobileNo2.getIsPostPd()){
                return false;
            }
            if(val.getMobileNo2().getPostYear() != mobileNo2.getPostYear()){
                return false;
            }
        } else if(val.getMobileNo3() != null){
            if(!val.getMobileNo3().getMobileNo().equalsIgnoreCase(mobileNo3.getMobileNo())){
                return false;
            }
            if(val.getMobileNo3().getIsPostPd() != mobileNo3.getIsPostPd()){
                return false;
            }
            if(val.getMobileNo3().getPostYear() != mobileNo3.getPostYear()){
                return false;
            }
        }

        return true;
    }
}
