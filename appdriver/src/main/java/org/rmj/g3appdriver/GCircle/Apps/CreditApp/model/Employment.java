package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

import android.util.Log;

public class Employment {

    private String sTransNox = "";

    private String cMeanInfo = "";
    private String sSectorxx = "";
    private String cUniformP = "";
    private String cMilitary = "";
    private String cCompLevl = "";
    private String cEmpLevel = "";
    private String sBusiness = "";
    private String sCountryx = "";
    private String sCompName = "";
    private String sCompAddx = "";
    private String sProvIDxx = "";
    private String sTownIDxx = "";
    private String sJobTitle = "";
    private String sSpceficx = "";
    private String cStatusxx = "";
    private double sLengthxx = 0;
    private String cIsYearxx = "";
    private long sMonthlyx = 0;
    private String sContactx = "";

    private String sProvName = "";
    private String sTownName = "";
    private String sJobNamex = "";
    private String sCountryN = "";

    private String message;

    public Employment() {
    }

    public String getMessage() {
        return message;
    }

    public String getcMeanInfo() {
        return cMeanInfo;
    }

    public void setcMeanInfo(String cMeanInfo) {
        this.cMeanInfo = cMeanInfo;
    }


    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getEmploymentSector() {
        return sSectorxx;
    }

    public void setEmploymentSector(String sSectorxx) {
        this.sSectorxx = sSectorxx;
    }

    public String getUniformPersonal() {
        return cUniformP;
    }

    public void setUniformPersonal(String cUniformP) {
        this.cUniformP = cUniformP;
    }

    public String getMilitaryPersonal() {
        return cMilitary;
    }

    public void setMilitaryPersonal(String cMilitary) {
        this.cMilitary = cMilitary;
    }

    public String getCompanyLevel() {
        if(sSectorxx.equalsIgnoreCase("1")) {
            return cCompLevl;
        }
        return "";
    }

    public String getGovermentLevel(){
        if(sSectorxx.equalsIgnoreCase("0")) {
            return cCompLevl;
        }
        return "";
    }

    public String getOfwRegion(){
        if(sSectorxx.equalsIgnoreCase("2")) {
            return cCompLevl;
        }
        return "";
    }

    public void setCompanyLevel(String cCompLevl) {
        this.cCompLevl = cCompLevl;
    }

    public String getEmployeeLevel() {
        return cEmpLevel;
    }

    public String getOfwWorkCategory(){
        if(sSectorxx.equalsIgnoreCase("2")){
            return cEmpLevel;
        }
        return "";
    }

    public void setEmployeeLevel(String cEmpLevel) {
        this.cEmpLevel = cEmpLevel;
    }

    public String getBusinessNature() {
        if(sSectorxx.equalsIgnoreCase("1")) {
            return sBusiness;
        }
        return "";
    }

    public void setBusinessNature(String sBusiness) {
        this.sBusiness = sBusiness;
    }

    public String getCountry() {
        if(sSectorxx.equalsIgnoreCase("2")) {
            return sCountryx;
        }
        return "";
    }

    public void setCountry(String sCountryx) {
        this.sCountryx = sCountryx;
    }

    public String getCompanyName() {
        return sCompName;
    }

    public void setCompanyName(String sCompName) {
        this.sCompName = sCompName;
    }

    public String getCompanyAddress() {
        return sCompAddx.trim();
    }

    public void setCompanyAddress(String sCompAddx) {
        this.sCompAddx = sCompAddx;
    }

    public String getProvinceID() {
        return sProvIDxx;
    }

    public void setProvinceID(String sProvIDxx) {
        this.sProvIDxx = sProvIDxx;
    }

    public String getTownID() {
        return sTownIDxx;
    }

    public void setTownID(String sTownIDxx) {
        this.sTownIDxx = sTownIDxx;
    }

    public String getJobTitle() {
        return sJobTitle;
    }

    public void setJobTitle(String sJobTitle) {
        this.sJobTitle = sJobTitle;
    }

    public String getSpecificJob() {
        return sSpceficx.trim();
    }

    public void setSpecificJob(String sSpceficx) {
        this.sSpceficx = sSpceficx;
    }

    public String getEmployeeStatus() {
        return cStatusxx;
    }

    public void setEmployeeStatus(String cStatusxx) {
        this.cStatusxx = cStatusxx;
    }

