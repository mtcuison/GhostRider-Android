package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMCollectionRemittance extends AndroidViewModel {

    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RDCP_Remittance poRemit;
    private final ConnectionUtil poConn;
    private final HttpHeaders poHeaders;

    private String dTransact;

    public interface OnRemitCollectionCallback{
        void OnRemit();
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMCollectionRemittance(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poRemit = new RDCP_Remittance(application);
        this.poConn = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
    }

    public void setTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getTotalCollectedCash(){
        return poDcp.getCollectedTotalPayment(dTransact);
    }

    public LiveData<String> getTotalCollectedCheck(){
        return poDcp.getCollectedTotalCheckPayment(dTransact);
    }

    public LiveData<String> getTotalBranchRemittedCollection(){
        return poRemit.getTotalBranchRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBankRemittedCollection(){
        return poRemit.getTotalBankRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalOtherRemittedCollection(){
        return poRemit.getTotalOtherRemittedCollection(dTransact);
    }

    public void Calculate_COH_Remitted(RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_COH_Remitted(dTransact, callback);
    }

    public void Calculate_Check_Remitted(RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_Check_Remitted(dTransact, callback);
    }

    public void RemitCollection(EDCP_Remittance foRemittance, OnRemitCollectionCallback callback){
        new RemitCollectionTask(callback).execute(foRemittance);
    }

    @SuppressLint("StaticFieldLeak")
    private class RemitCollectionTask extends AsyncTask<EDCP_Remittance, Void, String>{

        OnRemitCollectionCallback callback;

        public RemitCollectionTask(OnRemitCollectionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(EDCP_Remittance... edcp_remittances) {
            String lsResult = "";
            try{
                poRemit.insert(edcp_remittances[0]);

                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Cash Remittance has been save.");
                } else {

                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
