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

package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;

import java.util.List;

public class RCreditApplicant {
    private final DCreditApplicantInfo creditApplicantInfoDao;
    private final LiveData<List<ECreditApplicantInfo>> creditApplicantList;

    private final Application app;

    public RCreditApplicant(Application application){
        this.app = application;
        GGC_GCircleDB GGCGriderDB = GGC_GCircleDB.getInstance(application);
        creditApplicantInfoDao = GGCGriderDB.CreditApplicantDao();
        creditApplicantList = creditApplicantInfoDao.getCreditApplicantList();
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfoLiveData(String TransNox){
        return creditApplicantInfoDao.getCreditApplicantInfo(TransNox);
    }

    public void insertGOCasData(ECreditApplicantInfo creditApplicantInfo){
        new InsertNewApplicationTask(creditApplicantInfoDao).execute(creditApplicantInfo);
    }

    public void updateGOCasData(ECreditApplicantInfo creditApplicantInfo){
        new UpdateGOCasDataTask(creditApplicantInfoDao).execute(creditApplicantInfo);
    }

    public void updateCurrentGOCasData(String GOCasData, String TransNox){
        new UpdateGOCasData(creditApplicantInfoDao, GOCasData).execute(TransNox);
    }

    public void deleteGOCasData(ECreditApplicantInfo creditApplicantInfo){

    }
    public void deleteAllCredit(){
        new DeleteAllCreditTask(creditApplicantInfoDao).execute();
    }
    public LiveData<List<ECreditApplicantInfo>> getCreditApplicantList(){
        return creditApplicantList;
    }

    public LiveData<String> getAppMeansInfo(String TransNox){
        return creditApplicantInfoDao.getAppMeansInfo(TransNox);
    }

    private static class InsertNewApplicationTask extends AsyncTask<ECreditApplicantInfo, Void, Void>{
        private final DCreditApplicantInfo applicantInfo;

        public InsertNewApplicationTask(DCreditApplicantInfo applicantInfo){
            this.applicantInfo = applicantInfo;
        }

        @Override
        protected Void doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            applicantInfo.insert(eCreditApplicantInfos[0]);
            return null;
        }
    }

    private static class UpdateGOCasDataTask extends AsyncTask<ECreditApplicantInfo, Void, Void>{
        private final DCreditApplicantInfo applicantInfo;

        public UpdateGOCasDataTask(DCreditApplicantInfo applicantInfo){
            this.applicantInfo = applicantInfo;
        }

        @Override
        protected Void doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            applicantInfo.update(eCreditApplicantInfos[0]);
            return null;
        }
    }

    private static class UpdateGOCasData extends AsyncTask<String, Void, Void>{
        private final DCreditApplicantInfo applicantInfo;
        private final String GOCasData;

        public UpdateGOCasData(DCreditApplicantInfo applicantInfo, String GOCasData){
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
        private final DCreditApplicantInfo creditDao;

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
