package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

import java.util.ArrayList;
import java.util.List;

public class Dependent {

    private String sTransNox = "";

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

    public void Add(DependentInfo args){
        loList.add(args);
    }

    public void clear(){
        loList.clear();
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
        private String sSchlProv = "";
        private String sSchlTown = "";
        private String cEmpSctor = "";
        private String sCompName = "";
        private String cEmployed = "";
        private String cDependnt = "";
        private String cHouseHld = "";
        private String cMarriedx = "";
        private String cStudentx = "";
        private String cSchoolar = "";

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
            if(!isFullName()){
                return false;
            }
            if(!isDpndentAge()){
                return false;
            }
            if(!isDPDRelationship()){
                return false;
            }

            if(cStudentx.equalsIgnoreCase("1")){
                return isStudent();
            }
            if(Integer.parseInt(cStudentx) < 0){
                message = "Please select dependent if student!";
                return false;
            }


            if(cEmployed.equalsIgnoreCase("1")){
                return isEmployed();
            }
            if(Integer.parseInt(cEmployed) < 0){
                message = "Please select dependent if employed!";
                return false;
            }
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
            return isSchoolType() &&
                    isDpdEducLevel() &&
                    isDpdSchoolName() &&
                    isDpdSchoolAddress() &&
                    isDpdSchoolProv() &&
                    isDpdSchoolTown();
        }
        private boolean isDPDRelationship(){
            if(Integer.parseInt(cRelation) < 0){
                message = "Please enter Dependent Relationship!";
                return false;
            }
            return true;
        }
        private boolean isDpndentAge(){
            if(nDpdntAge == 0){
                message = "Please enter Dependent Age!";
                return false;
            }
            return true;
        }
        private boolean isSchoolType(){
            if(Integer.parseInt(cSchoolTp) < 0){
                message = "Please select School Type!";
                return false;
            }
            return true;
        }
        private boolean isDpdEducLevel(){
            if(Integer.parseInt(cEduLevel) < 0){
                message = "Please select Educational Level!";
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
        private boolean isDpdSchoolProv(){
            if(sSchlProv.trim().isEmpty()){
                message = "Please enter Dependent School Province!";
                return false;
            }
            return true;
        }
        private boolean isDpdSchoolTown(){
            if(sSchlTown.trim().isEmpty()){
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
            if(Integer.parseInt(cEmpSctor) < 0){
                message = "Please select Employment Sector!";
                return false;
            }
            return true;
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
