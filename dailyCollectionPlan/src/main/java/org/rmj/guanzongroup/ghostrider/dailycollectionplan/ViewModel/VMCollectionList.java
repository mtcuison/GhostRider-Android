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

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Data.ImportCollection;

import java.util.List;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDCPRepo;
    private final RBranch poBranch;
    private final ImportCollection dataImport;
    private final LiveData<List<EDCPCollectionDetail>> collectionList;

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> pnEntryNox = new MutableLiveData<>();

    public VMCollectionList(@NonNull Application application) {
        super(application);
        this.instance = application;
        poDCPRepo = new RDailyCollectionPlan(application);
        poBranch = new RBranch(application);
        dataImport = new ImportCollection(application);
        collectionList = poDCPRepo.getCollectionDetailList();
    }

    public void DownloadDcp(String date){
        dataImport.setDate(date);
        dataImport.ImportData(new ImportDataCallback() {
            @Override
            public void OnSuccessImportData() {
                Log.e(TAG, "DCP Downloaded.");
            }

            @Override
            public void OnFailedImportData(String message) {
                Log.e(TAG, message);
            }
        });
    }

    public LiveData<EDCPCollectionDetail> getCollectionLastEntry(){
        return poDCPRepo.getCollectionLastEntry();
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionList(){
        return collectionList;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDCPRepo.getCollectionMaster();
    }

    public LiveData<EDCPCollectionDetail> getDuplicateAccountrEntry(String TransNox, String AccountNo){
        return poDCPRepo.getDuplicateAccountEntry(TransNox, AccountNo);
    }

    public void setParameter(String fsTransNox, String fnEntryNox){
        try{
            this.psTransNox.setValue(fsTransNox);
            this.pnEntryNox.setValue(fnEntryNox);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importInsuranceInfo(String fsSerialNo, ViewModelCallback callback){
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("sSerialNo", fsSerialNo);
            new ImportData(instance, psTransNox.getValue(), pnEntryNox.getValue(), WebApi.URL_GET_REG_CLIENT, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importARClientInfo(String fsAccntNox, ViewModelCallback callback){
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("sAcctNmbr", fsAccntNox);
            new ImportData(instance, psTransNox.getValue(), pnEntryNox.getValue(), WebApi.URL_GET_AR_CLIENT, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportData extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RDailyCollectionPlan dcpRepo;
        private final ConnectionUtil conn;
        private final ViewModelCallback callback;
        private final String sTransNox, nEntryNox, Url;

        public ImportData(Application instance, String TransNox, String EntryNox, String Url,ViewModelCallback callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.dcpRepo = new RDailyCollectionPlan(instance);
            this.conn = new ConnectionUtil(instance);
            this.callback = callback;
            this.sTransNox = TransNox;
            this.nEntryNox = EntryNox;
            this.Url = Url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(Url, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        saveDetailDataToLocal(jsonResponse.getJSONObject("data"));
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{"Client Account info has been saved."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedResult(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            }
        }

        void saveDetailDataToLocal(JSONObject foData) throws JSONException {
            Log.e(TAG, foData.toString());
            EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
            collectionDetail.setTransNox(sTransNox);
            collectionDetail.setEntryNox(nEntryNox);
            collectionDetail.setAcctNmbr(foData.getString("sAcctNmbr"));
            collectionDetail.setFullName(foData.getString("xFullName"));
            collectionDetail.setIsDCPxxx("0");
            collectionDetail.setMobileNo(foData.getString("sMobileNo"));
            collectionDetail.setHouseNox(foData.getString("sHouseNox"));
            collectionDetail.setAddressx(foData.getString("sAddressx"));
            collectionDetail.setBrgyName(foData.getString("sBrgyName"));
            collectionDetail.setTownName(foData.getString("sTownName"));
            collectionDetail.setClientID(foData.getString("sClientID"));
            collectionDetail.setSerialID(foData.getString("sSerialID"));
            collectionDetail.setSerialNo(foData.getString("sSerialNo"));
            collectionDetail.setTranStat("0");
            dcpRepo.insertCollectionDetail(collectionDetail);
            dcpRepo.updateEntryMaster(nEntryNox);
        }
    }
}
