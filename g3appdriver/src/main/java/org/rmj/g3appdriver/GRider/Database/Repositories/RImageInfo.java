package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

public class RImageInfo {
    private static final String TAG = "DB_Image_Repository";
    private final DImageInfo imageDao;

    private final Application application;

    public RImageInfo(Application application){
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.imageDao = appDatabase.ImageInfoDao();
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return imageDao.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public void insertImageInfo(EImageInfo imageInfo){
        new InsertTask(imageDao, "insert").execute(imageInfo);
    }

    public LiveData<EImageInfo> getImageInfo(String sTransNox){
        return imageDao.getImageInfo();
    }

    public void updateImageInfo(String TransNox, String oldTransNox){
        imageDao.updateImageInfo(TransNox, AppConstants.DATE_MODIFIED, oldTransNox);
    }

    public void updateImageInfo(EImageInfo imageInfo){
        new InsertTask(imageDao, "update").execute(imageInfo);
    }

    public LiveData<List<EImageInfo>> getUnsentSelfieLogImageList(){
        return imageDao.getUnsentLoginImageInfo();
    }

//    public void updateImageInfo(EImageInfo imgInfo){
//        imageDao.updateImageInfo(imgInfo);
//    }

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    public LiveData<List<EImageInfo>> getUnsentImageList(){
        return imageDao.getUnsentImageInfoList();
    }

    public LiveData<List<EImageInfo>> getAllImageInfo(){
        return imageDao.getImageInfoList();
    }

    public LiveData<List<EImageInfo>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return imageDao.getCurrentLogTimeIfExist(DateLog);
    }

    public LiveData<List<EImageInfo>> getLoginImageInfo(){
        return imageDao.getUnsentLoginImageInfo();
    }

    private static class InsertTask extends AsyncTask<EImageInfo, Void, String>{
        private final DImageInfo imageDao;
        private String transInfo;
        public InsertTask(DImageInfo imageDao, String transInfo) {
            this.imageDao = imageDao;
            this.transInfo = transInfo;
        }

        @Override
        protected String doInBackground(EImageInfo... eImageInfos) {
            if (transInfo.equalsIgnoreCase("insert")){
                imageDao.insert(eImageInfos[0]);
                Log.e(TAG, "Image info has been save in background!");
            }else{
                imageDao.updateImageInfo(eImageInfos[0]);
                Log.e(TAG, "Image info has been update in background!");
            }
            return null;
        }
    }

    public String getImageNextCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Image_Information", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }
}
