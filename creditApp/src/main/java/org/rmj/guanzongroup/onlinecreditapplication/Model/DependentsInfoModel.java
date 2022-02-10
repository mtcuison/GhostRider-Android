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

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;

public class DependentsInfoModel {
    private String dpdFullname,
            dpdRlationship,
            dpdAge,
            dpdSchoolType,
            dpdEducLevel,
            dpdSchoolName,
            dpdSchoolAddress,
            dpdSchoolProv,
            dpdSchoolTown,
            dpdEmployedSector,
            dpdCompanyName,
            isEmployed,
            isDependent,
            isHouseHold,
            isMarried,
            isStudent,
            dpdIsScholar;

    private String message;


    public DependentsInfoModel(){
    }


    public DependentsInfoModel(String dpdFullname,
                               String dpdRlationship,
                               String dpdAge,
                               String dpdIsStudent,
                               String dpdSchoolType,
                               String dpdIsScholar,
                               String dpdEducLevel,
                               String dpdSchoolName,
                               String dpdSchoolAddress,
                               String dpdSchoolProv,
                               String dpdSchoolTown,
                               String dpdIsEmployed,
                               String dpdEmployedSector,
                               String dpdCompanyName,
                               String dpdIsDependent,
                               String dpdIsHouseHold,
                               String dpdIsMarried){
        this.dpdFullname = dpdFullname;
        this.dpdRlationship = dpdRlationship;
        this.dpdAge = dpdAge;
        this.isStudent = dpdIsStudent;
        this.dpdSchoolType = dpdSchoolType;
        this.dpdEducLevel = dpdEducLevel;
        this.dpdSchoolName = dpdSchoolName;
        this.dpdSchoolAddress = dpdSchoolAddress;
        this.dpdSchoolProv = dpdSchoolProv;
        this.dpdSchoolTown = dpdSchoolTown;
        this.isEmployed = dpdIsEmployed;
        this.dpdEmployedSector = dpdEmployedSector;
        this.dpdCompanyName = dpdCompanyName;
        this.isDependent = dpdIsDependent;
        this.isHouseHold = dpdIsHouseHold;
        this.isMarried = dpdIsMarried;
        this.dpdIsScholar = dpdIsScholar;
    }

    public String getMessage() {
        return message;
    }

    public void setDpdFullname(String dpdFullname){
        this.dpdFullname = dpdFullname;
    }
    public String getDpdFullName(){ return dpdFullname;}
    public void setDpdIsScholar(String dpdIsScholar){
        this.dpdIsScholar = dpdIsScholar;
    }
    public String getDpdIsScholar(){ return dpdIsScholar;}

    public void setDpdRlationship(String dpdRlationship){
        this.dpdRlationship = dpdRlationship;
    }
    public String getDpdRlationship(){ return dpdRlationship;}

    public void setDpdAge(String dpdAge){
        this.dpdAge = dpdAge;
    }
    public String getDpdAge(){ return dpdAge;}

    public void setDpdSchoolType(String dpdSchoolType){
        this.dpdSchoolType = dpdSchoolType;
    }
    public String getDpdSchoolType(){ return dpdSchoolType;}


    public void setDpdEducLevel(String dpdEducLevel){
        this.dpdEducLevel = dpdEducLevel;
    }
    public String getDpdEducLevel(){ return dpdEducLevel;}

    public void setDpdSchoolName(String dpdSchoolName){
        this.dpdSchoolName = dpdSchoolName;
    }
    public String getDpdSchoolName(){ return dpdSchoolName;}


    public void setDpdSchoolAddress(String dpdSchoolAddress){
        this.dpdSchoolAddress = dpdSchoolAddress;
    }
    public String getDpdSchoolAddress(){ return dpdSchoolAddress.trim();}


    public void setDpdSchoolProv(String dpdSchoolProv){
        this.dpdSchoolProv = dpdSchoolProv;
    }
    public String getDpdSchoolProv(){ return dpdSchoolProv;}


    public void setDpdSchoolTown(String dpdSchoolTown){
        this.dpdSchoolTown = dpdSchoolTown;
    }
    public String getDpdSchoolTown(){ return dpdSchoolTown;}

