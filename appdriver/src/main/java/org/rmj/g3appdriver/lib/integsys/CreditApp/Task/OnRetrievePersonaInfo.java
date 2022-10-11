package org.rmj.g3appdriver.lib.integsys.CreditApp.Task;

public interface OnRetrievePersonaInfo {
    void LastName(String args);
    void FirstName(String args);
    void MiddleName(String args);
    void Suffix(String args);
    void BirthDate(String args);
    void BirthPlace(String args, String args1);
    void Gender(String args);
    void CivilStatus(String args);
    void Citizenship(String args, String args1);
    void MaidenName(String args);
    void MobileNo(String[] args, String[] args1, int[] args2);
    void TelephoneNo(String[] args);
    void EmailAdd(String[] args);
    void FacebookAcc(String args);
    void ViberAcc(String args);
}
