/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dataChecker
 * Electronic Personnel Access Control Security System
 * project file created : 10/16/21, 1:48 PM
 * project file last modified : 10/16/21, 1:34 PM
 */

package org.rmj.guanzongroup.ghostrider.dataChecker.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.DCPData;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.FileUtil;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.UserInfo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VMDBExplorer extends AndroidViewModel {
    private static final String TAG = VMDBExplorer.class.getSimpleName();

    private final Application instance;

    public static final int PICK_DB_FILE = 101;

    public interface FindDatabaseCallback{
        void OnFind(Intent findDB);
    }

    public interface ExploreDatabaseCallback{
        void OnDataOwnerRetrieve(String DataOwner);
        void OnDCPListRetrieve(ArrayList<DCPData> dcpData);
        void OnOwnerInfoRetrieve(UserInfo info);
        void OnFailedRetrieveInfo(String message);
    }

    public interface OnPostCollectionListener{
        void OnPost(String message);
        void OnPostSuccess(String message);
        void OnPostFailed(String message);
    }

    public VMDBExplorer(@NonNull Application application) {
        super(application);
        this.instance = application;
    }

    public void ExploreDb(Uri data, ExploreDatabaseCallback callback){
        new ParseDBData(data, callback).execute();
    }

    private class ParseDBData extends AsyncTask<Void, Void, String>{

        private final Uri poData;
        private ExploreDatabaseCallback callback;

        private String psOwner;
        private ArrayList<DCPData> poDCPData;
        private UserInfo poUserInfo;

        public ParseDBData(Uri data, ExploreDatabaseCallback callback) {
            this.poData = data;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result;
            try {
                //stablishing the connection
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(FileUtil.from(instance, poData), null);

                //working with query and results.
                psOwner = getDataOwner(db);

                poDCPData = getDCPData(db);

                poUserInfo = getUserInfo(db);

                result = "success";
            } catch (SQLiteCantOpenDatabaseException | IOException e) {
                Log.d(TAG, "Error opening sqlite database: " + e.getMessage());
                return "error";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnDataOwnerRetrieve(psOwner);

                callback.OnDCPListRetrieve(poDCPData);

                callback.OnOwnerInfoRetrieve(poUserInfo);
            } else {
                callback.OnFailedRetrieveInfo("unable to retrieve data.");
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void FindDatabase(FindDatabaseCallback callback){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
//        intent.setType("text/plain");
//        intent.setType("file/*");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        try {
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, new URI(instance.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        callback.OnFind(intent);
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri){
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = instance.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
        }
        return displayName;
    }

    @SuppressLint("Range")
    private String getDataOwner(SQLiteDatabase db){
        String lsOwner = "";
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM User_Info_Master", null);

            if (cursor.getCount() >= 1) {
                while (cursor.moveToNext()) {
                    lsOwner = cursor.getString(cursor.getColumnIndex("sUserName"));
                }
            } else {
                lsOwner = "Data owner info is not save.";
            }

            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
            lsOwner = "Error on getting data owner info.";
        }

        return lsOwner;
    }

    @SuppressLint("Range")
    private ArrayList<DCPData> getDCPData(SQLiteDatabase db){
        ArrayList<DCPData> loData = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT a.sTransNox, " +
                    "a.nEntryNox, " +
                    "a.sAcctNmbr, " +
                    "a.sRemCodex, " +
                    "a.dModified, " +
                    "a.xFullName, " +
                    "a.sPRNoxxxx, " +
                    "a.nTranAmtx, " +
                    "a.nDiscount, " +
                    "a.dPromised, " +
                    "a.nOthersxx, " +
                    "a.sRemarksx, " +
                    "a.cTranType, " +
                    "a.nTranTotl, " +
                    "a.cApntUnit, " +
                    "a.sBranchCd, " +
                    "b.sTransNox AS sImageIDx, " +
                    "b.sFileCode, " +
                    "b.sSourceCD, " +
                    "b.sImageNme, " +
                    "b.sMD5Hashx, " +
                    "b.sFileLoct, " +
                    "b.nLongitud, " +
                    "b.nLatitude, " +
                    "c.sLastName, " +
                    "c.sFrstName, " +
                    "c.sMiddName, " +
                    "c.sSuffixNm, " +
                    "c.sHouseNox, " +
                    "c.sAddressx, " +
                    "c.sTownIDxx, " +
                    "c.cGenderxx, " +
                    "c.cCivlStat, " +
                    "c.dBirthDte, " +
                    "c.dBirthPlc, " +
                    "c.sLandline, " +
                    "c.sMobileNo, " +
                    "c.sEmailAdd, " +
                    "d.cReqstCDe AS saReqstCde, " +
                    "d.cAddrssTp AS saAddrsTp, " +
                    "d.sHouseNox AS saHouseNox, " +
                    "d.sAddressx AS saAddress, " +
                    "d.sTownIDxx AS saTownIDxx, " +
                    "d.sBrgyIDxx AS saBrgyIDxx, " +
                    "d.cPrimaryx AS saPrimaryx, " +
                    "d.nLatitude AS saLatitude, " +
                    "d.nLongitud AS saLongitude, " +
                    "d.sRemarksx AS saRemarksx," +
                    "e.cReqstCDe AS smReqstCde, " +
                    "e.sMobileNo AS smContactNox, " +
                    "e.cPrimaryx AS smPrimaryx, " +
                    "e.sRemarksx AS smRemarksx " +
                    "FROM LR_DCP_Collection_Detail a " +
                    "LEFT JOIN Image_Information b " +
                    "ON a.sTransNox = b.sSourceNo " +
                    "AND a.sAcctNmbr = b.sDtlSrcNo " +
                    "LEFT JOIN Client_Update_Request c " +
                    "ON a.sTransNox = c.sSourceNo " +
                    "AND a.sAcctNmbr = c.sDtlSrcNo " +
                    "LEFT JOIN Address_Update_Request d " +
                    "ON a.sClientID = d.sClientID " +
                    "LEFT JOIN MOBILE_UPDATE_REQUEST e " +
                    "ON a.sClientID = e.sClientID " +
                    "WHERE a.cSendStat <> '1'", null);

            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();
                for (int x = 0; x < cursor.getCount(); x++) {
                    DCPData data = new DCPData();
                    data.sTransNox = cursor.getString(cursor.getColumnIndex("sTransNox"));
                    data.nEntryNox = cursor.getInt(cursor.getColumnIndex("nEntryNox"));
                    data.sAcctNmbr = cursor.getString(cursor.getColumnIndex("sAcctNmbr"));
                    data.sRemCodex = cursor.getString(cursor.getColumnIndex("sRemCodex"));
                    data.dModified = cursor.getString(cursor.getColumnIndex("dModified"));
                    data.xFullName = cursor.getString(cursor.getColumnIndex("xFullName"));
                    data.sPRNoxxxx = cursor.getString(cursor.getColumnIndex("sPRNoxxxx"));
                    data.nTranAmtx = cursor.getString(cursor.getColumnIndex("nTranAmtx"));
                    data.nDiscount = cursor.getString(cursor.getColumnIndex("nDiscount"));
                    data.nOthersxx = cursor.getString(cursor.getColumnIndex("nOthersxx"));
                    data.sRemarksx = cursor.getString(cursor.getColumnIndex("sRemarksx"));
                    data.cTranType = cursor.getString(cursor.getColumnIndex("cTranType"));
                    data.nTranTotl = cursor.getString(cursor.getColumnIndex("nTranTotl"));
                    data.cApntUnit = cursor.getString(cursor.getColumnIndex("cApntUnit"));
                    data.sBranchCd = cursor.getString(cursor.getColumnIndex("sBranchCd"));
                    data.dPromised = cursor.getString(cursor.getColumnIndex("dPromised"));
                    data.sImageIDx = cursor.getString(cursor.getColumnIndex("sImageIDx"));
                    data.sFileCode = cursor.getString(cursor.getColumnIndex("sFileCode"));
                    data.sSourceCD = cursor.getString(cursor.getColumnIndex("sSourceCD"));
                    data.sImageNme = cursor.getString(cursor.getColumnIndex("sImageNme"));
                    data.sMD5Hashx = cursor.getString(cursor.getColumnIndex("sMD5Hashx"));
                    data.sFileLoct = cursor.getString(cursor.getColumnIndex("sFileLoct"));
                    data.nLongitud = cursor.getString(cursor.getColumnIndex("nLongitud"));
                    data.nLatitude = cursor.getString(cursor.getColumnIndex("nLatitude"));
                    data.sLastName = cursor.getString(cursor.getColumnIndex("sLastName"));
                    data.sFrstName = cursor.getString(cursor.getColumnIndex("sFrstName"));
                    data.sMiddName = cursor.getString(cursor.getColumnIndex("sMiddName"));
                    data.sSuffixNm = cursor.getString(cursor.getColumnIndex("sSuffixNm"));
                    data.sHouseNox = cursor.getString(cursor.getColumnIndex("sHouseNox"));
                    data.sAddressx = cursor.getString(cursor.getColumnIndex("sAddressx"));
                    data.sTownIDxx = cursor.getString(cursor.getColumnIndex("sTownIDxx"));
                    data.cGenderxx = cursor.getString(cursor.getColumnIndex("cGenderxx"));
                    data.cCivlStat = cursor.getString(cursor.getColumnIndex("cCivlStat"));
                    data.dBirthDte = cursor.getString(cursor.getColumnIndex("dBirthDte"));
                    data.dBirthPlc = cursor.getString(cursor.getColumnIndex("dBirthPlc"));
                    data.sLandline = cursor.getString(cursor.getColumnIndex("sLandline"));
                    data.sMobileNo = cursor.getString(cursor.getColumnIndex("sMobileNo"));
                    data.sEmailAdd = cursor.getString(cursor.getColumnIndex("sEmailAdd"));
                    data.smReqstCde = cursor.getString(cursor.getColumnIndex("smReqstCde"));
                    data.smPrimaryx = cursor.getString(cursor.getColumnIndex("smPrimaryx"));
                    data.smContactNox = cursor.getString(cursor.getColumnIndex("smContactNox"));
                    data.smRemarksx = cursor.getString(cursor.getColumnIndex("smRemarksx"));
                    loData.add(data);
                    cursor.moveToNext();
                }
            }

            cursor.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        return loData;
    }

    @SuppressLint("Range")
    private UserInfo getUserInfo(SQLiteDatabase db){
        UserInfo loInfo = new UserInfo();
        try {
            Cursor cursor = db.rawQuery("SELECT a.*, b.* FROM User_Info_Master a LEFT JOIN App_Token_Info b", null);

            if (cursor.getCount() >= 1) {
                while (cursor.moveToNext()) {
                    loInfo.UserID = cursor.getString(cursor.getColumnIndex("sUserIDxx"));
                    loInfo.ClientId = cursor.getString(cursor.getColumnIndex("sClientID"));
                    loInfo.LogNumber = cursor.getString(cursor.getColumnIndex("sLogNoxxx"));
                    loInfo.AppToken = cursor.getString(cursor.getColumnIndex("sTokenInf"));
                    loInfo.ProducID = "gRider";
                    loInfo.DeviceID = cursor.getString(cursor.getColumnIndex("sDeviceID"));
                    loInfo.MobileNo = cursor.getString(cursor.getColumnIndex("sUserName"));
                }
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return loInfo;
    }

    private HashMap<String, String> initHttpHeaders(UserInfo info) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Calendar calendar = Calendar.getInstance();


        //TODO: Change Values on user testing and production
        String lsUserIDx = info.UserID;
        String lsClientx = info.ClientId;
        String lsLogNoxx = info.LogNumber;
        String lsTokenxx = info.AppToken;
        String lsProduct = "gRider";
        String lsDevcIDx = "355d1cbe24df1e1d";
        String lsDateTme = SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss");
        String lsDevcMdl = Build.MODEL;
        String lsMobileN = info.MobileNo;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", lsProduct);
        headers.put("g-api-client", lsClientx);
        headers.put("g-api-imei", lsDevcIDx);
        headers.put("g-api-model", "");
        headers.put("g-api-mobile", "09171870011");
        headers.put("g-api-token", lsTokenxx);
        headers.put("g-api-user", lsUserIDx);
        headers.put("g-api-key", lsDateTme);
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", lsLogNoxx);
        return headers;
    }

    public void PostCollectionDetail(ArrayList<DCPData> dcpData, UserInfo foUser, OnPostCollectionListener listener){
        new PostCollectionTask(dcpData, foUser, listener).execute();
    }

    private class PostCollectionTask extends AsyncTask<String, Integer, String>{

        private final OnPostCollectionListener mListener;
        private final List<DCPData> poDcp;
        private final UserInfo poUser;
        private final WebApi poApi;

        public PostCollectionTask(List<DCPData> foDcp, UserInfo foUser, OnPostCollectionListener listener) {
            this.poDcp = foDcp;
            this.poUser = foUser;
            this.mListener = listener;
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            mListener.OnPost("Posting DCP details. Please wait...");
            super.onPreExecute();
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            try {
                boolean[] isDataSent = new boolean[poDcp.size()];
                String[] reason = new String[poDcp.size()];
                for (int x = 0; x < poDcp.size(); x++) {
                    publishProgress(x);
                    try {
                        reason[x] = "reason is unknown \n";
                        DCPData loDetail = poDcp.get(x);

                        JSONObject loData = new JSONObject();

                        if (!loDetail.sRemCodex.isEmpty()) {

                            if (loDetail.sRemCodex.equalsIgnoreCase("PAY")) {
                                loData.put("sPRNoxxxx", loDetail.sPRNoxxxx);
                                loData.put("nTranAmtx", loDetail.nTranAmtx);
                                loData.put("nDiscount", loDetail.nDiscount);
                                loData.put("nOthersxx", loDetail.nOthersxx);
                                loData.put("cTranType", loDetail.cTranType);
                                loData.put("nTranTotl", loDetail.nTranTotl);
//                                               Added by Jonathan 07/27/2021
                                loData.put("sRemarksx", loDetail.sRemarksx);
                            } else if (loDetail.sRemCodex.equalsIgnoreCase("PTP")) {
                                //Required parameters for Promise to pay..
                                loData.put("cApntUnit", loDetail.cApntUnit);
                                loData.put("sBranchCd", loDetail.sBranchCd);
                                loData.put("dPromised", loDetail.dPromised);

                                loData.put("sImageNme", loDetail.sImageNme);
                                loData.put("sSourceCD", loDetail.sSourceCD);
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

                                loData.put("sImageNme", loDetail.sImageNme);
                                loData.put("sSourceCD", loDetail.sSourceCD);
                                if(loDetail.nLongitud != null &&
                                        loDetail.nLatitude != null) {
                                    loData.put("nLongitud", loDetail.nLongitud);
                                    loData.put("nLatitude", loDetail.nLatitude);
                                } else {
                                    loData.put("nLongitud", "0.0");
                                    loData.put("nLatitude", "0.0");
                                }
                            } else {
                                loData.put("sImageNme", loDetail.sImageNme);
                                loData.put("sSourceCD", loDetail.sSourceCD);
                                loData.put("nLongitud", loDetail.nLongitud);
                                loData.put("nLatitude", loDetail.nLatitude);
                            }

                            loData.put("sRemarksx", loDetail.sRemarksx);
                        } else {
                            loData.put("sRemarksx", "Not visited");
                        }

                        JSONObject loJson = new JSONObject();
                        loJson.put("sTransNox", loDetail.sTransNox);
                        loJson.put("nEntryNox", loDetail.nEntryNox);
                        loJson.put("sAcctNmbr", loDetail.sAcctNmbr);
                        if (loDetail.sRemCodex.isEmpty()) {
                            loJson.put("sRemCodex", "NV");
                            loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                        } else {
                            loJson.put("sRemCodex", loDetail.sRemCodex);
                            loJson.put("dModified", loDetail.dModified);
                        }

                        loJson.put("sJsonData", loData);
                        loJson.put("dReceived", "");

                        loJson.put("sUserIDxx", "GAP021003973");
//                        loJson.put("sUserIDxx", poUser.UserID);
                        loJson.put("sDeviceID", "355d1cbe24df1e1d");
//                        params[x] = loJson.toString() + " \n";
                        String lsResponse1 = WebClient.sendRequest(poApi.getUrlDcpSubmit(), loJson.toString(), HttpHeaders.getInstance(instance).getHeaders());
                        if (lsResponse1 == null) {
                            reason[x] = "Server no response \n";
                            isDataSent[x] = false;
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse1);

                            String result = loResponse.getString("result");
                            if (result.equalsIgnoreCase("success")) {
                                if (loDetail.sRemCodex.isEmpty()) {
//                                    poDcp.updateCollectionDetailStatusWithRemarks(loDetail.sTransNox, loDetail.nEntryNox, sRemarksx);
                                } else {
//                                    poDcp.updateCollectionDetailStatus(loDetail.sTransNox, loDetail.nEntryNox);
                                }
                                isDataSent[x] = true;
                            } else {
                                JSONObject loError = loResponse.getJSONObject("error");
                                String lsMessage = loError.getString("message");
                                isDataSent[x] = false;
                                reason[x] = lsMessage + "\n";
                            }
                        }

                        boolean allDataSent = true;
                        for (boolean b : isDataSent) {
                            if (!b) {
                                allDataSent = false;
                                break;
                            }
                        }

                        if (allDataSent) {
                            JSONObject loparam = new JSONObject();
                            loparam.put("sTransNox", poDcp.get(0).sTransNox);
                            String lsResponse2 = WebClient.sendRequest(poApi.getUrlPostDcpMaster(), loparam.toString(), HttpHeaders.getInstance(instance).getHeaders());
                            if (lsResponse2 == null) {
//                                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response on posting DCP master detail. Tap 'Okay' to create dcp file for backup");
                            } else {
                                JSONObject loResponse = new JSONObject(lsResponse2);
                                String result = loResponse.getString("result");
                                if (result.equalsIgnoreCase("success")) {
//                                    poDcp.updateSentPostedDCPMaster(laCollDetl.get(0).sTransNox);
//                                    lsResult = loResponse.toString();
                                } else {
                                    JSONObject loError = loResponse.getJSONObject("error");
//                                    lsResult = loError.toString();
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
//                        isDataSent[x] = false;
                        reason[x] = e.getMessage() + "\n";
                    }

                    Thread.sleep(1000);
                    }
                } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListener.OnPostSuccess("");
//            mListener.OnPostFailed("");
        }
    }
}
