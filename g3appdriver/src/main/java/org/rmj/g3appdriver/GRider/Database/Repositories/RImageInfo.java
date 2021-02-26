package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

public class RImageInfo {
    private static final String TAG = "DB_Image_Repository";
    private final DImageInfo imageDao;

    public RImageInfo(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.imageDao = appDatabase.ImageInfoDao();
    }

    public void insertImageInfo(EImageInfo imageInfo){
        new InsertTask(imageDao).execute(imageInfo);
    }

    public LiveData<EImageInfo> getImageInfo(String sTransNox){
        return imageDao.getImageInfo(sTransNox);
    }

    private static class InsertTask extends AsyncTask<EImageInfo, Void, String>{
        private final DImageInfo imageDao;

        public InsertTask(DImageInfo imageDao) {
            this.imageDao = imageDao;
        }

        @Override
        protected String doInBackground(EImageInfo... eImageInfos) {
            imageDao.insert(eImageInfos[0]);
            Log.e(TAG, "Image info has been save!");
            return null;
        }
    }
}
