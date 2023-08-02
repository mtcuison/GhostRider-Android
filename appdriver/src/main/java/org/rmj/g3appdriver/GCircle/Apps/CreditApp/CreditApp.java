package org.rmj.g3appdriver.GCircle.Apps.CreditApp;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;

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
