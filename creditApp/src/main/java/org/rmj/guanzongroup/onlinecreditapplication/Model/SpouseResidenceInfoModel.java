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
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getHouseNox() {
            return houseNox;
        }

        public void setHouseNox(String houseNox) {
            this.houseNox = houseNox;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
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
            return isLandmarkValid() &&
                    isProvinceValid() &&
                    isTownValid() &&
                    isBarangayValid();
        }

        private boolean isLandmarkValid(){
            if(landmark.trim().isEmpty()){
                message = "Please provide Landmark.";
                return false;
            }
            return true;
        }

        private boolean isProvinceValid(){
            if(province.trim().isEmpty()){
                message = "Please provide Province.";
                return false;
            }
            return true;
        }

        private boolean isTownValid(){
            if(town.trim().isEmpty()){
                message = "Please provide Town.";
                return false;
            }
            return true;
        }

        private boolean isBarangayValid(){
            if(barangay.trim().isEmpty()){
                message = "Please provide Barangay.";
                return false;
            }
            return true;
        }
    }
