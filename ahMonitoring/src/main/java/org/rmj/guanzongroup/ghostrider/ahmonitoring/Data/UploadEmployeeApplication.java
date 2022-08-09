package org.rmj.guanzongroup.ghostrider.ahmonitoring.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

public class UploadEmployeeApplication {
    private static final String TAG = UploadEmployeeApplication.class.getSimpleName();

    private final Application instance;

    public UploadEmployeeApplication(Application instance) {
        this.instance = instance;
    }

    public void UploadApplication(){
        new UploadTask(instance).execute();
    }

    private static class UploadTask extends AsyncTask<String, Void, String>{

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final SessionManager poSession;
        private final REmployeeLeave poLeave;
        private final REmployeeBusinessTrip poBusTrp;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;

        public UploadTask(Application instance) {
            this.poLeave = new REmployeeLeave(instance);
            this.poBusTrp = new REmployeeBusinessTrip(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poSession = new SessionManager(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try{
                if(poConn.isDeviceConnected()) {
                    PostLeaveApp();

                    PostBusinessTripApp();
                } else {
                    Log.d(TAG, "Unable to post applications. No internet");
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void PostLeaveApp(){
            String lsResult;
            try{
                List<EEmployeeLeave> loLeave = poLeave.getUnsentEmployeeLeave();
                if(loLeave.size()>0){
                    for(int x = 0; x < loLeave.size(); x++){
                        EEmployeeLeave leave = loLeave.get(x);
                        JSONObject param = new JSONObject();
                        param.put("sTransNox", poLeave.getNextLeaveCode());
                        param.put("dTransact", AppConstants.CURRENT_DATE);
                        param.put("sEmployID", leave.getEmployID());
                        param.put("dDateFrom", leave.getDateFrom());
                        param.put("dDateThru", leave.getDateThru());
                        param.put("nNoDaysxx", leave.getNoDaysxx());
                        param.put("sPurposex", leave.getPurposex());
                        param.put("cLeaveTyp", leave.getLeaveTyp());
                        param.put("dAppldFrx", leave.getDateFrom());
                        param.put("dAppldTox", leave.getDateThru());
                        param.put("sEntryByx", leave.getEmployID());
                        param.put("dEntryDte", leave.getEntryDte());
                        param.put("nWithOPay", "0");
                        param.put("nEqualHrs", leave.getEqualHrs());
                        param.put("sApproved", "0");
                        param.put("dApproved", "");
                        param.put("dSendDate", "2017-07-19");
                        param.put("cTranStat", "1");
                        param.put("sModified", leave.getEmployID());

                        lsResult = WebClient.sendRequest(poApi.getUrlSendLeaveApplication(loConfig.isBackUpServer()), param.toString(), poHeaders.getHeaders());
                        if(lsResult == null){
                            Log.d(TAG, "Sending employee leave. server no response");
                        } else {
                            JSONObject loResult = new JSONObject(lsResult);
                            String result = loResult.getString("result");
                            if(result.equalsIgnoreCase("success")){
                                poLeave.updateSendStatus(
                                        new AppConstants().DATE_MODIFIED,
                                        leave.getTransNox(),
                                        loResult.getString("sTransNox"));
                                Log.d("Employee Leave", "Leave info updated!");
                            }
                        }

                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "Sending employee leave. " + e.getMessage());
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void PostBusinessTripApp(){
            String lsResult;
            try{
                List<EEmployeeBusinessTrip> loBusTrp = poBusTrp.getUnsentEmployeeOB();
                if(loBusTrp.size() > 0){
                    for(int x = 0; x < loBusTrp.size(); x++){
                        EEmployeeBusinessTrip loOb = loBusTrp.get(x);
                        JSONObject loJson = new JSONObject();
                        loJson.put("sTransNox", loOb.getTransNox());
                        loJson.put("dTransact", loOb.getTransact());
                        loJson.put("sEmployID", loOb.getEmployee());
                        loJson.put("dDateFrom", loOb.getDateFrom());
                        loJson.put("dDateThru", loOb.getDateThru());
                        loJson.put("sDestinat", loOb.getDestinat());
                        loJson.put("sRemarksx", loOb.getRemarksx());
                        loJson.put("sApproved", "");
                        loJson.put("dApproved", "");
                        loJson.put("dAppldFrx", "");
                        loJson.put("dAppldTox", "");
                        loJson.put("cTranStat", "0");
                        loJson.put("sModified", poSession.getUserID());
                        loJson.put("dModified", AppConstants.CURRENT_DATE);

                        lsResult = WebClient.sendRequest(poApi.getUrlSendObApplication(loConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());
                        if(lsResult == null){
                            Log.d(TAG, "Server no response while posting business trip.");
                        } else {
                            JSONObject jsonResponse = new JSONObject(lsResult);
                            if (jsonResponse.getString("result").equalsIgnoreCase("success")){
                                String sTransnox = jsonResponse.getString("sTransNox");
                                poBusTrp.updateOBSentStatus(loOb.getTransNox(), sTransnox);
                                Log.d(TAG, "Business trip application updated.");
                            } else {
                                JSONObject loError = jsonResponse.getJSONObject("error");
                                Log.d(TAG, "Unable to post ob application. " + loError.getString("message"));
                            }
                        }

                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "Error while sending ob application " + e.getMessage());
            }
        }
    }
}
