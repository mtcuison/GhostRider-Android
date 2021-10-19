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

import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.DCPData;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.UserInfo;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
            try {
                //Defining your external storage path.
                String extStore = Environment.getExternalStorageDirectory().getPath();

                //Defining the file to be opened.
                File dbfile = new File(extStore + "/" + getFileName(poData));

                //stablishing the connection
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);

                //working with query and results.
                psOwner = getDataOwner(db);

                poDCPData = getDCPData(db);

                poUserInfo = getUserInfo(db);

            } catch (SQLiteCantOpenDatabaseException e) {
                Log.d(TAG, "Error opening sqlite database: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.OnDataOwnerRetrieve(psOwner);

            callback.OnDCPListRetrieve(poDCPData);

            callback.OnOwnerInfoRetrieve(poUserInfo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void FindDatabase(FindDatabaseCallback callback){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.sqlite3");
//        intent.setType("text/plain");

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
            Cursor cursor = db.rawQuery("SELECT * FROM User_Info_Master", null);

            if (cursor.getCount() >= 1) {
                while (cursor.moveToNext()) {
                    loInfo.UserID = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.ClientId = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.LogNumber = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.AppToken = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.ProducID = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.DeviceID = cursor.getString(cursor.getColumnIndex("sUserName"));
                    loInfo.MobileNo = cursor.getString(cursor.getColumnIndex("sUserName"));
                }
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return loInfo;
    }

    private Map<String, String> initHttpHeaders(UserInfo info) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Calendar calendar = Calendar.getInstance();


        //TODO: Change Values on user testing and production
        String lsUserIDx = info.UserID;
        String lsClientx = info.ClientId;
        String lsLogNoxx = info.LogNumber;
        String lsTokenxx = info.AppToken;
        String lsProduct = info.ProducID;
        String lsDevcIDx = info.DeviceID;
        String lsDateTme = SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss");
        String lsDevcMdl = Build.MODEL;
        String lsMobileN = info.MobileNo;

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", lsProduct);
        headers.put("g-api-client", lsClientx);
        headers.put("g-api-imei", lsDevcIDx);
        headers.put("g-api-model", lsDevcMdl);
        headers.put("g-api-mobile", lsMobileN);
        headers.put("g-api-token", lsTokenxx);
        headers.put("g-api-user", lsUserIDx);
        headers.put("g-api-key", lsDateTme);
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", lsLogNoxx);
        return headers;
    }
}
