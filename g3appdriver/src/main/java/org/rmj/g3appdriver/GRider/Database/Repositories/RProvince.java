package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
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
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        provinceDao = appDatabase.ProvinceDao();
        allProvinceInfo = provinceDao.getAllProvinceInfo();
        allProvinceNames = provinceDao.getAllProvinceNames();
    }

    public void insert(EProvinceInfo provinceInfo){
        new InsertToProvince(provinceDao).execute(provinceInfo);
    }

    public void insertBulkInfo(List<EProvinceInfo> provinceInfoList){
        provinceDao.insertBulkData(provinceInfoList);
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
