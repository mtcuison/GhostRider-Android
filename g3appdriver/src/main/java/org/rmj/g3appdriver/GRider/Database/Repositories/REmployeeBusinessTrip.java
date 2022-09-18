/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 8/13/21 10:35 AM
 * project file last modified : 8/13/21 10:35 AM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class REmployeeBusinessTrip {
    private static final String TAG = REmployeeBusinessTrip.class.getSimpleName();

    private final DEmployeeBusinessTrip poDao;
    private final Application instance;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private String transno, message;

    public REmployeeBusinessTrip(Application application){
        this.instance = application;
        this.poDao = GGC_GriderDB.getInstance(instance).employeeOBDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insert(EEmployeeBusinessTrip obLeave) {
        poDao.insert(obLeave);
    }

    public void update(EEmployeeBusinessTrip obLeave) {
        poDao.update(obLeave);
    }

    public void insertNewOBLeave(EEmployeeBusinessTrip obLeave) {
        poDao.insertNewOBLeave(obLeave);
    }

    public List<EEmployeeBusinessTrip> getOBIfExist(String TransNox) {
        return poDao.getOBIfExist(TransNox);
    }

    public LiveData<EEmployeeBusinessTrip> getBusinessTripInfo(String TransNox) {
        return poDao.getBusinessTripInfo(TransNox);
    }

    public void updateOBSentStatus(String TransNox, String newTransNox) {
        poDao.updateOBSentStatus(TransNox, newTransNox);
    }

    public void updateOBApproval(String TranStat, String TransNox, String DateSent) {
        poDao.updateOBApproval(TranStat, TransNox, DateSent);
    }

    public LiveData<List<EEmployeeBusinessTrip>> getOBListForApproval() {
        return poDao.getOBListForApproval();
    }

    public LiveData<List<EEmployeeBusinessTrip>> getOBList() {
        return poDao.getOBList();
    }

    public List<EEmployeeBusinessTrip> getUnsentEmployeeOB() {
        return poDao.getUnsentEmployeeOB();
    }

    public LiveData<List<EEmployeeBusinessTrip>> GetApproveBusTrip() {
        return poDao.GetApproveBusTrip();
    }

    public boolean SaveOBApplication(OBApplication foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return false;
            }

            EEmployeeBusinessTrip detail = new EEmployeeBusinessTrip();
            String lsTransNo = CreateUniqueID();
            detail.setTransNox(lsTransNo);
            detail.setTransact(new AppConstants().DATE_MODIFIED);
            detail.setEmployee(poSession.getEmployeeID());
            detail.setBranchNm(foVal.getDestinat());
            detail.setDateFrom(foVal.getDateFrom());
            detail.setDateThru(foVal.getDateThru());
            detail.setRemarksx(foVal.getRemarksx());
            detail.setDestinat(foVal.getDestinat());
            detail.setApproved(poSession.getUserID());
            detail.setDapprove(AppConstants.CURRENT_DATE);
            detail.setAppldFrx(null);
            detail.setAppldTox(null);
            detail.setTranStat("0");
            detail.setSendStat("0");
            poDao.insert(detail);
            Log.d(TAG, "Business trip application save locally.");
            Log.d(TAG, "Business trip transaction no. :" + lsTransNo);
            transno = lsTransNo;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadApplication(){
        try{
            EEmployeeBusinessTrip loDetail = poDao.GetOBApplicationForPosting(transno);
            if(loDetail == null){
                message = "Unable to retrieve business trip application to upload.";
                Log.e(TAG, message);
                return false;
            }

            JSONObject loJson = new JSONObject();
            loJson.put("sTransNox", loDetail.getTransNox());
            loJson.put("dTransact", loDetail.getTransact());
            loJson.put("sEmployID", loDetail.getEmployee());
            loJson.put("dDateFrom", loDetail.getDateFrom());
            loJson.put("dDateThru", loDetail.getDateThru());
            loJson.put("sDestinat", loDetail.getDestinat());
            loJson.put("sRemarksx", loDetail.getRemarksx());
            loJson.put("sApproved", "");
            loJson.put("dApproved", "");
            loJson.put("dAppldFrx", "");
            loJson.put("dAppldTox", "");
            loJson.put("cTranStat", "0");
            loJson.put("sModified", "");
            loJson.put("dModified", "");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSendObApplication(poConfig.isBackUpServer()),
                    loJson.toString(),
                    poHeaders.getHeaders());
            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("success")){
                poDao.updateOBSentStatus(loJson.getString("sTransNox"),
                        loResponse.getString("sTransNox"));
                return true;
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                Log.e(TAG, message);
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadApplications(){
        try{
            List<EEmployeeBusinessTrip> loObs = poDao.GetUnpostedOBApplications();
            if(loObs == null){
                message = "No business trip application found.";
                return false;
            }

            if(loObs.size() == 0){
                message = "No business trip application found.";
                return false;
            }

            for(int x = 0; x < loObs.size(); x++){
                EEmployeeBusinessTrip loDetail = loObs.get(x);

                JSONObject loJson = new JSONObject();
                loJson.put("sTransNox", loDetail.getTransNox());
                loJson.put("dTransact", loDetail.getTransact());
                loJson.put("sEmployID", loDetail.getEmployee());
                loJson.put("dDateFrom", loDetail.getDateFrom());
                loJson.put("dDateThru", loDetail.getDateThru());
                loJson.put("sDestinat", loDetail.getDestinat());
                loJson.put("sRemarksx", loDetail.getRemarksx());
                loJson.put("sApproved", "");
                loJson.put("dApproved", "");
                loJson.put("dAppldFrx", "");
                loJson.put("dAppldTox", "");
                loJson.put("cTranStat", "0");
                loJson.put("sModified", "");
                loJson.put("dModified", "");

                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlSendObApplication(poConfig.isBackUpServer()),
                        loJson.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = "No server response. Restart app or try again later.";
                    return false;
                }
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    poDao.updateOBSentStatus(loJson.getString("sTransNox"),
                            loResponse.getString("sTransNox"));
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Log.e(TAG, message);
                }
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean DownloadApplications(){
        try{
            String obResponse = WebClient.sendRequest(
                    poApi.getUrlGetObApplication(poConfig.isBackUpServer()),
                    new JSONObject().toString(),
                    poHeaders.getHeaders());
            if (obResponse == null) {
               message = "No server response.";
               return false;
            } else {
                JSONObject loResponse = new JSONObject(obResponse);
                String result = loResponse.getString("result");
                if (result.equalsIgnoreCase("success")) {
                    JSONArray jsonA = loResponse.getJSONArray("payload");
                    for (int x = 0; x < jsonA.length(); x++) {
                        JSONObject loJson = jsonA.getJSONObject(x);
                        if (poDao.getOBIfExist(loJson.getString("sTransNox")).size() > 0) {
                            Log.d(TAG, "OB application already exist.");
                        } else {
                            EEmployeeBusinessTrip loOB = new EEmployeeBusinessTrip();
                            loOB.setTransNox(loJson.getString("sTransNox"));
                            loOB.setTransact(loJson.getString("dTransact"));
                            loOB.setEmployee(loJson.getString("sEmployID"));
                            loOB.setFullName(loJson.getString("sCompnyNm"));
                            loOB.setBranchNm(loJson.getString("sBranchNm"));
                            loOB.setDeptName(loJson.getString("sDeptName"));
                            loOB.setDateFrom(loJson.getString("dDateFrom"));
                            loOB.setDateThru(loJson.getString("dDateThru"));
                            loOB.setRemarksx(loJson.getString("sRemarksx"));
                            loOB.setTranStat(loJson.getString("cTranStat"));
                            poDao.insert(loOB);
                        }
                    }
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    if (message != null) {
                        message = message + "\n" + "Fail to retrieve business trip applications. Reason: " + loError.getString("message");
                    } else {
                        message = "Fail to retrieve business trip applications. Reason: " + loError.getString("message");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean SaveBusinessTripApproval(OBApprovalInfo foVal){
        try{
            poDao.updateOBApproval(
                    foVal.getTranStat(),
                    foVal.getTransNox(),
                    new AppConstants().DATE_MODIFIED);
            transno = foVal.getTransNox();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean PostBusinessTripApproval(){
        try{
            EEmployeeBusinessTrip loDetail = poDao.GetOBApprovalForPosting(transno);
            if(loDetail == null){
                message = "No business trip application found.";
                return false;
            }

            JSONObject param = new JSONObject();
            param.put("sTransNox", loDetail.getTransNox());
            param.put("dAppldFrx", loDetail.getAppldFrx());
            param.put("dAppldTox", loDetail.getAppldTox());
            param.put("sApproved", loDetail.getApproved());
            param.put("dApproved", loDetail.getDapprove());
            param.put("cTranStat", loDetail.getTranStat());
            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlConfirmObApplication(poConfig.isBackUpServer()),
                    param.toString(), poHeaders.getHeaders());
            if (lsResponse == null) {
                message = "No server response. Restart app or try again later.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String result = loResponse.getString("result");
            if (result.equalsIgnoreCase("success")) {
                poDao.updateObApprovalPostedStatus(transno);
                if (loDetail.getTranStat().equalsIgnoreCase("1")) {
                    message = "Business trip has been approve successfully.";
                } else {
                    message = "Business trip has been disapprove successfully.";
                }
                return true;
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadUnpostedApproval(){
        try{
            List<EEmployeeBusinessTrip> loApprovals = poDao.GetUnpostedApprovals();
            if(loApprovals == null){
                message = "No unposted approval found";
                Log.e(TAG, message);
                return false;
            }

            if(loApprovals.size() == 0){
                message = "No unposted approval found";
                Log.e(TAG, message);
                return false;
            }

            for(int x = 0; x < loApprovals.size(); x++){
                EEmployeeBusinessTrip loDetail = loApprovals.get(x);
                JSONObject param = new JSONObject();
                param.put("sTransNox", loDetail.getTransNox());
                param.put("dAppldFrx", loDetail.getAppldFrx());
                param.put("dAppldTox", loDetail.getAppldTox());
                param.put("sApproved", loDetail.getApproved());
                param.put("dApproved", loDetail.getDapprove());
                param.put("cTranStat", loDetail.getTranStat());
                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlConfirmObApplication(poConfig.isBackUpServer()),
                        param.toString(), poHeaders.getHeaders());
                if (lsResponse == null) {
                    message = "No server response. Restart app or try again later.";
                    Log.e(TAG, message);
                    return false;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String result = loResponse.getString("result");
                if (result.equalsIgnoreCase("success")) {
                    poDao.updateObApprovalPostedStatus(transno);
                    if (loDetail.getTranStat().equalsIgnoreCase("1")) {
                        message = "Business trip has been approve successfully.";
                    } else {
                        message = "Business trip has been disapprove successfully.";
                    }
                    Log.d(TAG, message);
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Log.e(TAG, message);
                }
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

    public static class OBApplication {

        private String TransNox;
        private String Transact;
        private String EmployID;
        private String DateFrom;
        private String DateThru;
        private String Destinat;
        private String Remarksx;
        private String Approved;
        private String DateAppv;
        private String AppldFrx;
        private String AppldTox;
        private String TranStat;
        private String Modifier;
        private String Modified;

        private String message;

        public OBApplication(){

        }

        public String getTransNox() {
            return TransNox;
        }

        public void setTransNox(String transNox) {
            TransNox = transNox;
        }

        public String getTransact() {
            return Transact;
        }

        public void setTransact(String transact) {
            Transact = transact;
        }

        public String getEmployID() {
            return EmployID;
        }

        public void setEmployID(String employID) {
            EmployID = employID;
        }

        public String getDateFrom() {
            return DateFrom;
        }

        public void setDateFrom(String dateFrom) {
            DateFrom = dateFrom;
        }

        public String getDateThru() {
            return DateThru;
        }

        public void setDateThru(String dateThru) {
            DateThru = dateThru;
        }

        public String getDestinat() {
            return Destinat;
        }

        public void setDestinat(String destinat) {
            Destinat = destinat;
        }

        public String getRemarksx() {
            return Remarksx.trim();
        }

        public void setRemarksx(String remarksx) {
            Remarksx = remarksx;
        }

        public String getApproved() {
            return Approved;
        }

        public void setApproved(String approved) {
            Approved = approved;
        }

        public String getDateAppv() {
            return DateAppv;
        }

        public void setDateAppv(String dateAppv) {
            DateAppv = dateAppv;
        }

        public String getAppldFrx() {
            return AppldFrx;
        }

        public void setAppldFrx(String appldFrx) {
            AppldFrx = appldFrx;
        }

        public String getAppldTox() {
            return AppldTox;
        }

        public void setAppldTox(String appldTox) {
            AppldTox = appldTox;
        }

        public String getTranStat() {
            return TranStat;
        }

        public void setTranStat(String tranStat) {
            TranStat = tranStat;
        }

        public String getModifier() {
            return Modifier;
        }

        public void setModifier(String modifier) {
            Modifier = modifier;
        }

        public String getModified() {
            return Modified;
        }

        public void setModified(String modified) {
            Modified = modified;
        }

        public String getMessage() {
            return message;
        }

        public boolean isDataValid(){
            return isDateFrom() &&
                    isDateFrom();
        }

        private boolean isDateFrom(){
            if(DateFrom.trim().isEmpty()){
                message = "Please enter Date From";
                return false;
            }
            return true;
        }
        private boolean isDateThru(){
            if(DateThru.trim().isEmpty()){
                message = "Please enter Date Thru";
                return false;
            }
            return true;
        }

    }

    public static class OBApprovalInfo {

        private String TransNox;
        private String AppldFrx;
        private String AppldTox;
        private String Approved;
        private String DateAppv;
        private String TranStat;

        public OBApprovalInfo() {
        }

        public String getTransNox() {
            return TransNox;
        }

        public void setTransNox(String transNox) {
            TransNox = transNox;
        }

        public String getAppldFrx() {
            return AppldFrx;
        }

        public void setAppldFrx(String appldFrx) {
            AppldFrx = appldFrx;
        }

        public String getAppldTox() {
            return AppldTox;
        }

        public void setAppldTox(String appldTox) {
            AppldTox = appldTox;
        }

        public String getApproved() {
            return Approved;
        }

        public void setApproved(String approved) {
            Approved = approved;
        }

        public String getDateAppv() {
            return DateAppv;
        }

        public void setDateAppv(String dateAppv) {
            DateAppv = dateAppv;
        }

        public String getTranStat() {
            return TranStat;
        }

        public void setTranStat(String tranStat) {
            TranStat = tranStat;
        }
    }
}
