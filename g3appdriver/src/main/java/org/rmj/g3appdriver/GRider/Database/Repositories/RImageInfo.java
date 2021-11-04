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
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
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
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        this.imageDao = GGCGriderDB.ImageInfoDao();
    }

    public LiveData<String> getImageLocationFromSrcId(String fsSource) {
        return imageDao.getImageLocationFromSrcId(fsSource);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return imageDao.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public void insertImageInfo(EImageInfo imageInfo){
        imageInfo.setTransNox(getImageNextCode());
        new InsertTask(imageDao, "insert").execute(imageInfo);
    }
    public void insertDownloadedImageInfo(EImageInfo imageInfo){

        new InsertTask(imageDao, "insert").execute(imageInfo);
    }

    public LiveData<EImageInfo> getImageInfo(String sTransNox){
        return imageDao.getImageInfo();
    }

    public void updateImageInfo(String TransNox, String oldTransNox){
        imageDao.updateImageInfo(TransNox, new AppConstants().DATE_MODIFIED, oldTransNox);
    }
    public void updateImageInfos(String TransNox, String sourceNo){
        imageDao.updateImageInfos(TransNox, new AppConstants().DATE_MODIFIED, sourceNo);
    }

    public void updateImageInfo(EImageInfo imageInfo){
        new InsertTask(imageDao, "update").execute(imageInfo);
    }

    public List<EImageInfo> getUnsentSelfieLogImageList(){
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
        return imageDao.getUnsentDCPImageInfoList();
    }

    public LiveData<List<EImageInfo>> getAllImageInfo(){
        return imageDao.getImageInfoList();
    }

    public LiveData<List<EImageInfo>> getImageListInfo(String transNox){
        return imageDao.getImageListInfo(transNox);
    }
    public LiveData<EImageInfo> getImageLogPreview(String transNox){
        return imageDao.getImageLogPreview(transNox);
    }

    public LiveData<List<EImageInfo>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return imageDao.getCurrentLogTimeIfExist(DateLog);
    }

    /**
     *
     * @param TransNox pass the transaction no. of Credit_Online_Application
     * @return list of all scanned documents which are stored in local while internet is not available
     */
    public List<EImageInfo> getUnsentLoanAppDocFiles(String TransNox){
        return imageDao.getUnsentLoanAppDocFiles(TransNox);
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

    public EImageInfo getDCPImageInfoForPosting(String TransNox, String AccntNo){
        return imageDao.getDCPImageInfoForPosting(TransNox, AccntNo);
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
