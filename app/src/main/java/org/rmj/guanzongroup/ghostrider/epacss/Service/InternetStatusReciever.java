/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

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
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
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
            poSendTask.execute();
        }
    }

    private class SendDataTask extends AsyncTask<Void, String, String>{
        private final Application instance;

        private final HttpHeaders poHeaders;
        private final SessionManager poSession;
        private final Telephony poDevice;

        private final RLogSelfie poLog;
        private final RImageInfo poImage;
        private final RDailyCollectionPlan poDcp;
        private final RCreditApplication poCreditApp;
        private final RBranchLoanApplication poLoan;
        private final RCreditApplicationDocument poDocs;
        private final RDCP_Remittance poRemit;

        private String lsClient;
        private String lsAccess;

        private String Message;

        private List<ELog_Selfie> loginDetails = new ArrayList<>();
        private List<EImageInfo> loginImageInfo = new ArrayList<>();
        private List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();
        private List<ECreditApplication> loanApplications = new ArrayList<>();
        private List<EImageInfo> loanDocs = new ArrayList<>();
        private List<ECreditApplicationDocuments> docsFile = new ArrayList<>();

        /**
         * this List handles all the sent application to sever
         *
         */
        private List<ECreditApplication> sentAppl = new ArrayList<>();

        public SendDataTask(Application instance) {
            this.instance = instance;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poSession = new SessionManager(instance);
            this.poDevice = new Telephony(instance);
            this.poImage = new RImageInfo(instance);
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poLog = new RLogSelfie(instance);
            this.poCreditApp = new RCreditApplication(instance);
            this.poLoan = new RBranchLoanApplication(instance);
            this.poDocs = new RCreditApplicationDocument(instance);
            this.poRemit = new RDCP_Remittance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            Message = "Local data and server is updated.";
            try {
                loginDetails = poLog.getUnsentSelfieLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                loginImageInfo = poImage.getUnsentSelfieLogImageList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                collectionDetails = poDcp.getUnsentPaidCollection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                loanApplications = poCreditApp.getUnsentLoanApplication();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                docsFile = poDocs.getUnsentApplicationDocumentss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //loanDocs = poImage.getUnsentLoanAppDocFiles();
            try {
                uploadLoginImages();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                uploadLoginDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                uploadPaidCollectionDetail();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                uploadLoanApplications();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                uploadLoanApplicationsDocuments();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, values[0], GNotifBuilder.SYNC_PROGRESS).show();
        }

        void uploadLoginImages() throws Exception{
            if(loginImageInfo.size() > 0 || loginDetails.size() > 0 || collectionDetails.size() > 0) {
                publishProgress("Requesting access token...");
                lsClient = RequestClientToken("IntegSys", poSession.getClientId(), poSession.getUserID());
                lsAccess = RequestAccessToken(lsClient);

                if (loginImageInfo.size() > 0) {
                    if (!lsAccess.isEmpty()) {
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
                        if (allDataSent) {
                            Message = "All selfie login images has been sent.\n ";
                        } else {
                            Message = "Some selfie login images has been sent.\n ";
                        }
                    } else {
                        Message = "Failed to send images. Reason: failed requesting access token.\n ";
                        publishProgress("Failed requesting access token. Sending selfie image failed...");
                    }
                }

                publishProgress("Updating data to server has finished.");
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void uploadLoginDetails() throws Exception{
            if (loginDetails.size() > 0) {
                publishProgress("Sending selfie log info...");
                boolean[] isSent = new boolean[loginDetails.size()];
                for (int x = 0; x < loginDetails.size(); x++) {
                    try {
                        ELog_Selfie selfieLog = loginDetails.get(x);
                        JSONObject loJson = new JSONObject();
                        loJson.put("sEmployID", selfieLog.getEmployID());
                        loJson.put("dLogTimex", selfieLog.getLogTimex());
                        loJson.put("nLatitude", selfieLog.getLatitude());
                        loJson.put("nLongitud", selfieLog.getLongitud());

                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_POST_SELFIELOG, loJson.toString(), poHeaders.getHeaders());

                        if (lsResponse == null) {
                            Log.e(TAG, "Sending selfie log info. Server no response");
                            publishProgress("Sending selfie log info. Server no response");
                            isSent[x] = false;
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            String result = loResponse.getString("result");
                            if (result.equalsIgnoreCase("success")) {
                                String TransNox = loResponse.getString("sTransNox");
                                String OldTrans = selfieLog.getTransNox();
                                poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                                Log.e(TAG, "Selfie log has been uploaded successfully.");
                                publishProgress("Selfie log has been uploaded successfully.");
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
                if (allDataSent) {
                    Message = Message + "All selfie login info has been sent.\n";
                } else {
                    Message = Message + "Some selfie login info has been sent.\n";
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void uploadPaidCollectionDetail() throws Exception{
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
                                loDetail.setModified(new AppConstants().DATE_MODIFIED);
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
                if (allDataSent) {
                    Message = Message + "DCP Paid transactions has been sent.\n";
                } else {
                    Message = Message + "Unable to send some DCP Paid transactions.\n";
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void uploadLoanApplications() throws Exception{
            if(loanApplications.size() > 0){
                publishProgress("Sending loan applications...");
                boolean[] isSent = new boolean[loanApplications.size()];
                for(int x = 0; x < loanApplications.size(); x++){
                    try{
                        ECreditApplication loLoan = loanApplications.get(x);
                        JSONObject params = new JSONObject(loLoan.getDetlInfo());
                        params.put("dCreatedx", loLoan.getCreatedx());

                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_SUBMIT_ONLINE_APPLICATION, params.toString(), poHeaders.getHeaders());
                        if(lsResponse != null) {
                            JSONObject loResponse = new JSONObject(lsResponse);

                            String result = loResponse.getString("result");
                            if(result.equalsIgnoreCase("success")){
                                String lsTransNox = loResponse.getString("sTransNox");
                                poCreditApp.updateSentLoanAppl(loLoan.getTransNox(), lsTransNox);
                                sentAppl.add(loLoan);
                                isSent[x] = true;
                            } else {
                                isSent[x] = false;
                            }
                        }

                        Thread.sleep(1000);
                    } catch (Exception e){
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
                if (allDataSent) {
                    Message = Message + "Loan Applications has been sent.\n";
                } else {
                    Message = Message + "Unable to send some loan applications.\n";
                }
            }
        }

        void uploadLoanApplicationsDocuments() throws Exception{
            if (lsClient == null || lsAccess == null || lsClient.isEmpty() || lsAccess.isEmpty()) {
                lsClient = RequestClientToken("IntegSys", poSession.getClientId(), poSession.getUserID());
                lsAccess = RequestAccessToken(lsClient);
            }

            if(docsFile.size() > 0) {
                for (int i = 0; i < docsFile.size(); i++) {
                    loanDocs = poImage.getUnsentLoanAppDocFiles(docsFile.get(i).getTransNox());

                    if(loanDocs.size() > 0) {

                        if (!lsAccess.isEmpty()) {
                            for(int x = 0; x < loanDocs.size(); x++){
                                try{
                                    publishProgress("Sending selfie log images...");
                                    boolean[] isSent = new boolean[loanDocs.size()];
                                    try {
                                        EImageInfo loImage = loanDocs.get(x);
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
                                    boolean allDataSent = true;
                                    for (boolean b : isSent)
                                        if (!b) {
                                            allDataSent = false;
                                            break;
                                        }
                                    if (allDataSent) {
                                        Message = "All selfie login images has been sent.\n ";
                                    } else {
                                        Message = "Some selfie login images has been sent.\n ";
                                    }
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Message = "Failed to send images. Reason: failed requesting access token.\n ";
                            publishProgress("Failed requesting access token. Sending selfie image failed...");
                        }
                    }
                }
            }
        }
    }
}
