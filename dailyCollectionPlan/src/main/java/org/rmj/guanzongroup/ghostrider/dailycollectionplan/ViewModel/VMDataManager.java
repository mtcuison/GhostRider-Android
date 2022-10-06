/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;

import java.util.List;

public class VMDataManager extends AndroidViewModel {

    private final SelfieLog poLog;
    private final RImageInfo poImage;
    private final RDailyCollectionPlan poDcp;
    private final RCreditApplication poCreditApp;
    private final RBranchLoanApplication poLoan;
    private final RCreditApplicationDocument poDocs;

    public interface OnDataFetchListener{
        void OnCheck();
        void OnCheckProgress(int table);
        void OnCheckLocalData(boolean hasPendingData);
    }

    public VMDataManager(@NonNull Application instance) {
        super(instance);
        this.poImage = new RImageInfo(instance);
        this.poDcp = new RDailyCollectionPlan(instance);
        this.poLog = new SelfieLog(instance);
        this.poCreditApp = new RCreditApplication(instance);
        this.poLoan = new RBranchLoanApplication(instance);
        this.poDocs = new RCreditApplicationDocument(instance);
    }

    public void checkData(OnDataFetchListener listener){
        new CheckDataTask(listener).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class CheckDataTask extends AsyncTask<String, Integer, Boolean>{

        OnDataFetchListener mListener;

        public CheckDataTask(OnDataFetchListener mListener) {
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnCheck();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean hasData = false;
            try{
                List<EImageInfo> loginImageInfo = poImage.getUnsentSelfieLogImageList();
                if(loginImageInfo.size() > 0){
                    hasData = true;
                }
                publishProgress(1);

                List<EDCPCollectionDetail> collectionDetails = poDcp.getUnsentPaidCollection();
                if(collectionDetails.size() > 0){
                    hasData = true;
                }
                publishProgress(1);

                List<ECreditApplication> loanApplications = poCreditApp.getUnsentLoanApplication();
                if(loanApplications.size() > 0){
                    hasData = true;
                }
                publishProgress(1);

                List<ECreditApplicationDocuments> docsFile = poDocs.getUnsentApplicationDocumentss();
                if(docsFile.size() > 0){
                    hasData = true;
                }
                publishProgress(1);

            } catch (Exception e){
                e.printStackTrace();
            }
            return hasData;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.OnCheckLocalData(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mListener.OnCheckProgress(values[0]);
        }
    }

}
