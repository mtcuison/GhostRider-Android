package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;

public class ImportCollection implements ImportInstance {
    private static final String TAG = ImportCollection.class.getSimpleName();
    private final RDailyCollectionPlan dcpRepo;
    private final ConnectionUtil conn;
    private final WebApi webApi;
    private final HttpHeaders headers;

    public ImportCollection(Application application){
        dcpRepo = new RDailyCollectionPlan(application);
        conn = new ConnectionUtil(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("sEmployID", "M00110006088");
            loJson.put("dTransact", "2021-02-04");
            loJson.put("cDCPTypex", "1");
            new ImportData(headers, dcpRepo, conn, webApi, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportData extends AsyncTask<JSONObject, Void, String>{
        private final HttpHeaders headers;
        private final RDailyCollectionPlan dcpRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final ImportDataCallback callback;

        public ImportData(HttpHeaders headers, RDailyCollectionPlan dcpRepo, ConnectionUtil conn, WebApi webaApi, ImportDataCallback callback) {
            this.headers = headers;
            this.dcpRepo = dcpRepo;
            this.conn = conn;
            this.webApi = webaApi;
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(WebApi.URL_DOWNLOAD_DCP, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        saveMasterDataToLocal(jsonResponse);
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
                    callback.OnSuccessImportData();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedImportData(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            }
        }


        void saveMasterDataToLocal(JSONObject foJson) throws JSONException{
            EDCPCollectionMaster collectionMaster = new EDCPCollectionMaster();
            JSONObject loJson = foJson.getJSONObject("master");
            collectionMaster.setTransNox(loJson.getString("sTransNox"));
            collectionMaster.setTransact(loJson.getString("dTransact"));
            collectionMaster.setReferNox(loJson.getString("sReferNox"));
            collectionMaster.setCollName(loJson.getString("xCollName"));
            collectionMaster.setRouteNme(loJson.getString("sRouteNme"));
            collectionMaster.setReferDte(loJson.getString("dReferDte"));
            collectionMaster.setTranStat(loJson.getString("cTranStat").charAt(0));
            collectionMaster.setDCPTypex(loJson.getString("cDCPTypex").charAt(0));
            collectionMaster.setEntryNox(loJson.getString("nEntryNox"));
            collectionMaster.setBranchNm(loJson.getString("sBranchNm"));
            collectionMaster.setCollctID(loJson.getString("sCollctID"));
            JSONArray laJson = foJson.getJSONArray("detail");
            dcpRepo.insertMasterData(collectionMaster);
            saveDetailDataToLocal(laJson, loJson);
        }

        void saveDetailDataToLocal(JSONArray faJson, JSONObject jsonMaster) throws JSONException {
            List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();
            for(int x = 0; x < faJson.length(); x++){
                JSONObject loJson = faJson.getJSONObject(x);
                EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
                collectionDetail.setTransNox(jsonMaster.getString("sTransNox"));
                collectionDetail.setEntryNox(loJson.getString("nEntryNox"));
                collectionDetail.setAcctNmbr(loJson.getString("sAcctNmbr"));
                collectionDetail.setFullName(loJson.getString("xFullName"));
                collectionDetail.setIsDCPxxx(loJson.getString("cIsDCPxxx"));
                collectionDetail.setHouseNox(loJson.getString("sHouseNox"));
                collectionDetail.setAddressx(loJson.getString("sAddressx"));
                collectionDetail.setBrgyName(loJson.getString("sBrgyName"));
                collectionDetail.setTownName(loJson.getString("sTownName"));
                collectionDetail.setAmtDuexx(loJson.getString("nAmtDuexx"));
                collectionDetail.setApntUnit(loJson.getString("cApntUnit"));
                collectionDetail.setDueDatex(loJson.getString("dDueDatex"));
                collectionDetail.setLongitud(loJson.getString("nLongitud"));
                collectionDetail.setLatitude(loJson.getString("nLatitude"));
                collectionDetail.setClientID(loJson.getString("sClientID"));
                collectionDetail.setSerialID(loJson.getString("sSerialID"));
                collectionDetail.setSerialNo(loJson.getString("sSerialNo"));
                collectionDetails.add(collectionDetail);
            }
            dcpRepo.insertDetailBulkData(collectionDetails);
        }
    }
}
