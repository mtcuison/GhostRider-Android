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
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;

import java.util.List;

public class RCreditApplicationDocument {
    private static final String TAG = "DB_Document_Repository";
    private final DCreditApplicationDocuments documentsDao;
    private final Application application;
    public RCreditApplicationDocument(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        this.documentsDao = GGCGriderDB.DocumentInfoDao();
    }

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    public void insertDocumentInfo(ECreditApplicationDocuments documentsInfo){
        new InsertTask(documentsDao, "insert").execute(documentsInfo);
    }
    public void updateDocumentInfo(ECreditApplicationDocuments documentsInfo){
        new InsertTask(documentsDao, "update").execute(documentsInfo);
    }
    public void insertDocumentsInfo(String transNox){
        new InsertByTransNox(documentsDao).execute(transNox);
    }
    public void updateDocumentsInfo(String transNox, String sFileCD){
        new UpdateByTransNox(documentsDao,sFileCD).execute(transNox);
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocument(String TransNox){
        return documentsDao.getDocument(TransNox);
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocumentInfos(String TransNox){
        return documentsDao.getDocumentInfo(TransNox);
    }
//    public Live



    public LiveData<List<EFileCode>> getDocumentInfoByFile(){
        return documentsDao.getDocumentInfoByFile();
    }
    public void updateDocumentsInfoByFile(String transNox, String sFileCD){
        new updateDocumentsInfoByFile(documentsDao,sFileCD).execute(transNox);
    }



    public List<ECreditApplicationDocuments> getUnsentApplicationDocumentss(){
        return documentsDao.getUnsentApplicationDocumentss();
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocumentDetailForPosting(){
        return documentsDao.getDocumentDetailForPosting();
    }

    private static class InsertTask extends AsyncTask<ECreditApplicationDocuments, Void, String>{
        private final DCreditApplicationDocuments documentsDao;
        private String transInfo;
        public InsertTask(DCreditApplicationDocuments documentsDao, String transInfo) {
            this.documentsDao = documentsDao;
            this.transInfo = transInfo;
        }

        @Override
        protected String doInBackground(ECreditApplicationDocuments... creditApplicationDocuments) {
            if (transInfo.equalsIgnoreCase("insert")){
                documentsDao.insert(creditApplicationDocuments[0]);
            }
            else if(transInfo.equalsIgnoreCase("update")){
                documentsDao.update(creditApplicationDocuments[0]);
            }
            else if(transInfo.equalsIgnoreCase("updates")){
                documentsDao.update(creditApplicationDocuments[0]);
            }
            return null;
        }
    }

    private static class InsertByTransNox extends AsyncTask<String, Void, String>{
        private final DCreditApplicationDocuments documentsDao;
        public InsertByTransNox(DCreditApplicationDocuments documentsDao) {
            this.documentsDao = documentsDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (documentsDao.getDuplicateTransNox(transNox[0]).size()>0){
                documentsDao.updateDocumentsInfos(transNox[0]);

            } else {
                documentsDao.insertDocumentByTransNox(transNox[0]);
            }
            return null;
        }
    }
    private static class UpdateByTransNox extends AsyncTask<String, Void, String>{
        private final DCreditApplicationDocuments documentsDao;
        private final String sFileCd;
        public UpdateByTransNox(DCreditApplicationDocuments documentsDao, String fileCode) {
            this.documentsDao = documentsDao;
            this.sFileCd = fileCode;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (documentsDao.getDuplicateTransNox(transNox[0]).size()>0){
                documentsDao.updateDocumentsInfo(transNox[0],sFileCd);
            }
            return null;
        }
    }

    private static class updateDocumentsInfoByFile extends AsyncTask<String, Void, String>{
        private final DCreditApplicationDocuments documentsDao;
        private final String sFileCd;
        public updateDocumentsInfoByFile(DCreditApplicationDocuments documentsDao, String fileCode) {
            this.documentsDao = documentsDao;
            this.sFileCd = fileCode;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (documentsDao.getDuplicateTransNox(transNox[0]).size()>0){
                documentsDao.updateDocumentsInfoByFile(transNox[0],sFileCd);
            }
            return null;
        }
    }

    public String getImageNextCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Credit_Online_Application_Documents", "sDocTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }
}
