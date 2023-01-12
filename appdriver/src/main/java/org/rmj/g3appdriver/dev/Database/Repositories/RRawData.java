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

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

public class RRawData {

    private DRawDao rawDao;

    public RRawData(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        rawDao = GGCGriderDB.RawDao();
    }

    public LiveData<DRawDao.McAmortInfo> getMonthlyAmortInfo(String ModelID, int Term){
        return rawDao.getMonthlyAmort(ModelID, Term);
    }

    public LiveData<DRawDao.McDPInfo> getDownpayment(String ModelID){
        return rawDao.getDownpayment(ModelID);
    }

    public LiveData<DRawDao.AppLocalData> getAppLocalData(){
        return rawDao.getAppLocalData();
    }
}
