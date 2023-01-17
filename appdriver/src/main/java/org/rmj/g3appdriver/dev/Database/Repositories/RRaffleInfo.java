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

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

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
