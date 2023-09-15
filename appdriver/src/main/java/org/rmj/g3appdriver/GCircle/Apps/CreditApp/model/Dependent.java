package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

import java.util.ArrayList;
import java.util.List;

public class Dependent {

    private String sTransNox = "";

    private String message;

    private List<DependentInfo> loList;

    public Dependent() {
        loList = new ArrayList<>();
    }

    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getMessage() {
        return message;
    }

    public void clear(){
        loList.clear();
    }

    public void setDependentList(List<DependentInfo> val){
        this.loList = val;
    }

    public List<DependentInfo> getDependentList(){
        return loList;
    }

    public static class DependentInfo{

        private String sFullName = "";
        private String cRelation = "";
        private int nDpdntAge = 0;
        private String cSchoolTp = "";
        private String cEduLevel = "";
        private String sSchoolNm = "";
        private String sSchlAddx = "";
        private String sSchlTown = "";
        private String cEmpSctor = "";
        private String sCompName = "";
        private String cEmployed = "0";
        private String cDependnt = "0";
        private String cHouseHld = "0";
        private String cMarriedx = "0";
        private String cStudentx = "0";
        private String cSchoolar = "0";

        private String message;

        public DependentInfo() {
        }

        public String getMessage() {
            return message;
        }

        public String getFullName() {
            return sFullName;
        }

        public void setFullName(String sFullName) {
            this.sFullName = sFullName;
        }

        public String getRelation() {
            return cRelation;
        }

        public void setRelation(String cRelation) {
            this.cRelation = cRelation;
        }

        public int getDpdntAge() {
            return nDpdntAge;
        }

        public void setDpdntAge(int nDpdntAge) {
            this.nDpdntAge = nDpdntAge;
        }

        public String getSchoolTp() {
            return cSchoolTp;
        }

        public void setSchoolTp(String cSchoolTp) {
            this.cSchoolTp = cSchoolTp;
        }

        public String getEduLevel() {
            return cEduLevel;
        }

        public void setEduLevel(String cEduLevel) {
            this.cEduLevel = cEduLevel;
        }

        public String getSchoolNm() {
            return sSchoolNm;
        }

        public void setSchoolNm(String sSchoolNm) {
            this.sSchoolNm = sSchoolNm;
        }

        public String getSchlAddx() {
            return sSchlAddx;
        }

        public void setSchlAddx(String sSchlAddx) {
            this.sSchlAddx = sSchlAddx;
        }

        public String getSchlTown() {
            return sSchlTown;
        }

        public void setSchlTown(String sSchlTown) {
            this.sSchlTown = sSchlTown;
        }

        public String getEmpSctor() {
            return cEmpSctor;
        }

        public void setEmpSctor(String cEmpSctor) {
            this.cEmpSctor = cEmpSctor;
        }

        public String getCompName() {
            return sCompName;
        }

        public void setCompName(String sCompName) {
            this.sCompName = sCompName;
        }

        public String getEmployed() {
            return cEmployed;
        }

        public void setEmployed(String cEmployed) {
            this.cEmployed = cEmployed;
        }

        public String getDependnt() {
            return cDependnt;
        }

        public void setDependnt(String cDependnt) {
            this.cDependnt = cDependnt;
        }

        public String getHouseHld() {
            return cHouseHld;
        }

        public void setHouseHld(String cHouseHld) {
            this.cHouseHld = cHouseHld;
        }

        public String getMarriedx() {
            return cMarriedx;
        }

        public void setMarriedx(String cMarriedx) {
            this.cMarriedx = cMarriedx;
        }

        public String getStudentx() {
            return cStudentx;
        }

        public void setStudentx(String cStudentx) {
            this.cStudentx = cStudentx;
        }

        public String getSchoolar() {
            return cSchoolar;
        }

        public void setSchoolar(String cSchoolar) {
            this.cSchoolar = cSchoolar;
        }

        public boolean isDataValid(){
            if(sFullName.trim().isEmpty()){
                message = "Please enter Dependent Full Name!";
                return false;
            }
            if(nDpdntAge == 0){
                message = "Please enter Dependent Age!";
                return false;
            }
            if(cRelation.isEmpty()){
                message = "Please select dependent relation.";
                return false;
            }
            if(cStudentx.equalsIgnoreCase("1")){
                return isStudent();
            }
            if(cEmployed.equalsIgnoreCase("1")){
                return isEmployed();
            }
            return true;
        }

        private boolean isStudent(){
            if(cSchoolTp.trim().isEmpty()){
                message = "Please select school type!";
                return false;
            }
            if(cEduLevel.trim().isEmpty()){
                message = "Please select education level!";
                return false;
            }
            if(sSchoolNm.trim().isEmpty()){
                message = "Please enter school name!";
                return false;
            }
            if(sSchlAddx.trim().isEmpty()){
                message = "Please enter school address!";
                return false;
            }
            if(sSchlTown.trim().isEmpty()){
                message = "Please enter school town.";
                return false;
            }
            return true;
        }

        private boolean isEmployed(){
            if(cEmpSctor.trim().isEmpty()){
                message = "Please select employment sector!";
                return false;
            }
            if(sCompName.trim().isEmpty()){
                message = "Please enter company name!";
                return false;
            }
            return true;
        }
    }
}
