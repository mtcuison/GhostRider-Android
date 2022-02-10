/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.util.Log;

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
    private String country;


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

    public String getOFWRegion() {
        if(sector.equalsIgnoreCase("2")) {
            return companyLvl;
        }
        return "";
    }

    public void setCompanyLvl(String companyLvl) {
        this.companyLvl = companyLvl;
    }

    public String getEmployeeLvl() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0"))
        {
            return employeeLvl;
        }
        return "";
    }

    public String getWorkCategory() {
        if(sector.equalsIgnoreCase("2")) {
            return employeeLvl;
        }
        return "";
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
        return companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompAddress() {
        return compAddress.trim();
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
        return jobSpecific.trim();
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
        if(!grossMonthly.equalsIgnoreCase("")) {
            return Long.parseLong(grossMonthly.replace(",", ""));
        }
        return 0;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


//    __ GLOBAL TEST__
    public boolean isSpouseEmploymentInfoValid() {
        if(isCompanyLvlValid() || isEmployeeLvlValid()) {
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
                    isMonthOrYearValid() &&
                    isLengthOfServiceValid() &&
                    isGrossMonthlyValid() &&
                    isCompTelNoxValid() &&
                    isOFWFieldsValid();
        } else {
            return true;
        }
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
            if(companyLvl == null || Integer.parseInt(companyLvl)< 0){
                message = "Please select Company Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(companyLvl == null || Integer.parseInt(companyLvl)< 0){
                message = "Please select Government Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("2")) {
            if(companyLvl == null || Integer.parseInt(companyLvl)< 0){
                message = "Please select Region";
                return false;
            }
        }
        return true;
    }

    private boolean isEmployeeLvlValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(employeeLvl == null || Integer.parseInt(employeeLvl)< 0){
                message = "Please select Employee Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(employeeLvl == null || Integer.parseInt(employeeLvl)< 0){
                message = "Please select Employee Level";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("2")) {
            if(employeeLvl == null || Integer.parseInt(employeeLvl)< 0){
                message = "Please select work category";
                return false;
            }
        }
        return true;
    }

    private boolean isBizIndustryValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(bizIndustry == null){
                message = "Please select nature of business";
                return false;
            }
        }
        return true;
    }

    private boolean isCompanyNameValid() {
        if(sector.equalsIgnoreCase("1")) {
            if(companyName == null || companyName.equalsIgnoreCase("")) {
                message = "Please provide company name";
                return false;
            }
        }
        else if(sector.equalsIgnoreCase("0")) {
            if(companyName == null || companyName.equalsIgnoreCase("")) {
                message = "Please enter Government Agency/Institution";
                return false;
            }
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
        if(sector.equalsIgnoreCase("1")) {
            if(jobTitle == null || jobTitle.equalsIgnoreCase("")) {
                message = "Please select a Job Title";
                return false;
            }
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
                message = "Please enter job/position";
                return false;
            }
        }
        return true;
    }

    private boolean isEmploymentStatValid() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0")) {
            if(employmentStat == null || employmentStat.equalsIgnoreCase("")) {
                message = "Please select employment status";
                return false;
            }
        }
        return true;
    }

    private boolean isMonthOrYearValid() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0")) {
            if(monthOrYear == null || monthOrYear.equalsIgnoreCase("-1")) {
                message = "Please select duration of length of service";
                return false;
            }
        }
        return true;
    }

    private boolean isLengthOfServiceValid() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0")) {
            if (lengthOfService == null || lengthOfService.equalsIgnoreCase("")) {
                message = "Please enter length of service";
                return false;
            }
        }
        return true;
    }

    private boolean isGrossMonthlyValid() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0")) {
            if (grossMonthly == null || grossMonthly.equalsIgnoreCase("")) {
                message = "Please enter estimated monthly income";
                return false;
            }
        }
        return  true;
    }

    private boolean isCompTelNoxValid() {
        if(sector.equalsIgnoreCase("1") || sector.equalsIgnoreCase("0")) {
            if(compTelNox == null || compTelNox.equalsIgnoreCase("")) {
                message = "Please provide company contact number";
                return false;
            }
        }
        return true;
    }

    private boolean isOFWFieldsValid() {
       if(sector.equalsIgnoreCase("2")) {
           if(country == null || country.equalsIgnoreCase("")) {
               message = "Please select country";
               return false;
           }
       }
       return true;
    }

}

