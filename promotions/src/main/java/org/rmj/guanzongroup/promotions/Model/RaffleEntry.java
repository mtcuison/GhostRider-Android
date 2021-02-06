package org.rmj.guanzongroup.promotions.Model;

public class RaffleEntry {
    private String BranchCodexx;
    private String CustomerName;
    private String DocumentType;
    private String DocumentNoxx;
    private String MobileNumber;

    public RaffleEntry(String branchCodexx, String customerName, String documentNoxx, String mobileNumber) {
        BranchCodexx = branchCodexx;
        CustomerName = customerName;
        DocumentNoxx = documentNoxx;
        MobileNumber = mobileNumber;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getBranchCodexx() {
        return BranchCodexx;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public String getDocumentNoxx() {
        return DocumentNoxx;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public boolean isValidInfo(){
        return isNameValid() &&
                isDocumentNoValid() &&
                isMobileNoValid() &&
                isDocumentTypeValid();
    }

    private boolean isNameValid(){
        return !CustomerName.trim().isEmpty();
    }

    private boolean isDocumentNoValid(){
        return !DocumentNoxx.trim().isEmpty();
    }

    private boolean isMobileNoValid(){
        if(MobileNumber.trim().isEmpty()){
            return false;
        }
        if(MobileNumber.length()!=11){
            return false;
        }
        return MobileNumber.substring(0, 2).equalsIgnoreCase("09");
    }

    private boolean isDocumentTypeValid(){
        return !DocumentType.trim().isEmpty();
    }
}
