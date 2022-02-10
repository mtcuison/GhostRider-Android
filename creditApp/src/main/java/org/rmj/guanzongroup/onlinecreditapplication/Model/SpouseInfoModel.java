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

    import java.util.ArrayList;
    import java.util.List;

    public class SpouseInfoModel {

        private String LastName;
        private String FrstName;
        private String MiddName;
        private String Suffix;
        private String NickName;
        private String BirthDate;
        private String BirthPlace;
        private String Citizenx;
        private final List<SpousePersonalMobileNo> mobileNoList = new ArrayList<>();
        private String PhoneNo;
        private String EmailAdd;
        private String FBacct;
        private String VbrAcct;

        private String ProvNme;
        private String TownNme;

        private String message;

        public String getMessage() {
            return message;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getFrstName() {
            return FrstName;
        }

        public void setFrstName(String frstName) {
            FrstName = frstName;
        }

        public String getMiddName() {
            return MiddName;
        }

        public void setMiddName(String middName) {
            MiddName = middName;
        }

        public String getSuffix() {
            return Suffix;
        }

        public void setSuffix(String suffix) {
            Suffix = suffix;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String birthDate) {
            BirthDate = birthDate;
        }

        public String getBirthPlace() {
            return BirthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            BirthPlace = birthPlace;
        }

        public String getCitizenx() {
            return Citizenx;
        }

        public void setCitizenx(String citizenx) {
            Citizenx = citizenx;
        }

        public int getMobileNoQty(){
            return mobileNoList.size();
        }

        public String getMobileNo(int position){
            return mobileNoList.get(position).getMobileNo();
        }

        public String getPostPaid(int position){
            return mobileNoList.get(position).getIsPostPd();
        }

        public int getPostYear(int position){
            return mobileNoList.get(position).getPostYear();
        }

        public void setMobileNo(String MobileNo, String Postpaid, int PostYear){
            SpousePersonalMobileNo mobileNo = new SpousePersonalMobileNo(MobileNo, Postpaid, PostYear);
            mobileNoList.add(mobileNo);
        }

        public String getPhoneNo() {
            return PhoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            PhoneNo = phoneNo;
        }

        public String getEmailAdd() {
            return EmailAdd.trim();
        }

        public void setEmailAdd(String emailAdd) {
            EmailAdd = emailAdd;
        }

        public String getFBacct() {
            return FBacct.trim();
        }

        public void setFBacct(String FBacct) {
            this.FBacct = FBacct;
        }

        public String getVbrAcct() {
            return VbrAcct;
        }

        public void setVbrAcct(String vbrAcct) {
            VbrAcct = vbrAcct;
        }

        public String getProvNme() {
            return ProvNme;
        }

        public void setProvNme(String provNme) {
            ProvNme = provNme;
        }

        public String getTownNme() {
            return TownNme;
        }

        public void setTownNme(String townNme) {
            TownNme = townNme;
        }


        public void clearMobileNo(){
            mobileNoList.clear();
        }




        public boolean isSpouseInfoValid(){
            return isLastNameValid() &&
                    isFirstNameValid() &&
                    isMiddNameValid() &&
                    isBirthDateValid() &&
                    isBirthPlaceValid() &&
                    isCitizenshipValid() &&
                    isValidContact() &&
                    isProvinceValid() &&
                    isTownValid();
        }
        private boolean isValidContact(){
            if (mobileNoList.size()> 0) {
                return isPrimaryContactValid() &&
                        isSecondaryContactValid() &&
                        isTertiaryContactValid();
            }else {
                message = "Please enter primary contact number";
                return false;
            }
        }
        private boolean isLastNameValid(){
            if(LastName.trim().isEmpty()){
                message = "Please provide Last name.";
                return false;
            }
            return true;
        }

        private boolean isFirstNameValid(){
            if(FrstName.trim().isEmpty()){
                message = "Please provide First name.";
                return false;
            }
            return true;
        }

        private boolean isMiddNameValid(){
            if(MiddName.trim().isEmpty()){
                message = "Please provide Middle name.";
                return false;
            }else if(MiddName.length() <= 1){
                message = "Please provide valid Middle name.";
                return false;
            }
            return true;
        }

        private boolean isBirthDateValid(){
            if(BirthDate.trim().isEmpty()){
                message = "Please provide Birth Date.";
                return false;
            }
            return true;
        }

        private boolean isBirthPlaceValid(){
            if(BirthPlace == null || BirthPlace.equalsIgnoreCase("")){
                message = "Please enter birth place";
                return false;
            }
            return true;
        }

        private boolean isProvinceValid(){
            if(ProvNme.trim().isEmpty()){
                message = "Please provide Province.";
                return false;
            }
            return true;
        }

        private boolean isTownValid(){
            if(TownNme.trim().isEmpty()){
                message = "Please provide Town.";
                return false;
            }
            return true;
        }

        private boolean isCitizenshipValid(){
            if(Citizenx == null || Citizenx.trim().isEmpty()){
                message = "Please enter citizenship";
                return false;
            }
            return true;
        }

        private boolean isPrimaryContactValid(){
            if(mobileNoList.get(0).getMobileNo().trim().isEmpty()){
                message = "Please enter primary contact number";
                return false;
            }

            else if(Integer.parseInt(mobileNoList.get(0).getIsPostPd()) < 0){
                message = "Please select sim card type";
                return false;
            }
            else if(!mobileNoList.get(0).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }
            else if(mobileNoList.get(0).getMobileNo().length() != 11){
                message = "Please enter primary contact info";
                return false;
            }
            return true;
        }

        private boolean isSecondaryContactValid(){
            if(mobileNoList.size() >= 2) {
                if (mobileNoList.get(1).getMobileNo().trim().isEmpty()) {
                    if(!mobileNoList.get(1).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
                        message = "Contact number must start with '09'";
                        return false;
                    }
                    if(mobileNoList.get(1).getMobileNo().length() != 11){
                        message = "Please enter primary contact info";
                        return false;
                    }
                    if(mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(0).getMobileNo())
                            || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                        message = "Contact numbers are duplicated";
                        return false;
                    }
                }

                if(Integer.parseInt(mobileNoList.get(1).getIsPostPd()) < 0){
                    message = "Please select sim card type";
                    return false;
                }
            }
            return true;
        }

        private boolean isTertiaryContactValid(){
            if(mobileNoList.size() == 3) {
                if (!mobileNoList.get(2).getMobileNo().trim().isEmpty()) {
                    if(!mobileNoList.get(2).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
                        message = "Contact number must start with '09'";
                        return false;
                    }
                    if(mobileNoList.get(2).getMobileNo().length() != 11){
                        message = "Please enter primary contact info";
                        return false;
                    }
                    if(mobileNoList.get(0).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())
                            || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                        message = "Contact numbers are duplicated";
                        return false;
                    }
                }

                if(Integer.parseInt(mobileNoList.get(2).getIsPostPd()) < 0){
                    message = "Please select sim card type";
                    return false;
                }
            }
            return true;
        }


        public static class SpousePersonalMobileNo{
            String MobileNo;
            String isPostPd;
            int PostYear;

            public SpousePersonalMobileNo(String mobileNo, String isPostPd, int postYear) {
                MobileNo = mobileNo;
                this.isPostPd = isPostPd;
                PostYear = postYear;
            }

            public String getMobileNo() {
                return MobileNo;
            }

            public String getIsPostPd() {
                return isPostPd;
            }

            public int getPostYear() {
                return PostYear;
            }
        }

    }