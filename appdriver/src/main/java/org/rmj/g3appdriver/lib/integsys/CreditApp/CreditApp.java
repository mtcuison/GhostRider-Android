package org.rmj.g3appdriver.lib.integsys.CreditApp;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EOccupationInfo;

import java.util.List;

public interface CreditApp {
    LiveData<ECreditApplicantInfo> GetApplication(String args);
    Object Parse(ECreditApplicantInfo args);
    int Validate(Object args);
    String Save(Object args);
    String getMessage();


    LiveData<List<EBarangayInfo>> GetBarangayList(String args);
    LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList();
    LiveData<List<ECountryInfo>> GetCountryList();
    LiveData<List<EOccupationInfo>> GetOccupations();
}
