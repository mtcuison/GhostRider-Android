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

package org.rmj.g3appdriver.lib.PetManager;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DSelfieLog;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.WebClient;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.WebApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelfieLog {
    private static final String TAG = SelfieLog.class.getSimpleName();

    private final DSelfieLog poDao;

    private final EmployeeMaster poUser;

    private final AppConfigPreference poConfig;
    private final RImageInfo poImage;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private String message;

    public SelfieLog(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).SelfieDao();
        this.poUser = new EmployeeMaster(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poImage = new RImageInfo(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<List<ESelfieLog>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return poDao.getCurrentTimeLogIfExist(DateLog);
    }

    public LiveData<List<ESelfieLog>> GetAllEmployeeTimeLog(String fsVal){
        return poDao.getAllEmployeeTimeLog(fsVal);
    }

    public int checkBranchCodeIfExist(String BranchCd, String Transact){
        return poDao.checkBranchCodeIfExist(BranchCd, Transact);
    }

    public String SaveSelfieLog(SelfieLogDetail foVal){
        try{
            ESelfieLog loSelfie = new ESelfieLog();
            String lsTransNo = CreateUniqueID();
            loSelfie.setTransNox(lsTransNo);
            loSelfie.setBranchCd(foVal.getBranchCode());
            loSelfie.setLatitude(foVal.getLatitude());
            loSelfie.setLongitud(foVal.getLongitude());
            loSelfie.setTransact(AppConstants.CURRENT_DATE);
            loSelfie.setEmployID(poSession.getEmployeeID());
            loSelfie.setLogTimex(new AppConstants().DATE_MODIFIED());
            loSelfie.setSendStat("0");

            String lsImageID = poImage.SaveSelfieLogImage(
                    foVal.getFileName(),
                    foVal.getFileLocation(),
                    foVal.getLatitude(),
                    foVal.getLongitude());
            if(lsImageID == null){
                message = poImage.getMessage();
                return null;
            }

            loSelfie.setImageIDx(lsImageID);
            poDao.SaveSelfieLog(loSelfie);
            Log.d(TAG, "Selfie log info has been save.");
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadSelfieLog(String fsVal){
        try{
            ESelfieLog loDetail = poDao.GetSelfieLog(fsVal);

            if(loDetail == null){
                message = "Unable to find selfie log to upload.";
                Log.e(TAG, message);
                return false;
            }

            String lsImageID = loDetail.getImageIDx();

            if(!poImage.UploadImage(lsImageID)){
                message = poImage.getMessage();
                return false;
            }

            Thread.sleep(1000);

            JSONObject params = new JSONObject();
            params.put("sEmployID", loDetail.getEmployID());
            params.put("dLogTimex", loDetail.getLogTimex());
            params.put("nLatitude", loDetail.getLatitude());
            params.put("nLongitud", loDetail.getLongitud());
            params.put("sBranchCd", loDetail.getBranchCd());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlPostSelfielog(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            poDao.updateEmployeeLogStat(
                    loResponse.getString("sTransNox"),
                    fsVal,
                    new AppConstants().DATE_MODIFIED());

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadSelfieLogs(){
        try{
            List<ESelfieLog> loSelfies = poDao.GetSelfieLogsForUpload();

            if(loSelfies == null){
                message = "No Selfie log to upload.";
                return false;
            }

            if(loSelfies.size() == 0){
                message = "No Selfie log to upload.";
                return false;
            }

            for(int x = 0; x < loSelfies.size(); x++){
                ESelfieLog loDetail = loSelfies.get(x);

                String lsImageID = loDetail.getImageIDx();

                if(!poImage.UploadImage(lsImageID)){
                    message = poImage.getMessage();
                    Log.e(TAG, message);
                    return false;
                }

                Thread.sleep(1000);

                JSONObject params = new JSONObject();
                params.put("sEmployID", loDetail.getEmployID());
                params.put("dLogTimex", loDetail.getLogTimex());
                params.put("nLatitude", loDetail.getLatitude());
                params.put("nLongitud", loDetail.getLongitud());
                params.put("sBranchCd", loDetail.getBranchCd());

                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlPostSelfielog(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = "Server no response";
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                String lsTransNo = loDetail.getTransNox();

                poDao.updateEmployeeLogStat(
                        loResponse.getString("sTransNox"),
                        lsTransNo,
                        new AppConstants().DATE_MODIFIED());
                Log.d(TAG, "Selfie log image uploaded successfully");
                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean HasUnfinishedInventory(){
        try{
            if(!poSession.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))){
                message = "Employee is not authorized to use cash count.";
                return false;
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
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

    public static class SelfieLogDetail{
        private String sBranchCd;
        private String nLatitude;
        private String nLongtude;
        private String sFileName;
        private String sFileLoct;

        public SelfieLogDetail() {
        }

        public String getBranchCode() {
            return sBranchCd;
        }

        public void setBranchCode(String sBranchCd) {
            this.sBranchCd = sBranchCd;
        }

        public String getLatitude() {
            return nLatitude;
        }

        public void setLatitude(String nLatitude) {
            this.nLatitude = nLatitude;
        }

        public String getLongitude() {
            return nLongtude;
        }

        public void setLongitude(String nLongtude) {
            this.nLongtude = nLongtude;
        }

        public String getFileName() {
            return sFileName;
        }

        public void setFileName(String sFileName) {
            this.sFileName = sFileName;
        }

        public String getFileLocation() {
            return sFileLoct;
        }

        public void setLocation(String sFileLoct) {
            this.sFileLoct = sFileLoct;
        }
    }
}
