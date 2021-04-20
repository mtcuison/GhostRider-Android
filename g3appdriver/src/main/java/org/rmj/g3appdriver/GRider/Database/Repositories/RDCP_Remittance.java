package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

public class RDCP_Remittance {
    private static final String TAG = RDCP_Remittance.class.getSimpleName();

    private final DDCP_Remittance remitDao;

    public interface OnCalculateCallback{
        void OnCalculate(String result);
    }

    public RDCP_Remittance(Application application) {
        this.remitDao = GGC_GriderDB.getInstance(application).DCPRemitanceDao();
    }

    public void initializeRemittance(String dTransact){
        new InitializeRemitTask().execute(dTransact);
    }

    public void insert(EDCP_Remittance remittance){
        remitDao.insert(remittance);
    }

    public String getTransnoxMaster(String dTransact){
        return remitDao.getMasterTransNox(dTransact);
    }

    public String getRemittanceEntry(String dTransact){
        return remitDao.getRemittanceEntry(dTransact);
    }

    public void updateSendStat(String TransNox, String EntryNox){
        remitDao.updateSendStatus(AppConstants.DATE_MODIFIED, TransNox, EntryNox);
    }

    public LiveData<String> getTotalRemittedCollection(String dTransact){
        return remitDao.getTotalRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalCashRemittedCollection(String dTransact){
        return remitDao.getTotalCashRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalCheckRemittedCollection(String dTransact){
        return remitDao.getTotalCheckRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBranchRemittedCollection(String dTransact){
        return remitDao.getTotalBranchRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBankRemittedCollection(String dTransact){
        return remitDao.getTotalBankRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalOtherRemittedCollection(String dTransact){
        return remitDao.getTotalOtherRemittedCollection(dTransact);
    }

    public LiveData<String> getCheckOnHand(String dTransact){
        return remitDao.getCheckOnHand(dTransact);
    }

    public LiveData<String> getCashOnHand(String dTransact){
        return remitDao.getCashOnHand(dTransact);
    }

    public void Calculate_COH_Remitted(String dTransact, OnCalculateCallback callback){
        new Calc_COH_Remitted(callback).execute(dTransact);
    }

    public void Calculate_Check_Remitted(String dTransact, OnCalculateCallback callback){
        new Calc_Check_Remitted(callback).execute(dTransact);
    }

    @SuppressLint("StaticFieldLeak")
    private class InitializeRemitTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String dTransact = strings[0];
            if(remitDao.checkRemittanceExist(dTransact) == null){
                remitDao.initializeCurrentDayRemittanceField(dTransact);
            }
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Calc_COH_Remitted extends AsyncTask<String, Void, String>{
        private final OnCalculateCallback mListner;

        public Calc_COH_Remitted(OnCalculateCallback mListner) {
            this.mListner = mListner;
        }

        @Override
        protected String doInBackground(String... strings) {
            String dTransact = strings[0];
            String lsResultx = "0";
            try {
                double lnCashOHx = 0;
                String lsCashOHx = remitDao.getCollectedCash(dTransact);
                if(lsCashOHx != null) {
                    lnCashOHx = Integer.parseInt(lsCashOHx);
                }

                double lnRemittd = 0;
                String lsRemittd = remitDao.getRemittedCash(dTransact);
                if(lsRemittd != null){
                    lnRemittd = Double.parseDouble(lsRemittd);
                }

                double lnTotalxx = lnCashOHx - lnRemittd;

                lsResultx = String.valueOf(lnTotalxx);
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResultx;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListner.OnCalculate(s);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Calc_Check_Remitted extends AsyncTask<String, Void, String>{
        private final OnCalculateCallback mListner;

        public Calc_Check_Remitted(OnCalculateCallback mListner) {
            this.mListner = mListner;
        }

        @Override
        protected String doInBackground(String... strings) {
            String dTransact = strings[0];
            String lsResultx = "0";
            try {
                double lnCashOHx = 0;
                String lsCashOHx = remitDao.getCollectedCheck(dTransact);
                if(lsCashOHx != null) {
                    lnCashOHx = Integer.parseInt(lsCashOHx);
                }

                double lnRemittd = 0;
                String lsRemittd = remitDao.getRemittedCheck(dTransact);
                if(lsRemittd != null){
                    lnRemittd = Double.parseDouble(lsRemittd);
                }

                double lnTotalxx = lnCashOHx - lnRemittd;

                lsResultx = String.valueOf(lnTotalxx);
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResultx;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListner.OnCalculate(s);
        }
    }
}
