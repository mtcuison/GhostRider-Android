    /*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

    import android.app.Application;

    import androidx.lifecycle.LiveData;

    import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
    import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBarangayInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;

    import java.util.List;

public class RBarangay {
    private final DBarangayInfo barangayDao;
    private final LiveData<List<EBarangayInfo>> allBarangayInfo;

    public RBarangay(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        barangayDao = GGCGriderDB.BarangayDao();
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

    public LiveData<String> getBarangayInfoFromID(String fsID) {
        return barangayDao.getBarangayInfoFromID(fsID);
    }

    public DBarangayInfo.BrgyTownProvNames getAddressInfo(String BrgyID){
        return barangayDao.getAddressInfo(BrgyID);
    }

    public String getLatestDataTime(){
        return barangayDao.getLatestDataTime();
    }

    public Integer GetBarangayRecordCount(){
        return barangayDao.GetBarangayRecordCount();
    }
    public EBarangayInfo CheckIfExist(String fsVal){
        return barangayDao.CheckIfExist(fsVal);
    }
}
