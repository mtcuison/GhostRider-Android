package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;

import java.util.List;

public class RCountry {
    private final DCountryInfo countryDao;
    private final LiveData<List<ECountryInfo>> allCountryInfo;
    private final LiveData<String[]> allCountryNames;
    private final LiveData<String[]> allCountryCitizenNames;

    public RCountry(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        countryDao = appDatabase.CountryDao();
        allCountryInfo = countryDao.getAllCountryInfo();
        allCountryNames = countryDao.getAllCountryNames();
        allCountryCitizenNames = countryDao.getAllCountryCitizenNames();
    }

    public LiveData<List<ECountryInfo>> getAllCountryInfo() {
        return allCountryInfo;
    }

    public LiveData<String[]> getAllCountryNames(){
        return allCountryNames;
    }

    public LiveData<String[]> getAllCountryCitizenName(){
        return allCountryCitizenNames;
    }

    public void insertBulkData(List<ECountryInfo> countryInfos){
        countryDao.insertBulkData(countryInfos);
    }
}
