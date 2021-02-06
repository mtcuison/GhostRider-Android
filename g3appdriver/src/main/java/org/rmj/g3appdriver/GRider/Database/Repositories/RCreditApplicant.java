package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;

import java.util.List;

public class RCreditApplicant {
    private final DCreditApplicantInfo creditApplicantInfoDao;
    private final LiveData<List<ECreditApplicantInfo>> creditApplicantList;

    public RCreditApplicant(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        creditApplicantInfoDao = appDatabase.CreditApplicantDao();
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

    public void deleteGOCasData(ECreditApplicantInfo creditApplicantInfo){

    }

    public LiveData<List<ECreditApplicantInfo>> getCreditApplicantList(){
        return creditApplicantList;
    }

    public LiveData<String> getAppMeansInfo(String TransNox){
        return creditApplicantInfoDao.getAppMeansInfo(TransNox);
    }

    private static class InsertNewApplicationTask extends AsyncTask<ECreditApplicantInfo, Void, Void>{
        private DCreditApplicantInfo applicantInfo;

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
        private DCreditApplicantInfo applicantInfo;

        public UpdateGOCasDataTask(DCreditApplicantInfo applicantInfo){
            this.applicantInfo = applicantInfo;
        }

        @Override
        protected Void doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            applicantInfo.update(eCreditApplicantInfos[0]);
            return null;
        }
    }
}
