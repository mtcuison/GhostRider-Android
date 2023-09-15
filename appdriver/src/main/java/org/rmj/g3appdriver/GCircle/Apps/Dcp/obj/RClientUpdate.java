/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.LoanUnit;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientUpdate;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RClientUpdate {
    private static final String TAG = RClientUpdate.class.getSimpleName();

    private final DClientUpdate poDao;

    private String message;

    public RClientUpdate(Application application){
        this.poDao = GGC_GCircleDB.getInstance(application).ClientUpdateDao();
    }

    public String getMessage() {
        return message;
    }

    public void insertClientUpdateInfo(EClientUpdate clientUpdate){
        this.poDao.SaveClientUpdate(clientUpdate);
    }

    public void updateClientInfo(EClientUpdate clientUpdate){
        this.poDao.updateClientInfo(clientUpdate);
    }
    public LiveData<EClientUpdate> selectClient(String sSourceNo, String DtlSrcNo){
        return this.poDao.selectClient(sSourceNo, DtlSrcNo);
    }

    public LiveData<List<EClientUpdate>> selectClientUpdate(){
        return poDao.selectClientUpdate();
    }

    public void UpdateClientInfoStatus(String ClientID){
        this.poDao.updateClientInfoImage(ClientID, AppConstants.DATE_MODIFIED());
    }

    public void UpdateClientImage(String ClientID, String ImageName){
        this.poDao.updateClientInfoImage(ClientID, ImageName);
    }

    /**
     *
     * @param ClientID pass a unique key to determine which info must be converted to json...
     * @return returns JSON object that is being created by doInbackground method.
     *          JSONObject contains all info inside Client_Update_Request table...
     */
    public JSONObject getClientUpdateDetail(String ClientID){
        ClientUpdateToJsonTask loUpdate = new ClientUpdateToJsonTask(poDao);
        loUpdate.execute(ClientID);
        return loUpdate.getDetail();
    }

    private static class ClientUpdateToJsonTask extends AsyncTask<String, Void, JSONObject>{
        private JSONObject loDetail;
        private DClientUpdate clientDao;

        public ClientUpdateToJsonTask(DClientUpdate clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject loJson = new JSONObject();
            try{
                EClientUpdate loClient = clientDao.getClientUpdateInfo(strings[0]).getValue();
                loJson.put("sClientID", loClient.getClientID());
                loJson.put("sSourceCd", loClient.getSourceCd());
                loJson.put("sSourceNo", loClient.getSourceNo());
                loJson.put("sDtlSrcNo", loClient.getDtlSrcNo());
                loJson.put("sLastName", loClient.getLastName());
                loJson.put("sFrstName", loClient.getFrstName());
                loJson.put("sMiddName", loClient.getMiddName());
                loJson.put("sSuffixNm", loClient.getSuffixNm());
                loJson.put("sHouseNox", loClient.getHouseNox());
                loJson.put("sAddressx", loClient.getAddressx());
                loJson.put("sTownIDxx", loClient.getTownIDxx());
                loJson.put("cGenderxx", loClient.getGenderxx());
                loJson.put("cCivlStat", loClient.getCivlStat());
                loJson.put("dBirthDte", loClient.getBirthDte());
                loJson.put("dBirthPlc", loClient.getBirthPlc());
                loJson.put("sLandline", loClient.getLandline());
                loJson.put("sMobileNo", loClient.getMobileNo());
                loJson.put("sEmailAdd", loClient.getEmailAdd());
                loJson.put("sImageNme", loClient.getImageNme());
            } catch (Exception e){
                e.printStackTrace();
            }
            return loJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            loDetail = jsonObject;
        }

        public JSONObject getDetail(){
            return loDetail;
        }
    }

    public EClientUpdate getClientUpdateInfoForPosting(String TransNox, String AcctNox){
        return poDao.getClientUpdateInfoForPosting(TransNox, AcctNox);
    }

    public String SaveClientUpdate(LoanUnit foVal){
        try {
            EClientUpdate loClient = new EClientUpdate();
            String lsTransNo = CreateUniqueID();
            loClient.setSourceNo(lsTransNo);
            loClient.setDtlSrcNo(foVal.getAccntNox());
            loClient.setFrstName(foVal.getFrstName());
            loClient.setLastName(foVal.getLastName());
            loClient.setMiddName(foVal.getMiddName());
            loClient.setSuffixNm(foVal.getSuffixxx());
            loClient.setAddressx(foVal.getStreetxx());
            loClient.setBirthDte(foVal.getBirthDte());
            loClient.setBirthPlc(foVal.getBirthPlc());
            loClient.setEmailAdd(foVal.getEmailAdd());
            loClient.setHouseNox(foVal.getHouseNox());
            loClient.setImageNme(foVal.getFileName());
            loClient.setLandline(foVal.getPhoneNox());
            loClient.setMobileNo(foVal.getMobileNo());
            loClient.setTownIDxx(foVal.getTownIDxx());
            loClient.setBarangay(foVal.getBrgyIDxx());
            loClient.setGenderxx(foVal.getGenderxx());
            loClient.setCivlStat(foVal.getCvilStat());
            loClient.setModified(AppConstants.DATE_MODIFIED());
            loClient.setSendStat("0");
            loClient.setSourceCd("DCPa");
            poDao.SaveClientUpdate(loClient);

            Log.d(TAG, "Client update request details has been save.");
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }
}
