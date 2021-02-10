package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class SpouseEmploymentInfoModel {

    // Private Sector Attrs
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

    // Government Sector Attrs
    private String uniformedPersonnel, militaryPersonnel;

    //OFW Sector Attrs
    private String workCategory, region, country;


    // Getter and Setters
    public String getMessage() {
        return message;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCompanyLevel() {
        if(sector.equalsIgnoreCase("1")) {
            return companyLvl;
        }
        return "";
    }

    public String getGovermentLevel(){
        if(sector.equalsIgnoreCase("0")) {
            return companyLvl;
        }
        return "";
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

    public double getLengthOfService() {
        try{
            if(Integer.parseInt(monthOrYear) == 0) {
                double ldValue = Double.parseDouble(lengthOfService);
                return ldValue / 12;
            } else {
                return Double.parseDouble(lengthOfService);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setLengthOfService(String lengthOfService) {
        this.lengthOfService = lengthOfService;
    }

    public void setMonthOrYear(String monthOrYear) {
        this.monthOrYear = monthOrYear;
    }

    public long getGrossMonthly() {
        return Long.parseLong(grossMonthly);
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

    // GOVERNMENT GETTER AND SETTERS

    public String getUniformedPersonnel() {
        return uniformedPersonnel;
    }

    public void setUniformedPersonnel(String uniformedPersonnel) {
        this.uniformedPersonnel = uniformedPersonnel;
    }

    public String getMilitaryPersonnel() {
        return militaryPersonnel;
    }

    public void setMilitaryPersonnel(String militaryPersonnel) {
        this.militaryPersonnel = militaryPersonnel;
    }


    // OFW GETTER AND SETTERS

    public String getWorkCategory() {
        return workCategory;
    }

    public void setWorkCategory(String workCategory) {
        this.workCategory = workCategory;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


//    __ GLOBAL TEST__
    public boolean isSpouseEmploymentInfoValid() {
        return isUniformedPersonnelValid() &&
                isMilitaryPersonnelValid() &&
                isCompanyLvlValid() &&
                isEmployeeLvlValid() &&
                isBizIndustryValid() &&
                isCompanyNameValid() &&
                isCompAddressValid() &&
                isJobTitleValid() &&
                isJobSpecificValid() &&
                isEmploymentStatValid() &&
                isLengthOfServiceValid() &&
                isGrossMonthlyValid() &&
                isOFWFieldsValid();
    }

    private boolean isUniformedPersonnelValid() {
        if(sector.equalsIgnoreCase("0")) {
            if(uniformedPersonnel == null || uniformedPersonnel.equalsIgnoreCase("")) {
                message = "Please select if uniformed personnel";
                return false;
            }
        }
        return true;
    }

    private boolean isMilitaryPersonnelValid() {
        if(sector.equalsIgnoreCase("0")) {
            if(militaryPersonnel == null || militaryPersonnel.equalsIgnoreCase("")) {
                message = "Please select if military personnel";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyLvlValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(companyLvl.equalsIgnoreCase("")){
                message = "Please select Company Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(companyLvl.equalsIgnoreCase("")){
                message = "Please select Government Level";
                return false;
            }
        }
        return true;
    }

    private boolean isEmployeeLvlValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(employeeLvl.equalsIgnoreCase("")){
                message = "Please select Employee Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(employeeLvl.equalsIgnoreCase("")){
                message = "Please select Employee Level";
                return false;
            }
        }
        return true;
    }

    private boolean isBizIndustryValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(bizIndustry == null || bizIndustry.equalsIgnoreCase("")){
                message = "Please select business industry";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(bizIndustry == null || bizIndustry.equalsIgnoreCase("")){
                message = "Please enter Government Agency/Institution";
                return false;
            }
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
        if(sector.equalsIgnoreCase("1")) {
            if(compProvince == null || compProvince.equalsIgnoreCase("")) {
                message = "Please provide company province address";
                return false;
            }
            else if(compTown == null || compTown.equalsIgnoreCase("")) {
                message = "Please provide company town address";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(compProvince == null || compProvince.equalsIgnoreCase("")) {
                message = "Please provide agency province address";
                return false;
            }
            else if(compTown == null || compTown.equalsIgnoreCase("")) {
                message = "Please provide agency town address";
                return false;
            }
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

    private boolean isJobSpecificValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(jobSpecific== null || jobSpecific.equalsIgnoreCase("")) {
                message = "Please enter specific job";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(jobSpecific== null || jobSpecific.equalsIgnoreCase("")) {
                message = "Please enter job title";
                return false;
            }
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

    private boolean isOFWFieldsValid() {
       if(sector.equalsIgnoreCase("2")) {
           if(workCategory == null || workCategory.equalsIgnoreCase("")) {
               message = "Please select work category";
               return false;
           }
           else if(region == null || region.equalsIgnoreCase("")) {
               message = "Please select region";
               return false;
           }
           else if(country == null || country.equalsIgnoreCase("")) {
               message = "Please select country";
               return false;
           }
       }
       return true;
    }

}

