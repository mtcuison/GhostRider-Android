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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
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
    private final MutableLiveData<List<EImageInfo>> plImageLst = new MutableLiveData<>();
    private final MutableLiveData<List<EAddressUpdate>> paAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> paMobile = new MutableLiveData<>();

    private final MutableLiveData<List<DDCPCollectionDetail.CollectionDetail>> plDetail = new MutableLiveData<>();

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

    public LiveData<List<EImageInfo>> getUnsentImageInfoList(){
        return poImage.getUnsentImageList();
    }

    public void setCollectionListForPosting(List<DDCPCollectionDetail.CollectionDetail> collectionDetails){
        this.plDetail.setValue(collectionDetails);
    }

    public LiveData<List<DDCPCollectionDetail.CollectionDetail>> getCollectionDetailForPosting(){
        return poDcp.getCollectionDetailForPosting();
    }

    public void setImageInfoList(List<EImageInfo> imageInfoList){
        this.plImageLst.setValue(imageInfoList);
    }

    public void PostLRCollectionDetail(PostTransactionCallback callback){
        new PostLRCollectionDetail(instance, callback).execute(plDetail.getValue());
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

    public static class PostLRCollectionDetail extends AsyncTask<List<DDCPCollectionDetail.CollectionDetail>, Void, String>{
        private final ConnectionUtil poConn;
        private final PostTransactionCallback callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;

        public PostLRCollectionDetail(Application instance, PostTransactionCallback callback){
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poTelephony = new Telephony(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @SafeVarargs
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected final String doInBackground(List<DDCPCollectionDetail.CollectionDetail>... lists) {
            String lsResult;
            List<DDCPCollectionDetail.CollectionDetail> laCollDetl = lists[0];
            try {
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {

                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if(lsClient.isEmpty() || lsAccess.isEmpty()){
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {
                        boolean[] isDataSent = new boolean[laCollDetl.size()];
                        for(int x = 0; x < laCollDetl.size(); x++) {
                            try {
                                DDCPCollectionDetail.CollectionDetail loDetail = laCollDetl.get(x);
                                org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(loDetail.sFileLoct,
                                        lsAccess,
                                        loDetail.sFileCode,
                                        loDetail.sAcctNmbr,
                                        loDetail.sImageNme,
                                        poUser.getUserID(),
                                        loDetail.sSourceCd,
                                        loDetail.sTransNox,
                                        "");

                                String lsResponse = (String) loUpload.get("result");
                                Log.e(TAG, "Uploading image result : " + lsResponse);

                                if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                                    Log.e(TAG, "Image file of Account No. " + loDetail.sAcctNmbr + ", Entry No. "+ loDetail.nEntryNox+ "was uploaded successfully");

                                    String lsTransNo = (String) loUpload.get("sTransNox");
                                    poImage.updateImageInfo(lsTransNo, loDetail.sImageIDx);
                                    poDcp.updateCollectionDetailImage(loDetail.sAcctNmbr);

                                    Thread.sleep(1000);

                                    JSONObject loData = new JSONObject();
                                    if (loDetail.sRemCodex.equalsIgnoreCase("PAY")) {
                                        loData.put("sPRNoxxxx", loDetail.sPRNoxxxx);
                                        loData.put("nTranAmtx", loDetail.nTranAmtx);
                                        loData.put("nDiscount", loDetail.nDiscount);
                                        loData.put("nOthersxx", loDetail.nOthersxx);
                                        loData.put("cTranType", loDetail.cTranType);
                                        loData.put("nTranTotl", loDetail.nTranTotl);
                                    } else if (loDetail.sRemCodex.equalsIgnoreCase("PTP")) {

                                        //Required parameters for Promise to pay..
                                        loData.put("cApntUnit", loDetail.cApntUnit);
                                        loData.put("sBranchCd", loDetail.sBranchCd);
                                        loData.put("dPromised", loDetail.dPromised);
                                        loData.put("sImageNme", loDetail.sImageNme);
                                        loData.put("nLongitud", loDetail.nLongitud);
                                        loData.put("nLatitude", loDetail.nLatitude);

                                    } else if (loDetail.sRemCodex.equalsIgnoreCase("LUn") ||
                                            loDetail.sRemCodex.equalsIgnoreCase("TA") ||
                                            loDetail.sRemCodex.equalsIgnoreCase("FO")) {

                                        //TODO: replace JSON parameters get the parameters which is being generated by RClientUpdate...
                                    loData.put("sLastName", loDetail.sLastName);
                                    loData.put("sFrstName", loDetail.sFrstName);
                                    loData.put("sMiddName", loDetail.sMiddName);
                                    loData.put("sSuffixNm", loDetail.sSuffixNm);
                                    loData.put("sHouseNox", loDetail.sHouseNox);
                                    loData.put("sAddressx", loDetail.sAddressx);
                                    loData.put("sTownIDxx", loDetail.sTownIDxx);
                                    loData.put("cGenderxx", loDetail.cGenderxx);
                                    loData.put("cCivlStat", loDetail.cCivlStat);
                                    loData.put("dBirthDte", loDetail.dBirthDte);
                                    loData.put("dBirthPlc", loDetail.dBirthPlc);
                                    loData.put("sLandline", loDetail.sLandline);
                                    loData.put("sMobileNo", loDetail.sMobileNo);
                                    loData.put("sEmailAdd", loDetail.sEmailAdd);
                                    } else {
                                        loData.put("sImageNme", loDetail.sImageNme);
                                        loData.put("nLongitud", loDetail.nLongitud);
                                        loData.put("nLatitude", loDetail.nLatitude);
                                    }
                                    JSONObject loJson = new JSONObject();
                                    loJson.put("sTransNox", loDetail.sTransNox);
                                    loJson.put("nEntryNox", loDetail.nEntryNox);
                                    loJson.put("sAcctNmbr", loDetail.sAcctNmbr);
                                    loJson.put("sRemCodex", loDetail.sRemCodex);

                                    //TODO: If RemarksCode == LU || TA || FO repalce loData with the JSON object generated by RClientUpdate...
                                    loJson.put("sJsonData", loData);
                                    loJson.put("dReceived", "");
                                    loJson.put("sUserIDxx", poUser.getUserID());
                                    loJson.put("sDeviceID", poTelephony.getDeviceID());

                                    Log.e(TAG, loJson.toString());
                                    String lsResponse1 = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());
                                    if (lsResponse1 == null) {
                                        Log.e(TAG, "Server no response.");
                                    } else {
                                        JSONObject loResponse = new JSONObject(lsResponse1);

                                        String result = loResponse.getString("result");
                                        if (result.equalsIgnoreCase("success")) {
                                            Log.e(TAG, "Data of Account No. " + loDetail.sAcctNmbr + ", Entry No. "+ loDetail.nEntryNox+ " was uploaded successfully");
                                            poDcp.updateCollectionDetailStatus(loDetail.sTransNox, loDetail.nEntryNox);
                                            isDataSent[x] = true;
                                        } else {
                                            JSONObject loError = loResponse.getJSONObject("error");
                                            Log.e(TAG, loError.getString("message"));
                                            isDataSent[x] = false;
                                        }
                                    }
                                } else {
                                    isDataSent[x] = false;
                                    Log.e(TAG, "Image file of Account No. " + loDetail.sAcctNmbr + ", Entry No. "+ loDetail.nEntryNox+ " was not uploaded to server.");
                                    JSONObject loError = new JSONObject(lsResponse);
                                    Log.e(TAG, "Reason : " + loError.getString("message"));
                                }

                            } catch (Exception e){
                                e.printStackTrace();
                                isDataSent[x] = false;
                            }

                            Thread.sleep(1000);
                        }

                        boolean allDataSent = true;
                        for (boolean b : isDataSent) {
                            if (!b) {
                                allDataSent = false;
                                break;
                            }
                        }

                        if(allDataSent){
                            lsResult = AppConstants.ALL_DATA_SENT();
                        } else {
                            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("An error occurred while posting collection details. Please try again...");
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.OnPostSuccess(new String[]{"Collection for today has been posted successfully."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnPostFailed(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }
    }
}
