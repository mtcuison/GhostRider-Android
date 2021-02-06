package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class EmploymentInfoModel {
    private String sSectorxx;
    private String cUniformP;
    private String cMilitary;
    private String cCompLevl;
    private String cEmpLevel;
    private String sBusiness;
    private String sCountryx;
    private String sCompName;
    private String sCompAddx;
    private String sProvIDxx;
    private String sTownIDxx;
    private String sJobTitle;
    private String sSpceficx;
    private String cStatusxx;
    private String sLengthxx;
    private String cIsYearxx;
    private String sMonthlyx;
    private String sContactx;

    private String message;

    public EmploymentInfoModel() {

    }

    public String getMessage() {
        return message;
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
        return sCompAddx;
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
        return sSpceficx;
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
        try{
            if(Integer.parseInt(cIsYearxx) == 0) {
                double ldValue = Double.parseDouble(sLengthxx);
                return ldValue / 12;
            } else {
                return Double.parseDouble(sLengthxx);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setLengthOfService(String sLengthxx) {
        this.sLengthxx = sLengthxx;
    }

    public void setIsYear(String cIsYearxx){
        this.cIsYearxx = cIsYearxx;
    }

    public long getMonthlyIncome() {
        return Long.parseLong(sMonthlyx);
    }

    public void setsMonthlyIncome(String sMonthlyx) {
        this.sMonthlyx = sMonthlyx;
    }

    public String getContact() {
        return sContactx;
    }

    public void setContact(String sContactx) {
        this.sContactx = sContactx;
    }

    public boolean isDataValid(){
        return isUniformPersonalValid() &&
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
                isMonthlySalaryValid() &&
                isCompanyContactValid();
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
            if(cMilitary == null || cMilitary.equalsIgnoreCase("")){
                message = "Please select if military personnel";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyLevelValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if(cCompLevl.equalsIgnoreCase("")){
                message = "Please select government level";
                return false;
            }
        } else if(sSectorxx.equalsIgnoreCase("1")){
            if(cCompLevl.equalsIgnoreCase("")){
                message = "Please select company level";
                return false;
            }
        } else {
            if(cCompLevl.equalsIgnoreCase("")){
                message = "Please select ofw region";
                return false;
            }
        }
        return true;
    }

    private boolean isEmployeeLevelValid(){
        if(sSectorxx.equalsIgnoreCase("0")){
            if(cEmpLevel.equalsIgnoreCase("")){
                message = "Please select government level";
                return false;
            }
        } else if(sSectorxx.equalsIgnoreCase("1")){
            if(cEmpLevel.equalsIgnoreCase("")){
                message = "Please select company level";
                return false;
            }
        } else {
            if(cEmpLevel.equalsIgnoreCase("")){
                message = "Please select ofw region";
                return false;
            }
        }
        return true;
    }

    private boolean isCountryValid(){
        if(sSectorxx.equalsIgnoreCase("2")){
            if(sCountryx == null || sCountryx.equalsIgnoreCase("")){
                message = "Please select country";
                return false;
            }
        }
        return true;
    }

    private boolean isBusinessNatureValid(){
        if(sSectorxx.equalsIgnoreCase("1")){
            if(sBusiness == null || sBusiness.equalsIgnoreCase("")){
                message = "Please select employment nature of business";
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
            if (sProvIDxx == null || sProvIDxx.equalsIgnoreCase("")) {
                message = "Please enter company province address";
                return false;
            }
            if (sTownIDxx == null || sTownIDxx.equalsIgnoreCase("")) {
                message = "Please enter company town address";
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
            if (sLengthxx == null || sLengthxx.equalsIgnoreCase("")) {
                message = "Please enter length of service";
                return false;
            }
        }
        return true;
    }

    private boolean isMonthlySalaryValid(){
        if(sSectorxx.equalsIgnoreCase("0") || sSectorxx.equalsIgnoreCase("1")) {
            if (sMonthlyx == null || sMonthlyx.equalsIgnoreCase("")) {
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
        }
        return true;
    }

}
