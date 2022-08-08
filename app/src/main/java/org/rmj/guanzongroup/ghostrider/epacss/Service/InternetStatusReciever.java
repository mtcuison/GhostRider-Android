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

import static org.rmj.g3appdriver.etc.WebFileServer.RequestAccessToken;
import static org.rmj.g3appdriver.etc.WebFileServer.RequestClientToken;

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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCashCount;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RItinerary;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Data.UploadEmployeeApplication;
import org.rmj.guanzongroup.ghostrider.epacss.Service.Sync.BackgroundSync;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        SendDataTask poSendTask = new SendDataTask(instance);
        poSendTask.execute();

        UploadEmployeeApplication poEmpApp = new UploadEmployeeApplication(instance);
        poEmpApp.UploadApplication();
    }

    private class SendDataTask extends AsyncTask<Void, String, String>{
        private final Application instance;

        private final AppConfigPreference poConfig;
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
        private final RCashCount poCashCount;
        private final WebApi poApi;

        private final BackgroundSync poSync;

        private String lsClient;
        private String lsAccess;

        private String Message;

        private List<ECashCount> cashCounts = new ArrayList<>();
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
        private final List<ECreditApplication> sentAppl = new ArrayList<>();

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
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.poSync = new BackgroundSync(instance);
            this.poCashCount = new RCashCount(instance);
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Message = "Local data and server is updated.";
            if(poConn.isDeviceConnected()) {
                Log.e(TAG, "Internet Status Received.");
                try {
                    loginDetails = poLog.getUnsentSelfieLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try{
                    new RApprovalCode(instance).uploadApprovalCodes();
                } catch (Exception e){
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
                try{
                    cashCounts = poCashCount.getAllUnsentCashCountEntries();
                } catch (Exception e){
                    e.printStackTrace();
                }
                //loanDocs = poImage.getUnsentLoanAppDocFiles();
                try {
                    uploadLoginImages();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    uploadCashCountEntries();
                } catch (Exception e){
                    e.printStackTrace();
                }

                poSync.uploadLoginDetails(poLog, loginDetails);

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
                try {
                    uploadDcpImages();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new RItinerary(instance).UploadUnsentItinerary();
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
                lsClient = RequestClientToken(poConfig.ProducID(), poSession.getClientId(), poSession.getUserID());
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

                        String lsResponse = WebClient.sendRequest(poApi.getUrlPostSelfielog(poConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());

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
                        String lsResponse = WebClient.sendRequest(poApi.getUrlDcpSubmit(poConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());

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

                        String lsResponse = WebClient.sendRequest(poApi.getUrlSubmitOnlineApplication(poConfig.isBackUpServer()), params.toString(), poHeaders.getHeaders());
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
                lsClient = RequestClientToken(poConfig.ProducID(), poSession.getClientId(), poSession.getUserID());
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

        void uploadCashCountEntries() throws Exception{
            for(int x = 0; x < cashCounts.size(); x++){
                JSONObject params = new JSONObject();
                ECashCount loCC = cashCounts.get(x);
                params.put("sTransNox", loCC.getTransNox());
                params.put("sBranchCd", loCC.getBranchCd());
                params.put("nPettyAmt", loCC.getPettyAmt());
                params.put("sORNoxxxx", loCC.getORNoxxxx());
                params.put("sSINoxxxx", loCC.getSINoxxxx());
                params.put("sPRNoxxxx", loCC.getPRNoxxxx());
                params.put("sCRNoxxxx", loCC.getCRNoxxxx());
                params.put("sORNoxNPt", loCC.getORNoxNPt());
                params.put("sPRNoxNPt", loCC.getPRNoxNPt());
                params.put("sDRNoxxxx", loCC.getDRNoxxxx());
                params.put("dTransact", loCC.getTransact());
                params.put("dEntryDte", loCC.getEntryDte());
                params.put("nCn0001cx", loCC.getCn0001cx());
                params.put("nCn0005cx", loCC.getCn0005cx());
                params.put("nCn0010cx", loCC.getCn0010cx());
                params.put("nCn0025cx", loCC.getCn0025cx());
                params.put("nCn0050cx", loCC.getCn0050cx());
                params.put("nCn0001px", loCC.getCn0001px());
                params.put("nCn0005px", loCC.getCn0005px());
                params.put("nCn0010px", loCC.getCn0010px());
                params.put("nNte0020p", loCC.getNte0020p());
                params.put("nNte0050p", loCC.getNte0050p());
                params.put("nNte0100p", loCC.getNte0100p());
                params.put("nNte0200p", loCC.getNte0200p());
                params.put("nNte0500p", loCC.getNte0500p());
                params.put("nNte1000p", loCC.getNte1000p());

                String lsResponse = WebClient.sendRequest(poApi.getUrlSubmitCashcount(poConfig.isBackUpServer()), params.toString(), poHeaders.getHeaders());

                if(lsResponse == null){
                    lsResponse = AppConstants.SERVER_NO_RESPONSE();
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    if(loResponse.getString("result").equalsIgnoreCase("success")){
                        poCashCount.UpdateByTransNox(loCC.getTransNox());
                    }
                }

                Thread.sleep(1000);
            }
        }

        void uploadDcpImages() throws Exception{

            String lsProdtID = poConfig.ProducID();
            String lsClntIDx = poSession.getClientId();
            String lsUserIDx = poSession.getUserID();

            List<DImageInfo.DcpImageForPosting> loImages = poImage.getDCPUnpostedImageListForBackgroundSync();

            for(int x = 0; x < loImages.size(); x++){
                DImageInfo.DcpImageForPosting loDetail = loImages.get(x);
                String lsClient = WebFileServer.RequestClientToken(lsProdtID, lsClntIDx, lsUserIDx);
                String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                        loDetail.sFileLoct,
                        lsAccess,
                        loDetail.sFileCode,
                        loDetail.sAcctNmbr,
                        loDetail.sFileName,
                        poSession.getBranchCode(),
                        loDetail.sSourceCD,
                        loDetail.sTransNox,
                        "");

                String lsResult = (String) loUpload.get("result");
                if (lsResult == null) {
                    Log.d(TAG, "Background DCP Image Sync Error : Server no response.");
                } else {
                    if (lsResult.equalsIgnoreCase("success")) {
                        String lsImageID = (String) loUpload.get("sTransNox");
                        poImage.updateImageInfo(lsImageID, loDetail.sImageIDx);
                        Log.d(TAG, "Posting collection Image with account no. :" + loDetail.sAcctNmbr + "Success!");
                    } else {
                        JSONObject loError = new JSONObject((String) loUpload.get("error"));
                        String lsMessage = loError.getString("message");
                        Log.d(TAG, "Background DCP Image Sync : Posting collection Image with account no. :" + loDetail.sAcctNmbr + "Failed!");
                        Log.d(TAG, "Background DCP Image Sync Error : " + lsMessage);
                    }
                }

                Thread.sleep(1000);
            }
        }

//        void uploadLeaveApplication() throws Exception {
//            JSONObject param = new JSONObject();
//            param.put("sTransNox", poLeave.getNextLeaveCode());
//            param.put("dTransact", AppConstants.CURRENT_DATE);
//            param.put("sEmployID", poSession.getEmployeeID());
//            param.put("dDateFrom", loLeave.getDateFromx());
//            param.put("dDateThru", loLeave.getDateThrux());
//            param.put("nNoDaysxx", loLeave.getNoOfDaysx());
//            param.put("sPurposex", loLeave.getRemarksxx());
//            param.put("cLeaveTyp", loLeave.getLeaveType());
//            param.put("dAppldFrx", loLeave.getDateFromx());
//            param.put("dAppldTox", loLeave.getDateThrux());
//            param.put("sEntryByx", poSession.getEmployeeID());
//            param.put("dEntryDte", AppConstants.CURRENT_DATE);
//            param.put("nWithOPay", "0");
//            param.put("nEqualHrs", loApp.getEqualHrs());
//            param.put("sApproved", "0");
//            param.put("dApproved", "");
//            param.put("dSendDate", "2017-07-19");
//            param.put("cTranStat", "1");
//            param.put("sModified", poSession.getEmployeeID());
//
//            if(poConn.isDeviceConnected()){
//                lsResult = WebClient.sendRequest(WebApi.URL_SEND_LEAVE_APPLICATION, param.toString(), poHeaders.getHeaders());
//                if(lsResult == null){
//                    lsResult = AppConstants.SERVER_NO_RESPONSE();
//                } else {
//                    JSONObject loResult = new JSONObject(lsResult);
//                    String result = loResult.getString("result");
//                    if(result.equalsIgnoreCase("success")){
//                        poLeave.updateSendStatus(
//                                new AppConstants().DATE_MODIFIED,
//                                loApp.getTransNox(),
//                                loResult.getString("sTransNox"));
//                        Log.d("Employee Leave", "Leave info updated!");
//                    }
//                }
//            } else {
//                lsResult = AppConstants.NO_INTERNET();
//            }
//        }
    }
}
