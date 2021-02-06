package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;

import java.util.List;

public class RBarangay {
    private final DBarangayInfo barangayDao;
    private final LiveData<List<EBarangayInfo>> allBarangayInfo;

    public RBarangay(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        barangayDao = appDatabase.BarangayDao();
        allBarangayInfo = barangayDao.getAllBarangayInfo();
    }

    public void insertBarangay(EBarangayInfo barangayInfo){
        //TODO: Create asyncktask class that will insert data to local database on background thread.
    }

    public void insertBulkData(List<EBarangayInfo> barangayInfos){
        barangayDao.insertBulkBarangayData(barangayInfos);
    }

    public void updateBarangay(EBarangayInfo barangayInfo){
        //TODO: Create asyncktask class that will update data to local database on background thread.
    }

    public void deleteBarangay(EBarangayInfo barangayInfo){
        //TODO: Create asyncktask class that will delete data to local database on background thread.
    }

    public LiveData<List<EBarangayInfo>> getAllBarangayInfo(){
        return allBarangayInfo;
    }

    public LiveData<List<EBarangayInfo>> getAllBarangayFromTown(String TownID){
        LiveData<List<EBarangayInfo>> allBarangayFromTown = barangayDao.getAllBarangayInfoFromTown(TownID);
        return allBarangayFromTown;
    }

    public LiveData<String[]> getBarangayNamesFromTown(String TownID){
        return barangayDao.getAllBarangayNameFromTown(TownID);
    }
}
