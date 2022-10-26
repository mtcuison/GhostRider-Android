package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;

import java.util.List;

public class SpousePensionInfo implements CreditApp {
    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return null;
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        return null;
    }

    @Override
    public int Validate(Object args) {
        return 0;
    }

    @Override
    public boolean Save(Object args) {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return null;
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return null;
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return null;
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
