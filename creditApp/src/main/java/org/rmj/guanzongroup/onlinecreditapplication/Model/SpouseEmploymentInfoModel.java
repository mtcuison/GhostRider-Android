package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class SpouseEmploymentInfoModel {

    private String message,
            sector,
            companyLvl,
            employeeLvl,
            bizIndustry,
            companyName,
            compAddress,
            compProvince,
            compTown,
            jobTitle,
            jobSpecific,
            employmentStat,
            lengthOfService,
            monthOrYear,
            grossMonthly,
            compTelNox;

    public String getMessage() {
        return message;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCompanyLvl() {
        return companyLvl;
    }

    public void setCompanyLvl(String companyLvl) {
        this.companyLvl = companyLvl;
    }

    public String getEmployeeLvl() {
        return employeeLvl;
    }

    public void setEmployeeLvl(String employeeLvl) {
        this.employeeLvl = employeeLvl;
    }

    public String getBizIndustry() {
        return bizIndustry;
    }

    public void setBizIndustry(String bizIndustry) {
        this.bizIndustry = bizIndustry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public String getCompProvince() {
        return compProvince;
    }

    public void setCompProvince(String compProvince) {
        this.compProvince = compProvince;
    }

    public String getCompTown() {
        return compTown;
    }

    public void setCompTown(String compTown) {
        this.compTown = compTown;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobSpecific() {
        return jobSpecific;
    }

    public void setJobSpecific(String jobSpecific) {
        this.jobSpecific = jobSpecific;
    }

    public String getEmploymentStat() {
        return employmentStat;
    }

    public void setEmploymentStat(String employmentStat) {
        this.employmentStat = employmentStat;
    }

    public String getLengthOfService() {
        return lengthOfService;
    }

    public void setLengthOfService(String lengthOfService) {
        this.lengthOfService = lengthOfService;
    }

    public String getMonthOrYear() {
        return monthOrYear;
    }

    public void setMonthOrYear(String monthOrYear) {
        this.monthOrYear = monthOrYear;
    }

    public String getGrossMonthly() {
        return grossMonthly;
    }

    public void setGrossMonthly(String grossMonthly) {
        this.grossMonthly = grossMonthly;
    }

    public String getCompTelNox() {
        return compTelNox;
    }

    public void setCompTelNox(String compTelNox) {
        this.compTelNox = compTelNox;
    }

//    __ GLOBAL TEST__
//    public boolean isSpouseEmploymentInfoValid() {
//
//    }

    private boolean isCompanyLvlValid() {
        if(companyLvl.equalsIgnoreCase("")){
            message = "Please select Company Level";
            return false;
        }
        return true;
    }

    private boolean isEmployeeLvlValid() {
        if(employeeLvl.equalsIgnoreCase("")){
            message = "Please select Employee Level";
            return false;
        }
        return true;
    }

    private boolean isBizIndustryValid() {
        if(bizIndustry == null || bizIndustry.equalsIgnoreCase("")){
            message = "Please select business industry";
            return false;
        }
        return true;
    }

    private boolean isCompanyNameValid() {
        if(companyName == null || companyName.equalsIgnoreCase("")) {
            message = "Please provide company name";
            return false;
        }
        return true;
    }

    private boolean isCompAddressValid() {
        if(compProvince == null || compProvince.equalsIgnoreCase("")) {
            message = "Please provide company province address";
            return false;
        }
        if(compTown == null || compTown.equalsIgnoreCase("")) {
            message = "Please provide company town address";
            return false;
        }
        return true;
    }

    private boolean isJobTitleValid() {
        if(jobTitle == null || jobTitle.equalsIgnoreCase("")) {
            message = "Please select a Job Title";
            return false;
        }
        return true;
    }

    private boolean isEmploymentStatValid() {
        if(employmentStat == null || employmentStat.equalsIgnoreCase("")) {
            message = "Please select employment status";
            return false;
        }
        return true;
    }

    private boolean isLengthOfServiceValid() {
        if (lengthOfService == null || lengthOfService.equalsIgnoreCase("")) {
            message = "Please enter length of service";
            return false;
        }
        return true;
    }

    private boolean isGrossMonthlyValid() {
        if (grossMonthly == null || grossMonthly.equalsIgnoreCase("")) {
            message = "Please enter estimated monthly income";
            return false;
        }
        return  true;
    }




}

