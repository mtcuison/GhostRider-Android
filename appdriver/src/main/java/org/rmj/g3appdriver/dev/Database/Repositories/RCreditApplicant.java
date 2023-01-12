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
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

import java.util.List;

public class RCreditApplicant {
    private final org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo creditApplicantInfoDao;
    private final LiveData<List<org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo>> creditApplicantList;

    private final Application app;

    public RCreditApplicant(Application application){
        this.app = application;
        org.rmj.g3appdriver.dev.Database.GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        creditApplicantInfoDao = GGCGriderDB.CreditApplicantDao();
        creditApplicantList = creditApplicantInfoDao.getCreditApplicantList();
    }

    public LiveData<org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo> getCreditApplicantInfoLiveData(String TransNox){
        return creditApplicantInfoDao.getCreditApplicantInfo(TransNox);
    }

    public void insertGOCasData(org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo creditApplicantInfo){
        new InsertNewApplicationTask(creditApplicantInfoDao).execute(creditApplicantInfo);
    }

    public void updateGOCasData(org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo creditApplicantInfo){
        new UpdateGOCasDataTask(creditApplicantInfoDao).execute(creditApplicantInfo);
    }

    public void updateCurrentGOCasData(String GOCasData, String TransNox){
        new UpdateGOCasData(creditApplicantInfoDao, GOCasData).execute(TransNox);
    }

    public void deleteGOCasData(org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo creditApplicantInfo){

    }
    public void deleteAllCredit(){
        new DeleteAllCreditTask(creditApplicantInfoDao).execute();
    }
    public LiveData<List<org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo>> getCreditApplicantList(){
        return creditApplicantList;
    }

    public LiveData<String> getAppMeansInfo(String TransNox){
        return creditApplicantInfoDao.getAppMeansInfo(TransNox);
    }

    private static class InsertNewApplicationTask extends AsyncTask<org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo, Void, Void>{
        private final org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo;

        public InsertNewApplicationTask(org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo){
            this.applicantInfo = applicantInfo;
        }

        @Override
        protected Void doInBackground(org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo... eCreditApplicantInfos) {
            applicantInfo.insert(eCreditApplicantInfos[0]);
            return null;
        }
    }

    private static class UpdateGOCasDataTask extends AsyncTask<org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo, Void, Void>{
        private final org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo;

        public UpdateGOCasDataTask(org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo){
            this.applicantInfo = applicantInfo;
        }

        @Override
        protected Void doInBackground(org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo... eCreditApplicantInfos) {
            applicantInfo.update(eCreditApplicantInfos[0]);
            return null;
        }
    }

    private static class UpdateGOCasData extends AsyncTask<String, Void, Void>{
        private final org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo;
        private final String GOCasData;

        public UpdateGOCasData(org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo applicantInfo, String GOCasData){
            this.applicantInfo = applicantInfo;
            this.GOCasData = GOCasData;
        }

        @Override
        protected Void doInBackground(String... transNox) {
            String TransNox = transNox[0];
            ECreditApplicantInfo loInfo = applicantInfo.getCurrentCreditApplicantInfo(TransNox);
            loInfo.setDetlInfo(GOCasData);
            applicantInfo.update(loInfo);
            return null;
        }
    }

    public void updateApplicantResidenceInfo(String TransNox, String Residence){
        creditApplicantInfoDao.updateApplicantResidenceInfo(TransNox, Residence);
    }
    public void updateOtherInfo(String TransNox, String OtherInfo){
        creditApplicantInfoDao.updateOtherInfo(TransNox, OtherInfo);
    }

    public static class DeleteAllCreditTask extends AsyncTask<Void, Void, Void>{
        private final org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo creditDao;

        public DeleteAllCreditTask(DCreditApplicantInfo creditDao) {
            this.creditDao = creditDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            creditDao.deleteAllCreditApp();
            return null;
        }
    }
}
