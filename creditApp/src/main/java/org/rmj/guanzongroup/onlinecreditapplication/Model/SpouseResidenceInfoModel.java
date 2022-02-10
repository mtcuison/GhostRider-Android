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

    public class SpouseResidenceInfoModel {

        private String landmark;
        private String houseNox;
        private String address1;
        private String address2;

        private String province;
        private String town;
        private String barangay;

        private String message;

        public String getMessage() {
            return message;
        }

        public String getLandmark() {
            return landmark.trim();
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getHouseNox() {
            return houseNox.trim();
        }

        public void setHouseNox(String houseNox) {
            this.houseNox = houseNox;
        }

        public String getAddress1() {
            return address1.trim();
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2.trim();
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getBarangay() {
            return barangay;
        }

        public void setBarangay(String barangay) {
            this.barangay = barangay;
        }

        public boolean isSpouseResidenceInfoValid() {
            if(isLandmarkValid() || !houseNox.trim().isEmpty() || !address1.trim().isEmpty() || !address2.trim().isEmpty() || isProvinceValid() || isTownValid() || isBarangayValid()) {
                return isLandmarkValid() &&
                        isProvinceValid() &&
                        isTownValid() &&
                        isBarangayValid();
            } else {
                return true;
            }
        }

        private boolean isLandmarkValid(){
            if(landmark.trim().isEmpty()){
                message = "Please provide Landmark.";
                return false;
            }
            return true;
        }

        private boolean isProvinceValid(){
            if(province == null || province.trim().isEmpty()){
                message = "Please provide Province.";
                return false;
            }
            return true;
        }

        private boolean isTownValid(){
            if(town == null || town.trim().isEmpty()){
                message = "Please provide Town.";
                return false;
            }
            return true;
        }

        private boolean isBarangayValid(){
            if(barangay == null || barangay.trim().isEmpty()){
                message = "Please provide Barangay.";
                return false;
            }
            return true;
        }
    }
