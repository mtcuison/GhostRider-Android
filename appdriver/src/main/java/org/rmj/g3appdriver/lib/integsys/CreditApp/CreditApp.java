package org.rmj.g3appdriver.lib.integsys.CreditApp;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;

import java.util.List;

public interface CreditApp {
    LiveData<ECreditApplicantInfo> GetApplication(String args);
    Object Parse(ECreditApplicantInfo args);
    int Validate(Object args);
    boolean Save(Object args);
    String getMessage();


    LiveData<List<EBarangayInfo>> GetBarangayList(String args);
    LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList();
    LiveData<List<ECountryInfo>> GetCountryList();
    LiveData<List<EOccupationInfo>> GetOccupations();
}
