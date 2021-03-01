package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.MobileUpdate;

import java.util.List;
import java.util.Objects;

public class VMCollectionLog extends AndroidViewModel {
    private static final String TAG = VMCollectionLog.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final RBranch poBranch;
    private final RImageInfo poImage;
    private final RCollectionUpdate poUpdate;


    private final MutableLiveData<EDCPCollectionMaster> poMaster = new MutableLiveData<>();
    private final MutableLiveData<List<EDCPCollectionDetail>> plTranList = new MutableLiveData<>();
    private final MutableLiveData<List<EImageInfo>> plImageLst = new MutableLiveData<>();
    private final MutableLiveData<List<EAddressUpdate>> paAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> paMobile = new MutableLiveData<>();

    public interface PostTransactionCallback{
        void OnLoad();
        void OnPostSuccess(String[] args);
        void OnPostFailed(String message);
    }

    public VMCollectionLog(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poImage = new RImageInfo(application);
        this.poUpdate = new RCollectionUpdate(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDcp.getCollectionMaster();
    }

    public void setCollectionMaster(EDCPCollectionMaster collectionMaster){
        try {
            this.poMaster.setValue(collectionMaster);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionList(){
        return poDcp.getCollectionDetailLog();
    }

    public LiveData<List<EImageInfo>> getUnsentImageInfoList(){
        return poImage.getUnsentImageList();
    }

    public void setTransactionList(List<EDCPCollectionDetail> transactionList){
        this.plTranList.setValue(transactionList);
    }

    public void setImageInfoList(List<EImageInfo> imageInfoList){
        this.plImageLst.setValue(imageInfoList);
    }

    public void postTransactions(PostTransactionCallback callback){
        List<EDCPCollectionDetail> loDetails = plTranList.getValue();
        new PostTask(instance, callback).execute(loDetails);
    }

    public void postTransactionImages(PostTransactionCallback callback){
        List<EImageInfo> loImages = plImageLst.getValue();
        new PostImagesTask(instance, plTranList.getValue(), paAddress.getValue(), paMobile.getValue(), callback).execute(loImages);
    }

    public LiveData<List<EAddressUpdate>> getAllAddress(){
        return poUpdate.getAddressList();
    }

    public LiveData<List<EMobileUpdate>> getAllMobileNox(){
        return poUpdate.getMobileList();
    }

    public void setAddressList(List<EAddressUpdate> paAddress) {
        this.paAddress.setValue(paAddress);
    }

    public void setMobileList(List<EMobileUpdate> paMobile) {
        this.paMobile.setValue(paMobile);
    }

    private static class PostTask extends AsyncTask<List<EDCPCollectionDetail>, Void, String>{
        private final PostTransactionCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RDailyCollectionPlan poDcp;
        private final SessionManager poUser;
        private final Telephony poTelephony;

        public PostTask(Application instance, PostTransactionCallback callback) {
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poUser = new SessionManager(instance);
            this.poTelephony = new Telephony(instance);
        }

        @Override
        protected void onPreExecute() {
            callback.OnLoad();
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(List<EDCPCollectionDetail>... lists) {
            try {
                if (poConn.isDeviceConnected()) {
                    for(int x = 0; x < lists[0].size(); x++){
                        EDCPCollectionDetail loDetail = lists[0].get(x);
                        JSONObject loData = new JSONObject();
                        if(loDetail.getRemCodex().equalsIgnoreCase("PAY")){
                            loData.put("sPRNoxxxx", loDetail.getPRNoxxxx());
                            loData.put("nTranAmtx", loDetail.getTranAmtx());
                            loData.put("nDiscount", loDetail.getDiscount());
                            loData.put("nOthersxx", loDetail.getOthersxx());
                            loData.put("cTranType", loDetail.getTranType());
                            loData.put("nTranTotl", loDetail.getTranTotl());
                        } else if(loDetail.getRemCodex().equalsIgnoreCase("PTP")) {
                            loData.put("cApntUnit", loDetail.getApntUnit());
                            loData.put("sBranchCd", loDetail.getBranchCd());
                            loData.put("dPromised", loDetail.getPromised());
                            loData.put("sImageNme", loDetail.getImageNme());
                        } else if(loDetail.getRemCodex().equalsIgnoreCase("LU") ||
                                loDetail.getRemCodex().equalsIgnoreCase("TA") ||
                                loDetail.getRemCodex().equalsIgnoreCase("FO")) {
                            loData.put("sLastName", loDetail.getEntryNox());
                            loData.put("sFrstName", loDetail.getAcctNmbr());
                            loData.put("sMiddName", loDetail.getFullName());
                            loData.put("sSuffixNm", loDetail.getPRNoxxxx());
                            loData.put("sHouseNox", loDetail.getTranAmtx());
                            loData.put("sAddressx", loDetail.getDiscount());
                            loData.put("sTownIDxx", loDetail.getOthersxx());
                            loData.put("cGenderxx", loDetail.getRemarksx());
                            loData.put("cCivlStat", loDetail.getPromised());
                            loData.put("dBirthDte", loDetail.getRemCodex());
                            loData.put("dBirthPlc", loDetail.getTranType());
                            loData.put("sLandline", loDetail.getTranTotl());
                            loData.put("sMobileNo", loDetail.getReferNox());
                            loData.put("sEmailAdd", loDetail.getPaymForm());
                        } else {
                             loData.put("sImageNme", loDetail.getImageNme());
                        }
                        JSONObject loJson = new JSONObject();
                        loJson.put("sTransNox", loDetail.getTransNox());
                        loJson.put("nEntryNox", loDetail.getEntryNox());
                        loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                        loJson.put("sRemCodex", loDetail.getRemCodex());
                        loJson.put("sJsonData", loData);
                        loJson.put("dReceived", "");
                        loJson.put("sUserIDxx", poUser.getUserID());
                        loJson.put("sDeviceID", poTelephony.getDeviceID());

                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());
                        if(lsResponse == null){
                            Log.e(TAG, "Server no response.");
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            String lsResult = loResponse.getString("result");
                            if (lsResult.equalsIgnoreCase("success")){
                                Log.e(TAG, x + " " + lsResult);
                                loDetail.setSendStat("1");
                                loDetail.setModified(AppConstants.DATE_MODIFIED);
                                poDcp.updateCollectionDetailInfo(loDetail);
                            } else {
                                Log.e(TAG, loResponse.getString(loResponse.toString()));
                            }
                        }
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            callback.OnPostSuccess(new String[]{s});
            super.onPostExecute(s);
        }
    }

    public static class PostImagesTask extends AsyncTask<List<EImageInfo>, Void, String>{
        private final Application instance;
        private final ConnectionUtil poConn;
        private final PostTransactionCallback callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final List<EDCPCollectionDetail> paDetail;
        private final List<EAddressUpdate> paAddress;
        private final List<EMobileUpdate> paMobile;
        private final HttpHeaders poHeaders;

        private RCollectionUpdate rCollect;

        public PostImagesTask(Application instance, List<EDCPCollectionDetail> faDetail, List<EAddressUpdate> eAddUpdate, List<EMobileUpdate> eMobUpdate, PostTransactionCallback callback) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poTelephony = new Telephony(instance);
            this.paDetail = faDetail;
            this.paAddress = eAddUpdate;
            this.paMobile = eMobUpdate;
            this.rCollect = new RCollectionUpdate(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(List<EImageInfo>... lists) {
            String lsResult = "";
            try {
                if(poConn.isDeviceConnected()) {
                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);
                    List<EImageInfo> images = lists[0];
                    for (int x = 0; x < images.size(); x++) {
                        EImageInfo imageInfo = images.get(x);

                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(imageInfo.getFileLoct(),
                                lsAccess,
                                imageInfo.getFileCode(),
                                imageInfo.getDtlSrcNo(),
                                imageInfo.getImageNme(),
                                poUser.getUserID(),
                                imageInfo.getSourceCD(),
                                imageInfo.getSourceNo(),
                                "");
                        lsResult = loUpload.toJSONString();
                        Log.e(TAG, "Uploading image result : " + lsResult);
                        String lsResponse = (String) loUpload.get("result");

                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            imageInfo.setSendStat('1');
                            imageInfo.setSendDate(AppConstants.DATE_MODIFIED);
                            poImage.updateImageInfo(lsTransNo, imageInfo.getTransNox());
                            poDcp.updateCollectionDetailImage(imageInfo.getTransNox(), imageInfo.getDtlSrcNo());
                        }
                    }

                    for (int x = 0; x < paDetail.size(); x++) {
                        EDCPCollectionDetail loDetail = paDetail.get(x);
                        JSONObject loData = new JSONObject();
                        if (loDetail.getRemCodex().equalsIgnoreCase("PAY")) {
                            loData.put("sPRNoxxxx", loDetail.getPRNoxxxx());
                            loData.put("nTranAmtx", loDetail.getTranAmtx());
                            loData.put("nDiscount", loDetail.getDiscount());
                            loData.put("nOthersxx", loDetail.getOthersxx());
                            loData.put("cTranType", loDetail.getTranType());
                            loData.put("nTranTotl", loDetail.getTranTotl());
                        } else if (loDetail.getRemCodex().equalsIgnoreCase("PTP")) {
                            loData.put("cApntUnit", loDetail.getApntUnit());
                            loData.put("sBranchCd", loDetail.getBranchCd());
                            loData.put("dPromised", loDetail.getPromised());
                            loData.put("sImageNme", loDetail.getImageNme());
                        } else if (loDetail.getRemCodex().equalsIgnoreCase("LU") ||
                                loDetail.getRemCodex().equalsIgnoreCase("TA") ||
                                loDetail.getRemCodex().equalsIgnoreCase("FO")) {
                            loData.put("sLastName", loDetail.getEntryNox());
                            loData.put("sFrstName", loDetail.getAcctNmbr());
                            loData.put("sMiddName", loDetail.getFullName());
                            loData.put("sSuffixNm", loDetail.getPRNoxxxx());
                            loData.put("sHouseNox", loDetail.getTranAmtx());
                            loData.put("sAddressx", loDetail.getDiscount());
                            loData.put("sTownIDxx", loDetail.getOthersxx());
                            loData.put("cGenderxx", loDetail.getRemarksx());
                            loData.put("cCivlStat", loDetail.getPromised());
                            loData.put("dBirthDte", loDetail.getRemCodex());
                            loData.put("dBirthPlc", loDetail.getTranType());
                            loData.put("sLandline", loDetail.getTranTotl());
                            loData.put("sMobileNo", loDetail.getReferNox());
                            loData.put("sEmailAdd", loDetail.getPaymForm());
                        } else {
                            loData.put("sImageNme", loDetail.getImageNme());
                        }
                        JSONObject loJson = new JSONObject();
                        loJson.put("sTransNox", loDetail.getTransNox());
                        loJson.put("nEntryNox", loDetail.getEntryNox());
                        loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                        loJson.put("sRemCodex", loDetail.getRemCodex());
                        loJson.put("sJsonData", loData);
                        loJson.put("dReceived", "");
                        loJson.put("sUserIDxx", poUser.getUserID());
                        loJson.put("sDeviceID", poTelephony.getDeviceID());

                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());
                        if (lsResponse == null) {
                            Log.e(TAG, "Server no response.");
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            lsResult = loResponse.toString();
                            String result = loResponse.getString("result");
                            if (result.equalsIgnoreCase("success")) {
                                Log.e(TAG, x + " " + result);
                                loDetail.setSendStat("1");
                                loDetail.setModified(AppConstants.DATE_MODIFIED);
                                poDcp.updateCollectionDetailInfo(loDetail);
                            } else {
                                Log.e(TAG, loResponse.getString(loResponse.toString()));
                            }
                        }

                        Thread.sleep(1000);

                        if(loDetail.getRemCodex().equalsIgnoreCase("CNA")) {
                            for (int i = 0; i < paAddress.size(); i++) {
                                EAddressUpdate info = paAddress.get(i);
                                JSONObject param = new JSONObject();
                                param.put("sTransNox", info.getTransNox());
                                param.put("sClientID", info.getClientID());
                                param.put("cReqstCDe", info.getReqstCDe());
                                param.put("cAddrssTp", info.getAddrssTp());
                                param.put("sHouseNox", info.getHouseNox());
                                param.put("sAddressx", info.getAddressx());
                                param.put("sTownIDxx", info.getTownIDxx());
                                param.put("sBrgyIDxx", info.getBrgyIDxx());
                                param.put("cPrimaryx", info.getPrimaryx());
                                param.put("nLatitude", info.getLatitude());
                                param.put("nLongitud", info.getLongitud());
                                param.put("sRemarksx", info.getRemarksx());
                                param.put("sSourceCD","DCPa");
                                param.put("sSourceNo", loDetail.getAcctNmbr());

                                String lsUpdateResponse = WebClient.httpsPostJSon(WebApi.URL_UPDATE_ADDRESS, param.toString(), poHeaders.getHeaders());

                                if(lsUpdateResponse == null) {
                                    Log.e("Result:", "Server no Repsonse");
                                } else {
                                    JSONObject loResult = new JSONObject(lsUpdateResponse);
                                    String result = loResult.getString("result");
                                    if(result.equalsIgnoreCase("success")) {
                                        String newTransNox = loResult.getString("sTransNox");
                                        rCollect.updateAddressStatus(newTransNox, info.getTransNox());
                                        Log.e("Result:",result);
                                    } else {
                                        Log.e("Result:","Failed");
                                    }
                                }
                            }
                        }
                    }


                } else {
                    lsResult = AppConstants.NO_INTERNET();
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
                 JSONObject loJson = new JSONObject();
                 if(loJson.getString("result").equalsIgnoreCase("success")){
                     callback.OnPostSuccess(new String[]{"Image uploaded successfully"});
                 } else {
                     callback.OnPostFailed("");
                 }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
