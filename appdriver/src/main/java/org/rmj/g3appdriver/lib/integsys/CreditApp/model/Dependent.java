package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

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
        private int cSchoolTp = 0;
        private int cEduLevel = 0;
        private String sSchoolNm = "";
        private String sSchlAddx = "";
        private String sSchlProv = "";
        private String sSchlTown = "";
        private int cEmpSctor = 0;
        private String sCompName = "";
        private int cEmployed = 0;
        private int cDependnt = 0;
        private int cHouseHld = 0;
        private int cMarriedx = 0;
        private int cStudentx = 0;
        private int cSchoolar = 0;

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

        public int getSchoolTp() {
            return cSchoolTp;
        }

        public void setSchoolTp(int cSchoolTp) {
            this.cSchoolTp = cSchoolTp;
        }

        public int getEduLevel() {
            return cEduLevel;
        }

        public void setEduLevel(int cEduLevel) {
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

        public String getSchlProv() {
            return sSchlProv;
        }

        public void setSchlProv(String sSchlProv) {
            this.sSchlProv = sSchlProv;
        }

        public String getSchlTown() {
            return sSchlTown;
        }

        public void setSchlTown(String sSchlTown) {
            this.sSchlTown = sSchlTown;
        }

        public int getEmpSctor() {
            return cEmpSctor;
        }

        public void setEmpSctor(int cEmpSctor) {
            this.cEmpSctor = cEmpSctor;
        }

        public String getCompName() {
            return sCompName;
        }

        public void setCompName(String sCompName) {
            this.sCompName = sCompName;
        }

        public int getEmployed() {
            return cEmployed;
        }

        public void setEmployed(int cEmployed) {
            this.cEmployed = cEmployed;
        }

        public int getDependnt() {
            return cDependnt;
        }

        public void setDependnt(int cDependnt) {
            this.cDependnt = cDependnt;
        }

        public int getHouseHld() {
            return cHouseHld;
        }

        public void setHouseHld(int cHouseHld) {
            this.cHouseHld = cHouseHld;
        }

        public int getMarriedx() {
            return cMarriedx;
        }

        public void setMarriedx(int cMarriedx) {
            this.cMarriedx = cMarriedx;
        }

        public int getStudentx() {
            return cStudentx;
        }

        public void setStudentx(int cStudentx) {
            this.cStudentx = cStudentx;
        }

        public int getSchoolar() {
            return cSchoolar;
        }

        public void setSchoolar(int cSchoolar) {
            this.cSchoolar = cSchoolar;
        }


        public boolean isDataValid(){
            if(!isFullName()){
                return false;
            }
            if(!isDpndentAge()){
                return false;
            }
//            if(!isDPDRelationship()){
//                return false;
//            }
            return true;
        }
        private boolean isFullName(){
            if(sFullName.trim().isEmpty()){
                message = "Please enter Dependent Full Name";
                return false;
            }
            return true;
        }
        private boolean isStudent(){
            return isDpdSchoolName() &&
                    isDpdSchoolAddress() &&
//                    isDpdSchoolProv() &&
                    isDpdSchoolTown();
        }
//        private boolean isDPDRelationship(){
//            if(cRelation) < 0){
//                message = "Please enter Dependent Relationship!";
//                return false;
//            }
//            return true;
//        }
        private boolean isDpndentAge(){
            if(nDpdntAge == 0){
                message = "Please enter Dependent Age!";
                return false;
            }
            return true;
        }
        private boolean isDpdSchoolName(){
            if(sSchoolNm.trim().isEmpty()){
                message = "Please enter School Type!";
                return false;
            }
            return true;
        }
        private boolean isDpdSchoolAddress(){
            if(sSchlAddx.trim().isEmpty()){
                message = "Please enter Dependent School Address!";
                return false;
            }
            return true;
        }
        private boolean isDpdSchoolTown(){
            if(sSchlTown.trim().isEmpty()){
                message = "Please enter Dependent School Municipality address!";
                return false;
            }
            return true;
        }
        private boolean isEmployed(){
            return isDpdCompanyName();
        }
        private boolean isDpdCompanyName(){
            if(sCompName.trim().isEmpty()){
                message = "Please enter Company Name!";
                return false;
            }
            return true;
        }
    }
}
