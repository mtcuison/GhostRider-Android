package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rmj.g3appdriver.etc.WebFileServer.*;

public class InternetStatusReciever extends BroadcastReceiver {
    private static final String TAG = InternetStatusReciever.class.getSimpleName();

    private final Application instance;

    private List<ELog_Selfie> loginDetails = new ArrayList<>();
    private List<EImageInfo> loginImageInfo = new ArrayList<>();
    private List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();

    public InternetStatusReciever(Application instance) {
        this.instance = instance;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "Internet Status Received.");
        SendDataTask poSendTask = new SendDataTask(instance);
        poSendTask.setLoginImageInfo(loginImageInfo);
        poSendTask.setLogSelfies(loginDetails);
        poSendTask.setDCPPaidInfo(collectionDetails);
        poSendTask.execute();
    }

    public void setLoginImageInfo(List<EImageInfo> imageInfoList){
        this.loginImageInfo = imageInfoList;
    }

    public void setDCPPaidInfo(List<EDCPCollectionDetail> collectionDetails){
        this.collectionDetails = collectionDetails;
    }

    public void setLoginDetails(List<ELog_Selfie> loginDetails){
        this.loginDetails = loginDetails;
    }

    private static class SendDataTask extends AsyncTask<Void, Void, String>{
        private final Application instance;

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final SessionManager poSession;
        private final Telephony poDevice;
        private final RLogSelfie poLog;

        private final RImageInfo poImage;
        private final RDailyCollectionPlan poDcp;

        private List<EImageInfo> loginImageInfo = new ArrayList<>();
        private List<ELog_Selfie> logSelfies = new ArrayList<>();
        private List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();

        public SendDataTask(Application instance) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poSession = new SessionManager(instance);
            this.poImage = new RImageInfo(instance);
            this.poDevice = new Telephony(instance);
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poLog = new RLogSelfie(instance);
        }

        public void setLoginImageInfo(List<EImageInfo> imageInfoList){
            this.loginImageInfo = imageInfoList;
        }

        public void setDCPPaidInfo(List<EDCPCollectionDetail> collectionDetails){
            this.collectionDetails = collectionDetails;
        }

        public void setLogSelfies(List<ELog_Selfie> logSelfies){
            this.logSelfies = logSelfies;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            if(poConn.isDeviceConnected()){
                String lsClient = RequestClientToken("IntegSys", poSession.getClientId(), poSession.getUserID());
                String lsAccess = RequestAccessToken(lsClient);

                if(!lsClient.isEmpty() && !lsAccess.isEmpty()){
                    if(loginImageInfo != null) {
                        if (loginImageInfo.size() > 0) {
                            for (int x = 0; x < loginImageInfo.size(); x++) {
                                try {
                                    EImageInfo loImage = loginImageInfo.get(x);
                                    org.json.simple.JSONObject loResult = WebFileServer.UploadFile(
                                            loImage.getFileLoct(),
                                            lsAccess,
                                            loImage.getFileCode(),
                                            loImage.getDtlSrcNo(),
                                            loImage.getImageNme(),
                                            poSession.getBranchCode(),
                                            loImage.getSourceCD(),
                                            loImage.getTransNox(),
                                            "");

                                    String lsResponse = (String) loResult.get("result");
                                    Log.e(TAG, "Uploading image result : " + lsResponse);

                                    if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {

                                        String lsTransNo = (String) loResult.get("sTransNox");
                                        poImage.updateImageInfo(lsTransNo, loImage.getTransNox());

                                    }

                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    if(logSelfies != null) {
                        if (logSelfies.size() > 0) {
                            for (int x = 0; x < logSelfies.size(); x++) {
                                try {
                                    ELog_Selfie selfieLog = logSelfies.get(x);
                                    JSONObject loJson = new JSONObject();
                                    loJson.put("sEmployID", selfieLog.getEmployID());
                                    loJson.put("dLogTimex", selfieLog.getLogTimex());
                                    loJson.put("nLatitude", selfieLog.getLatitude());
                                    loJson.put("nLongitud", selfieLog.getLongitud());

                                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_POST_SELFIELOG, loJson.toString(), poHeaders.getHeaders());

                                    if (lsResponse == null) {
                                        Log.e(TAG, "Sending selfie log info. Server no response");
                                    } else {
                                        JSONObject loResponse = new JSONObject(lsResponse);
                                        String result = loResponse.getString("result");
                                        if (result.equalsIgnoreCase("success")) {
                                            String TransNox = loResponse.getString("sTransNox");
                                            String OldTrans = selfieLog.getTransNox();
                                            poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                                            Log.e(TAG, "Selfie log has been uploaded successfully.");
                                        }
                                    }

                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    if(collectionDetails != null) {
                        if (collectionDetails.size() > 0) {
                            for (int x = 0; x < collectionDetails.size(); x++) {
                                try {
                                    EDCPCollectionDetail loDetail = collectionDetails.get(x);
                                    JSONObject loData = new JSONObject();
                                    loData.put("sPRNoxxxx", loDetail.getPRNoxxxx());
                                    loData.put("nTranAmtx", loDetail.getTranAmtx());
                                    loData.put("nDiscount", loDetail.getDiscount());
                                    loData.put("nOthersxx", loDetail.getOthersxx());
                                    loData.put("cTranType", loDetail.getTranType());
                                    loData.put("nTranTotl", loDetail.getTranTotl());
                                    if (loDetail.getBankIDxx() == null) {
                                        loData.put("sBankIDxx", "");
                                        loData.put("sCheckDte", "");
                                        loData.put("sCheckNox", "");
                                        loData.put("sCheckAct", "");
                                    } else {
                                        loData.put("sBankIDxx", loDetail.getBankIDxx());
                                        loData.put("sCheckDte", loDetail.getCheckDte());
                                        loData.put("sCheckNox", loDetail.getCheckNox());
                                        loData.put("sCheckAct", loDetail.getCheckAct());
                                    }
                                    JSONObject loJson = new JSONObject();
                                    loJson.put("sTransNox", loDetail.getTransNox());
                                    loJson.put("nEntryNox", loDetail.getEntryNox());
                                    loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                                    loJson.put("sRemCodex", loDetail.getRemCodex());
                                    loJson.put("sJsonData", loData);
                                    loJson.put("dReceived", "");
                                    loJson.put("sUserIDxx", poSession.getUserID());
                                    loJson.put("sDeviceID", poDevice.getDeviceID());
                                    Log.e(TAG, loJson.toString());
                                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());

                                    if (lsResponse == null) {
                                        lsResponse = AppConstants.SERVER_NO_RESPONSE();
                                    } else {
                                        JSONObject loResponse = new JSONObject(lsResponse);
                                        if (loResponse.getString("result").equalsIgnoreCase("success")) {
                                            loDetail.setSendStat("1");
                                            loDetail.setModified(AppConstants.DATE_MODIFIED);
                                            poDcp.updateCollectionDetailInfo(loDetail);
                                        }
                                    }

                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            return "Finished";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
