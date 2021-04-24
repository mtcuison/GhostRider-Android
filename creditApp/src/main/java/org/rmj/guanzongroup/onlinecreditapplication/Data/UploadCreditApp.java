/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.Objects;

public class UploadCreditApp {
    private static final String TAG = UploadCreditApp.class.getSimpleName();

    private final Application application;

    public interface OnUploadLoanApplication{
        void OnUpload();
        void OnSuccess(String ClientName);
        void OnFailed(String message);
    }

    public UploadCreditApp(Application application) {
        this.application = application;
    }

    public void UploadLoanApplication(ECreditApplication foUserApp,
                                      EBranchLoanApplication foBranchApp,
                                      EImageInfo foImage,
                                      OnUploadLoanApplication listener){
        new UploadTask(application, foUserApp, foBranchApp, foImage, listener).execute();
    }

    private static class UploadTask extends AsyncTask<Void, Void, String>{
        private final Application instance;
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final RCreditApplication poCreditApp;
        private final RBranchLoanApplication poLoan;
        private final RImageInfo poImgMngr;
        private final OnUploadLoanApplication mListener;
        private final ECreditApplication poInfo;
        private final EBranchLoanApplication poBranchApp;
        private final EImageInfo poImage;
        private final SessionManager poUser;

        public UploadTask(Application application,
                          ECreditApplication foUserApp,
                          EBranchLoanApplication foBranchApp,
                          EImageInfo foImage,
                          OnUploadLoanApplication listener) {
            this.instance = application;
            this.poInfo = foUserApp;
            this.poBranchApp = foBranchApp;
            this.poImage = foImage;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poCreditApp = new RCreditApplication(instance);
            this.poLoan = new RBranchLoanApplication(instance);
            this.poUser = new SessionManager(instance);
            this.poImgMngr = new RImageInfo(instance);
            this.mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnUpload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... strings) {
            String lsResult;
            try {
                poImage.setTransNox(poImgMngr.getImageNextCode());
                poImgMngr.insertImageInfo(poImage);
                poCreditApp.insertCreditApplication(poInfo);
                poLoan.insertNewLoanApplication(poBranchApp);
                if (poConn.isDeviceConnected()) {
                    JSONObject params = new JSONObject(poInfo.getDetlInfo());
                    params.put("dCreatedx", poInfo.getClientNm());

                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_SUBMIT_ONLINE_APPLICATION, params.toString(), poHeaders.getHeaders());
                    if(lsResponse != null) {
                        JSONObject loResponse = new JSONObject(lsResponse);

                        String result = loResponse.getString("result");
                        if(result.equalsIgnoreCase("success")){
                            String lsTransNox = loResponse.getString("sTransNox");
                            poCreditApp.updateSentLoanAppl(poInfo.getTransNox(), lsTransNox);

                            Thread.sleep(1000);

                            String lsClient = WebFileServer.RequestClientToken("IntegSys",
                                                                                            poUser.getClientId(),
                                                                                            poUser.getUserID());
                            String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                            if(!lsAccess.isEmpty()){
                                org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                                        poImage.getFileLoct(),
                                        lsAccess,
                                        poImage.getFileCode(),
                                        poImage.getDtlSrcNo(),
                                        poImage.getImageNme(),
                                        poUser.getBranchCode(),
                                        poImage.getSourceCD(),
                                        poImage.getSourceCD(),
                                        "");

                                lsResponse = (String) loUpload.get("result");
                                Log.e(TAG, "Uploading image result : " + lsResponse);

                                if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                                    String lsTransNo = (String) loUpload.get("sTransNox");
                                    poImgMngr.updateImageInfo(lsTransNo, poImage.getTransNox());
                                    lsResponse = (String) loUpload.toJSONString();
                                } else {
                                    lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR("Loan application has been sent. But failed to upload customer photo.");
                                }
                            }
                        }
                        lsResult = lsResponse;
                    } else {
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    }
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Unable to connect. Loan Application will be sent automatically if internet is available.");
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Something went wrong on local. Please Report to MIS.\n Message: " + e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    mListener.OnSuccess(poInfo.getClientNm());
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    mListener.OnFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mListener.OnFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                mListener.OnFailed(e.getMessage());
            }
        }
    }
}