    public void setDpdEmployedSector(String dpdEmployedSector){
        this.dpdEmployedSector = dpdEmployedSector;
    }
    public String getDpdEmployedSector(){ return dpdEmployedSector;}

    public void setDpdCompanyName(String dpdCompanyName){
        this.dpdCompanyName = dpdCompanyName;
    }
    public String getDpdCompanyName(){ return dpdCompanyName;}

    public void setIsStudent(String isStudent){
        this.isStudent = isStudent;
    }
    public String getIsStudent(){ return isStudent;}

    public void setIsEmployed(String isEmployed){
        this.isEmployed = isEmployed;
    }
    public String getIsEmployed(){ return isEmployed;}

    public void setIsDependent(String isDependent){
        this.isDependent = isDependent;
    }
    public String getIsDependent(){ return isDependent;}

    public void setIsHouseHold(String isHouseHold){
        this.isHouseHold = isHouseHold;
    }
    public String getIsHouseHold(){ return isHouseHold;}

    public void setIsMarried(String isMarried){
        this.isMarried = isMarried;
    }
    public String getIsMarried(){ return isMarried;}






    public boolean isDataValid(){
        if(!isFullName()){
            return false;
        }
        if(!isDpndentAge()){
            return false;
        }
        if(!isDPDRelationship()){
            return false;
        }

        if(isStudent.equalsIgnoreCase("1")){
            return isStudent();
        }
        if(Integer.parseInt(isStudent) < 0){
            message = "Please select dependent if student!";
            return false;
        }


        if(isEmployed.equalsIgnoreCase("1")){
            return isEmployed();
        }
        if(Integer.parseInt(isEmployed) < 0){
            message = "Please select dependent if employed!";
            return false;
        }
        return true;
    }
    private boolean isFullName(){
        if(dpdFullname.trim().isEmpty()){
            message = "Please enter Dependent Full Name";
            return false;
        }
        return true;
    }
    private boolean isStudent(){
        return isSchoolType() &&
                isDpdEducLevel() &&
                isDpdSchoolName() &&
                isDpdSchoolAddress() &&
                isDpdSchoolProv() &&
                isDpdSchoolTown();
    }
    private boolean isDPDRelationship(){
        if(Integer.parseInt(dpdRlationship) < 0){
            message = "Please enter Dependent Relationship!";
            return false;
        }
        return true;
    }
    private boolean isDpndentAge(){
        if(dpdAge.trim().isEmpty()){
            message = "Please enter Dependent Age!";
            return false;
        }
        return true;
    }
    private boolean isSchoolType(){
        if(Integer.parseInt(dpdSchoolType) < 0){
            message = "Please select School Type!";
            return false;
        }
        return true;
    }
    private boolean isDpdEducLevel(){
        if(Integer.parseInt(dpdEducLevel) < 0){
            message = "Please select Educational Level!";
            return false;
        }
        return true;
    }
    private boolean isDpdSchoolName(){
        if(dpdSchoolName.trim().isEmpty()){
            message = "Please enter School Type!";
            return false;
        }
        return true;
    }
    private boolean isDpdSchoolAddress(){
        if(dpdSchoolAddress.trim().isEmpty()){
            message = "Please enter Dependent School Address!";
            return false;
        }
        return true;
    }
    private boolean isDpdSchoolProv(){
        if(dpdSchoolProv.trim().isEmpty()){
            message = "Please enter Dependent School Province!";
            return false;
        }
        return true;
    }
    private boolean isDpdSchoolTown(){
        if(dpdSchoolTown.trim().isEmpty()){
            message = "Please enter Dependent School Town!";
            return false;
        }
        return true;
    }
    private boolean isEmployed(){

        return isDpdEmployedSector() &&
                isDpdCompanyName();
    }
    private boolean isDpdEmployedSector(){
        if(Integer.parseInt(dpdEmployedSector) < 0){
            message = "Please select Employment Sector!";
            return false;
        }
        return true;
    }
    private boolean isDpdCompanyName(){
        if(dpdCompanyName.trim().isEmpty()){
            message = "Please enter Company Name!";
            return false;
        }
        return true;
    }

}
