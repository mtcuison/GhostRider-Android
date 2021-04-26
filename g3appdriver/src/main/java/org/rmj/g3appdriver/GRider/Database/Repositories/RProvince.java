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
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;

import java.util.List;

public class RProvince {
    private DProvinceInfo provinceDao;
    private LiveData<List<EProvinceInfo>> allProvinceInfo;
    private LiveData<ArrayAdapter<String>> allProvinceName;
    private LiveData<String[]> allProvinceNames;
    private LiveData<List<EProvinceInfo>> searchProvinceName;

    public RProvince(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        provinceDao = GGCGriderDB.ProvinceDao();
        allProvinceInfo = provinceDao.getAllProvinceInfo();
        allProvinceNames = provinceDao.getAllProvinceNames();
    }

    public void insert(EProvinceInfo provinceInfo){
        new InsertToProvince(provinceDao).execute(provinceInfo);
    }

    public void insertBulkInfo(List<EProvinceInfo> provinceInfoList){
        provinceDao.insertBulkData(provinceInfoList);
    }

    public String getLatestDataTime(){
        return provinceDao.getLatestDataTime();
    }

    public LiveData<List<EProvinceInfo>> getAllProvinceInfo(){
        return allProvinceInfo;
    }

    public LiveData<String[]> getAllProvinceNames(){
        return allProvinceNames;
    }

        public LiveData<String> getProvinceNameFromProvID(String provID) {
            return provinceDao.getProvinceNameFromProvID(provID);
        }

        public static class InsertToProvince extends AsyncTask<EProvinceInfo, Void, Void> {
            private DProvinceInfo provinceInfo;

        public InsertToProvince(DProvinceInfo provinceInfo) {
            this.provinceInfo = provinceInfo;
        }

        @Override
        protected Void doInBackground(EProvinceInfo... EProvinceInfos) {
            provinceInfo.insert(EProvinceInfos[0]);
            return null;
        }
    }
}
