/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.promotions
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.promotions.Model;

public class RaffleEntry {
    private String BranchCodexx;
    private String CustomerName;
    private String CustomerAddx;
    private String CustomerTown;
    private String CustomerProv;
    private String DocumentType;
    private String DocumentNoxx;
    private String MobileNumber;

    private String message;

    public RaffleEntry() {
    }

    public String getMessage() {
        return message;
    }

    public String getBranchCodexx() {
        return BranchCodexx;
    }

    public void setBranchCodexx(String branchCodexx) {
        BranchCodexx = branchCodexx;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddx() {
        return CustomerAddx;
    }

    public void setCustomerAddx(String customerAddx) {
        CustomerAddx = customerAddx;
    }

    public String getCustomerProv() {
        return CustomerProv;
    }

    public void setCustomerProv(String customerProv) {
        CustomerProv = customerProv;
    }

    public String getCustomerTown() {
        return CustomerTown;
    }

    public void setCustomerTown(String customerTown) {
        CustomerTown = customerTown;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getDocumentNoxx() {
        return DocumentNoxx;
    }

    public void setDocumentNoxx(String documentNoxx) {
        DocumentNoxx = documentNoxx;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public boolean isValidInfo(){
        return isNameValid() &&
                isTownValid() &&
                isDocumentNoValid() &&
                isMobileNoValid() &&
                isDocumentTypeValid();
    }

    public boolean isNameValid(){
        if(CustomerName.trim().isEmpty()){
            message = "Please enter customer name";
            return false;
        }
        return true;
    }

    public boolean isTownValid(){
        if(CustomerTown.trim().isEmpty()){
            message = "Please enter town";
            return false;
        }
        return true;
    }

    public boolean isDocumentNoValid(){
        if(DocumentNoxx.trim().isEmpty()){
            message = "Please enter document number";
            return false;
        }
        return true;
    }

    public boolean isMobileNoValid(){
        if(MobileNumber.trim().isEmpty()){
            message = "Please enter mobile number";
            return false;
        }
        if(MobileNumber.length()!=11){
            message = "Please enter valid mobile number";
            return false;
        }
        if(!MobileNumber.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile number must start with '09'";
            return false;
        }
        return true;
    }

    public boolean isDocumentTypeValid(){
        if(DocumentType.trim().isEmpty()){
            message = "Please select document type";
            return false;
        }
        return true;
    }
}
