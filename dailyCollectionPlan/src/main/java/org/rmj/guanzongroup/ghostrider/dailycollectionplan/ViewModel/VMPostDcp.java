/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 10/22/21, 3:14 PM
 * project file last modified : 10/22/21, 3:14 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMPostDcp extends AndroidViewModel {
    private static final String TAG = VMPostDcp.class.getSimpleName();

    private final Application instance;

    public interface OnPostCollectionCallback{
        void OnStartPosting(String Title, String Message);
        void OnProgress(String label, int value);
        void OnFinishPosting(boolean isSuccess, String Message);
    }

    public VMPostDcp(@NonNull Application application) {
        super(application);
        this.instance = application;
    }

    public void PostDCP(OnPostCollectionCallback callback){
        new PostLRDCP(instance).execute();
    }

    private class PostLRDCP extends AsyncTask<List<EDCPCollectionDetail>, Integer, JSONObject>{

        private final Application instance;
        private final ConnectionUtil poConn;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final RClientUpdate poClient;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;
        private final RDailyCollectionPlan poDcp;
        private final AppConfigPreference poConfig;
        private final WebApi poApi;
        private String sRemarksx;
        private String psResult;
        private String psAccess;

        private ArrayList<UnpostedData> poUnposted;
        private ArrayList<UnpostedImage> poUnpstImg;

        public PostLRDCP(Application instance) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poClient = new RClientUpdate(instance);
            this.poTelephony = new Telephony(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.poUnposted = new ArrayList<>();
            this.poUnpstImg = new ArrayList<>();
            this.poApi = new WebApi(poConfig.getTestStatus());
        }

        public void setRemarks(String value){
            this.sRemarksx = value;
        }

        @SuppressLint("NewApi")
        @Override
        protected JSONObject doInBackground(List<EDCPCollectionDetail>... lists) {
            try{
                psAccess = getAccessToken();
                List<EDCPCollectionDetail> laDetail = lists[0];
                for(int x = 0; x < laDetail.size(); x++){
                    EDCPCollectionDetail loDetail = laDetail.get(x);
                    JSONObject loData = new JSONObject();
                    if(loDetail.getRemCodex().isEmpty()){
                        loData.put("sRemarksx", sRemarksx);
                    } else {
                        String lsRemCode = loDetail.getRemCodex();
                        if(!lsRemCode.equalsIgnoreCase("PAY")){
                            EImageInfo loImgInfo = uploadDCPImageFile(loDetail);
                            if (lsRemCode.equalsIgnoreCase("PTP")) {
                                //Required loDataeters for Promise to pay..
                                loData.put("cApntUnit", loDetail.getApntUnit());
                                loData.put("sBranchCd", loDetail.getBranchCd());
                                loData.put("dPromised", loDetail.getPromised());

                                loData.put("sImageNme", loDetail.getImageNme());
                                loData.put("sSourceCD", loImgInfo.getSourceCD());
                                loData.put("nLongitud", loImgInfo.getLongitud());
                                loData.put("nLatitude", loImgInfo.getLatitude());

                            } else if (lsRemCode.equalsIgnoreCase("LUn") ||
                                    lsRemCode.equalsIgnoreCase("TA") ||
                                    lsRemCode.equalsIgnoreCase("FO")) {

                                //TODO: replace JSON loDataeters get the loDataeters which is being generated by RClientUpdate...
                                EClientUpdate loClient = poClient.getClientUpdateInfoForPosting(loDetail.getTransNox(), loDetail.getAcctNmbr());
                                loData.put("sLastName", loClient.getLastName());
                                loData.put("sFrstName", loClient.getFrstName());
                                loData.put("sMiddName", loClient.getMiddName());
                                loData.put("sSuffixNm", loClient.getSuffixNm());
                                loData.put("sHouseNox", loClient.getHouseNox());
                                loData.put("sAddressx", loClient.getAddressx());
                                loData.put("sTownIDxx", loClient.getTownIDxx());
                                loData.put("cGenderxx", loClient.getGenderxx());
                                loData.put("cCivlStat", loClient.getCivlStat());
                                loData.put("dBirthDte", loClient.getBirthDte());
                                loData.put("dBirthPlc", loClient.getBirthPlc());
                                loData.put("sLandline", loClient.getLandline());
                                loData.put("sMobileNo", loClient.getMobileNo());
                                loData.put("sEmailAdd", loClient.getEmailAdd());

                                loData.put("sImageNme", loImgInfo.getImageNme());
                                loData.put("sSourceCD", loImgInfo.getSourceCD());
                                loData.put("nLongitud", loImgInfo.getLongitud());
                                loData.put("nLatitude", loImgInfo.getLatitude());
                            } else {
                                loData.put("sImageNme", loImgInfo.getImageNme());
                                loData.put("sSourceCD", loImgInfo.getSourceCD());
                                loData.put("nLongitud", loImgInfo.getLongitud());
                                loData.put("nLatitude", loImgInfo.getLatitude());
                            }

                            loData.put("sRemarksx", loDetail.getRemarksx());

                        } else {
                            loData.put("sPRNoxxxx", loDetail.getPRNoxxxx());
                            loData.put("nTranAmtx", loDetail.getTranAmtx());
                            loData.put("nDiscount", loDetail.getDiscount());
                            loData.put("nOthersxx", loDetail.getOthersxx());
                            loData.put("cTranType", loDetail.getTranType());
                            loData.put("nTranTotl", loDetail.getTranTotl());
//                                               Added by Jonathan 07/27/2021
                            loData.put("sRemarksx", loDetail.getRemarksx());
                        }
                    }

                    JSONObject params = new JSONObject();
                    params.put("sTransNox", loDetail.getTransNox());
                    params.put("nEntryNox", loDetail.getEntryNox());
                    params.put("sAcctNmbr", loDetail.getAcctNmbr());
                    if (loDetail.getRemCodex().isEmpty()) {
                        params.put("sRemCodex", "NV");
                        params.put("dModified", new AppConstants().DATE_MODIFIED);
                    } else {
                        params.put("sRemCodex", loDetail.getRemCodex());
                        params.put("dModified", loDetail.getModified());
                    }

                    params.put("sJsonData", loData);
                    params.put("dReceived", "");
                    params.put("sUserIDxx", poUser.getUserID());
                    params.put("sDeviceID", poTelephony.getDeviceID());

                    String lsDtlResult = WebClient.sendRequest(poApi.getUrlDcpSubmit(), params.toString(), poHeaders.getHeaders());
                    if(lsDtlResult == null){

                    } else {
                        JSONObject loResult = new JSONObject(lsDtlResult);
                        String lsResult = loResult.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            UpdateLocalDCPStatus(loDetail);
                        } else {

                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }

        String getAccessToken(){
            String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(), poUser.getClientId(), poUser.getUserID());
            String lsAccess = WebFileServer.RequestAccessToken(lsClient);
            return lsAccess;
        }

        EImageInfo uploadDCPImageFile(EDCPCollectionDetail foDetail){
            String lsTransNox = foDetail.getTransNox();
            String lsAccntNox = foDetail.getAcctNmbr();
            EImageInfo loImgInfo = poImage.getDCPImageInfoForPosting(lsTransNox, lsAccntNox);;
            try {
                org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(loImgInfo.getFileLoct(),
                        psAccess,
                        loImgInfo.getFileCode(),
                        foDetail.getAcctNmbr(),
                        foDetail.getImageNme(),
                        poUser.getBranchCode(),
                        loImgInfo.getSourceCD(),
                        foDetail.getTransNox(),
                        "");

                String lsResponse = (String) loUpload.get("result");

                if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                    String lsTransNo = (String) loUpload.get("sTransNox");
                    poImage.updateImageInfo(lsTransNo, loImgInfo.getTransNox());
                } else {
                    JSONObject loError = new JSONObject(loUpload.toJSONString());
                    poUnpstImg.add(new UnpostedImage(foDetail.getTransNox(), loError.getString("message")));
                }

                Thread.sleep(1000);

            } catch (Exception e){
                e.printStackTrace();
            }
            return loImgInfo;
        }

        void UpdateLocalDCPStatus(EDCPCollectionDetail loDetail){
            if(loDetail.getRemCodex().isEmpty()){
                poDcp.updateCollectionDetailStatusWithRemarks(loDetail.getTransNox(), loDetail.getEntryNox(), sRemarksx);
            } else {
                poDcp.updateCollectionDetailStatus(loDetail.getTransNox(), loDetail.getEntryNox());
            }
        }
    }

    class UnpostedData{
        String AccName;
        String RemCode;
        JSONObject data;
        String Message;
    }

    class UnpostedImage{
        String TransNox;
        String Message;

        public UnpostedImage(String transNox, String message) {
            TransNox = transNox;
            Message = message;
        }
    }
}
