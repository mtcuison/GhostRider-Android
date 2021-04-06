package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleInfo;

import java.util.List;

public class RRaffleInfo {
    private final DRaffleInfo raffleDao;
    public static final String TAG = RRaffleInfo.class.getSimpleName();
    private final LiveData<List<ERaffleBasis>> allRaffleBasis;
    private final LiveData<String[]> allRaffleBasisDesc;

    public RRaffleInfo(Application application) {
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        raffleDao = GGCGriderDB.RaffleDao();
        allRaffleBasis = raffleDao.getRaffleBasis();
        allRaffleBasisDesc = raffleDao.getRaffleBasisDesc();
    }

    public LiveData<List<ERaffleBasis>> getAllRaffleBasis(){
        return allRaffleBasis;
    }

    public LiveData<String[]> getAllRaffleBasisDesc(){
        return allRaffleBasisDesc;
    }

    public void insertBulkData(List<ERaffleBasis> raffleEntry){
        raffleDao.insertBulkData(raffleEntry);
    }

    public void insertRaffleEntry(ERaffleInfo raffleInfo){
        raffleDao.insert(raffleInfo);
    }

    public void updateRaffleEntry(ERaffleInfo raffleInfo){
        raffleDao.update(raffleInfo);
    }
}