    public double getLengthOfService() {
        if(!sSectorxx.equalsIgnoreCase("2")) {
            try {
                if(!"".equalsIgnoreCase(cIsYearxx)){
                    if (Integer.parseInt(cIsYearxx) == 0) {
                        double ldValue = sLengthxx;
                        return ldValue / 12;
                    } else {
                        return sLengthxx;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public void setLengthOfService(double sLengthxx) {
        this.sLengthxx = sLengthxx;
    }

    public void setIsYear(String cIsYearxx){
        this.cIsYearxx = cIsYearxx;
    }

    public long getMonthlyIncome() {
        if(sMonthlyx != 0) {
            return sMonthlyx;
        }
        return 0;
    }

    public void setMonthlyIncome(long sMonthlyx) {
        this.sMonthlyx = sMonthlyx;
    }

    public void setContact(String sContactx) {
        this.sContactx = sContactx;
    }
    public String getContact() {
        return sContactx;
    }



    public boolean isDataValid(){
        return  isEmploymentSectorValid() &&
                isUniformPersonalValid() &&
                isMilitaryPersonalValid() &&
                isCompanyLevelValid() &&
                isEmployeeLevelValid() &&
                isCountryValid() &&
                isBusinessNatureValid() &&
                isCompanyNameValid() &&
                isCompanyAddressValid() &&
                isJobTitleValid() &&
                isSpecificJobValid() &&
                isEmploymentStatusValid() &&
                isLengthOfServiceValid() &&
                isSpnLengthOfServiceValid() &&
                isMonthlySalaryValid() &&
                isCompanyContactValid();
    }

    public boolean isPrimary(){
        Log.e("means = ", cMeanInfo);
       if (!cMeanInfo.equalsIgnoreCase("0")){
           return false;
       }
       return true;

    }

    private boolean isEmploymentSectorValid(){
        if(sSectorxx.trim().isEmpty() || sSectorxx.equalsIgnoreCase("")){
                message = "Please select Employment Sector";
                return false;
        }
        return true;
    }

    private boolean isUniformPersonalValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if(cUniformP == null || cUniformP.equalsIgnoreCase("")){
                message = "Please select if uniform personnel";
                return false;
            }
        }
        return true;
    }

    private boolean isMilitaryPersonalValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if(cMilitary == null || cMilitary.trim().equalsIgnoreCase("")){
                message = "Please select if military personnel";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyLevelValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if (cCompLevl == null || cCompLevl.trim().equalsIgnoreCase("")) {
                message = "Please select government level";
                return false;
            }
        } else if(sSectorxx.equalsIgnoreCase("1")){
            if(cCompLevl == null || cCompLevl.trim().equalsIgnoreCase("")){
                message = "Please select company level";
                return false;
            }
        } else if (sSectorxx.equalsIgnoreCase("2")){
            if(cCompLevl == null || cCompLevl.trim().equalsIgnoreCase("")){
                message = "Please select ofw region";
                return false;
            }
        }
        return true;
    }

    private boolean isEmployeeLevelValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if(cEmpLevel == null || cEmpLevel.trim().equalsIgnoreCase("")){
                message = "Please select government level";
                return false;
            }
        } else if(sSectorxx.equalsIgnoreCase("1")){
            if(cEmpLevel == null || cEmpLevel.trim().equalsIgnoreCase("")){
                message = "Please select employee level";
                return false;
            }
        } else if (sSectorxx.equalsIgnoreCase("2")){
            if(cEmpLevel == null || cEmpLevel.trim().equalsIgnoreCase("")){
                message = "Please select ofw region";
                return false;
            }
        }
        return true;
    }

    private boolean isCountryValid(){
        if(sSectorxx.equalsIgnoreCase("2")){
            if(sCountryx == null || sCountryx.equalsIgnoreCase("")){
                message = "Please select country.";
                return false;
            }
        }
        return true;
    }

    private boolean isBusinessNatureValid(){
        if(sSectorxx.equalsIgnoreCase("1")){
            if(sBusiness == null || sBusiness.equalsIgnoreCase("")){
                message = "Please select business nature";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyNameValid(){
        if(sSectorxx.equalsIgnoreCase("1")){
            if(sCompName == null || sCompName.equalsIgnoreCase("")){
                message = "Please enter company name";
                return false;
            }
        } else if(sSectorxx.equalsIgnoreCase("0")){
            if(sCompName == null || sCompName.equalsIgnoreCase("")){
                message = "Please enter government institution";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyAddressValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
//            if (sProvIDxx == null || sProvIDxx.equalsIgnoreCase("")) {
//                message = "Please enter company province address";
//                return false;
//            }
            if (sTownName == null || sTownName.equalsIgnoreCase("")) {
                message = "Please enter company Municipality address";
                return false;
            }
        }
        return true;
    }

    private boolean isJobTitleValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sJobTitle == null || sJobTitle.equalsIgnoreCase("")) {
                message = "Please enter job title";
                return false;
            }
        }
        return true;
    }

    private boolean isSpecificJobValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sSpceficx == null || sSpceficx.equalsIgnoreCase("")) {
                message = "Please enter specific job";
                return false;
            }
        }
        return true;
    }

    private boolean isEmploymentStatusValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (cStatusxx == null || cStatusxx.equalsIgnoreCase("")) {
                message = "Please select employment status";
                return false;
            }
        }
        return true;
    }

    private boolean isLengthOfServiceValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sLengthxx == 0) {
                message = "Please enter length of service";
                return false;
            }
        }
        return true;
    }
    private boolean isSpnLengthOfServiceValid(){
        if(sSectorxx.equalsIgnoreCase("1") || sSectorxx.equalsIgnoreCase("0")) {
            if (cIsYearxx == null || cIsYearxx.isEmpty()) {
                message = "Please enter length of service\n Month/Year";
                return false;
            }
        }
        return true;
    }

    private boolean isMonthlySalaryValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sMonthlyx == 0) {
                message = "Please enter estimated monthly income";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyContactValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sContactx == null || sContactx.equalsIgnoreCase("")) {
                message = "Please enter company contact no.";
                return false;
            }
            if(sContactx.length() != 11){
                message = "Please enter complete company contact no.";
                return false;
            }
            if(!sContactx.substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }
        }
        return true;
    }

    public String getsProvName() {
        return sProvName;
    }

    public void setProvName(String sProvName) {
        this.sProvName = sProvName;
    }

    public String getsTownName() {
        return sTownName;
    }

    public void setTownName(String sTownName) {
        this.sTownName = sTownName;
    }

    public String getsJobNamex() {
        return sJobNamex;
    }

    public void setsJobName(String sJobNamex) {
        this.sJobNamex = sJobNamex;
    }

    /**
     *
     * @return call this method to get the UI preview of the selected country for OFW
     */
    public String getsCountryN() {
        return sCountryN;
    }

    /**
     *
     * @param sCountryN set the country name for UI preview
     */
    public void setsCountryN(String sCountryN) {
        this.sCountryN = sCountryN;
    }
}
