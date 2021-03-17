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
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rmj.g3appdriver.etc.WebFileServer.*;

public class InternetStatusReciever extends BroadcastReceiver {
    private static final String TAG = InternetStatusReciever.class.getSimpleName();

    private final Application instance;

    private ConnectionUtil poConn;

    private List<ELog_Selfie> loginDetails = new ArrayList<>();
    private List<EImageInfo> loginImageInfo = new ArrayList<>();
    private List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();

    public InternetStatusReciever(Application instance) {
        this.instance = instance;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        poConn = new ConnectionUtil(context);

        if(poConn.isDeviceConnected()) {
            Log.e(TAG, "Internet Status Received.");
            SendDataTask poSendTask = new SendDataTask(instance);
            poSendTask.setLoginImageInfo(loginImageInfo);
            poSendTask.setLogSelfies(loginDetails);
            poSendTask.setDCPPaidInfo(collectionDetails);
            poSendTask.execute();
        }
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

    private static class SendDataTask extends AsyncTask<Void, String, String>{
        private final Application instance;

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            GNotifBuilder.createNotification(instance, "GhostRider", "Checking local data...", GNotifBuilder.DCP_DATA).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String Message = "Local data and server is updated.";
            publishProgress("Requesting access token...");
            String lsClient = RequestClientToken("IntegSys", poSession.getClientId(), poSession.getUserID());
            String lsAccess = RequestAccessToken(lsClient);
            if(loginImageInfo != null) {
                if (loginImageInfo.size() > 0) {
                    if(lsAccess.isEmpty()){
                    publishProgress("Sending selfie log images...");
                    boolean[] isSent = new boolean[loginImageInfo.size()];
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
                                isSent[x] = true;
                            } else {
                                isSent[x] = false;
                            }

                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            isSent[x] = false;
                        }
                    }
                    boolean allDataSent = true;
                    for (boolean b : isSent)
                        if (!b) {
                            allDataSent = false;
                            break;
                        }
                    if(allDataSent){
                        Message = "All selfie login images has been sent.\n ";
                    } else {
                        Message = "Some selfie login images has been sent.\n ";
                    }
                } else {
                    Message = "Failed to send images. Reason: failed requesting access token.\n ";
                    publishProgress("Failed requesting access token. Sending selfie image failed...");
                }
            }
            }

            if(logSelfies != null) {
                if (logSelfies.size() > 0) {
                    publishProgress("Sending selfie log info...");
                    boolean[] isSent = new boolean[logSelfies.size()];
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
                                isSent[x] = false;
                            } else {
                                JSONObject loResponse = new JSONObject(lsResponse);
                                String result = loResponse.getString("result");
                                if (result.equalsIgnoreCase("success")) {
                                    String TransNox = loResponse.getString("sTransNox");
                                    String OldTrans = selfieLog.getTransNox();
                                    poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                                    Log.e(TAG, "Selfie log has been uploaded successfully.");
                                    isSent[x] = true;
                                } else {
                                    isSent[x] = false;
                                }
                            }

                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            isSent[x] = false;
                        }
                    }
                    boolean allDataSent = true;
                    for (boolean b : isSent)
                        if (!b) {
                            allDataSent = false;
                            break;
                        }
                    if(allDataSent){
                        Message = Message + "All selfie login info has been sent.\n";
                    } else {
                        Message = Message + "Some selfie login info has been sent.\n";
                    }
                }
            }

            if(collectionDetails != null) {
                if (collectionDetails.size() > 0) {
                    publishProgress("Sending collection detail info...");
                    boolean[] isSent = new boolean[collectionDetails.size()];
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
                                Log.e(TAG, "Sending selfie log info. Server no response");
                                isSent[x] = false;
                            } else {
                                JSONObject loResponse = new JSONObject(lsResponse);
                                if (loResponse.getString("result").equalsIgnoreCase("success")) {
                                    loDetail.setSendStat("1");
                                    loDetail.setModified(AppConstants.DATE_MODIFIED);
                                    poDcp.updateCollectionDetailInfo(loDetail);
                                    isSent[x] = true;
                                } else {
                                    isSent[x] = false;
                                }
                            }

                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            isSent[x] = false;
                        }
                    }
                    boolean allDataSent = true;
                    for (boolean b : isSent)
                        if (!b) {
                            allDataSent = false;
                            break;
                        }
                    if(allDataSent){
                        Message = Message + "DCP Paid transactions has been sent.\n";
                    } else {
                        Message = Message + "Unable to send some DCP Paid transactions.\n";
                    }
                }
            }
            publishProgress("Updating data to server has finished.");
            return Message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG, s);
            GNotifBuilder.createNotification(instance, "GhostRider", s, GNotifBuilder.DCP_DATA).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            GNotifBuilder.createNotification(instance, "GhostRider", values[0], GNotifBuilder.SELFIE_LOG).show();
        }
    }
}
