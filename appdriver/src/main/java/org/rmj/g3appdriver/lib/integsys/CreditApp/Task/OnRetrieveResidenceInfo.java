package org.rmj.g3appdriver.lib.integsys.CreditApp.Task;

public interface OnRetrieveResidenceInfo {
    void OnAddress(boolean args);
    void PresentLandmark(String args);
    void PresentHouseNo(String args);
    void PresentLotNumber(String args);
    void PresentStreet(String args);
    void PresentProvince(String args, String args1);
    void PresentTown(String args, String args1);
    void PresentBarangay(String args, String args1);
    void HouseOwnership(String args);
    void OwnerRelationship(String args);
    void LenghtOfStay(String args);
    void MonthylExpenses(String args);
    void HouseHolds(String args);
    void HouseType(String args);
    void HasGarage(String args);
    void PermanentLandmark(String args);
    void PermanentHouseNo(String args);
    void PermanentLotNumber(String args);
    void PermanentStreet(String args);
    void PermanentProvince(String args, String args1);
    void PermanentTown(String args, String args1);
    void PermanentBarangay(String args, String args1);
}
