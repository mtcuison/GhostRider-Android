/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/19/21 10:51 AM
 * project file last modified : 6/19/21 10:51 AM
 */

package org.rmj.g3appdriver.lib.PetManager;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.WebApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeLeave {
    private static final String TAG = EmployeeLeave.class.getSimpleName();
    private final DEmployeeLeave poDao;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private final EmployeeMaster poUser;
    private String message;

    public EmployeeLeave(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).employeeLeaveDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
        this.poUser = new EmployeeMaster(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public void insertApplication(EEmployeeLeave poLeave) {
        poDao.insertApplication(poLeave);
    }

    public List<EEmployeeLeave> getTransnoxIfExist(String TransNox, String TranStat) {
        return poDao.getTransnoxIfExist(TransNox, TranStat);
    }

    public LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox) {
        return poDao.getEmployeeLeaveInfo(TransNox);
    }

    public void updateSendStatus(String DateSent, String TransNox, String newTransNox) {
        poDao.updateSendStatus(DateSent, TransNox, newTransNox);
    }

    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList() {
        return poDao.getEmployeeLeaveForApprovalList();
    }

    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveList() {
        return poDao.getEmployeeLeaveList();
    }

    public LiveData<List<EEmployeeLeave>> getApproveLeaveList() {
        return poDao.getApproveLeaveList();
    }

    public String SaveLeaveApplication(LeaveApplication foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            EEmployeeInfo loUser = poDao.GetEmployeeInfo();

            if(loUser == null){
                message = "Invalid user record. Please re-login your account and try again.";
                return null;
            }

            String lsTransNo = CreateUniqueID();
            EEmployeeLeave loApp = new EEmployeeLeave();
            loApp.setTransNox(lsTransNo);
            loApp.setEmployID(loUser.getEmployID());
            loApp.setEntryByx(loUser.getEmployID());
            loApp.setTransact(AppConstants.CURRENT_DATE);
            loApp.setEmployID(foVal.getEmploName());
            loApp.setBranchNm(foVal.getBranchNme());
            loApp.setDateFrom(foVal.getDateFromx());
            loApp.setDateThru(foVal.getDateThrux());
            loApp.setAppldFrx(foVal.getDateFromx());
            loApp.setAppldTox(foVal.getDateThrux());
            loApp.setNoDaysxx(String.valueOf(foVal.getNoOfDaysx()));
            loApp.setPurposex(foVal.getRemarksxx());
            loApp.setEqualHrs(String.valueOf(foVal.getNoOfHours()));
            loApp.setLeaveTyp(foVal.getLeaveType());
            loApp.setEntryDte(AppConstants.CURRENT_DATE);
            loApp.setWithOPay("0");
            loApp.setApproved("0");
            loApp.setTranStat("0");
            poDao.insertApplication(loApp);
            Log.d(TAG, "Leave application has been save to local.");
            message = "Leave application has been save to local";
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadLeaveApplication(String fsVal){
        try{
            EEmployeeLeave loDetail = poDao.GetEmployeeLeave(fsVal);
            if(loDetail == null){
                message = "Unable to retrieve leave application to upload.";
                return false;
            }

            JSONObject param = new JSONObject();
            param.put("sTransNox", loDetail.getTransNox());
            param.put("dTransact", loDetail.getTransact());
            param.put("sEmployID", poSession.getEmployeeID());
            param.put("dDateFrom", loDetail.getDateFrom());
            param.put("dDateThru", loDetail.getDateThru());
            param.put("nNoDaysxx", loDetail.getNoDaysxx());
            param.put("sPurposex", loDetail.getPurposex());
            param.put("cLeaveTyp", loDetail.getLeaveTyp());
            param.put("dAppldFrx", loDetail.getDateFrom());
            param.put("dAppldTox", loDetail.getDateThru());
            param.put("sEntryByx", loDetail.getEntryByx());
            param.put("dEntryDte", loDetail.getEntryDte());
            param.put("nWithOPay", loDetail.getWithOPay());
            param.put("nEqualHrs", loDetail.getEqualHrs());
            param.put("sApproved", loDetail.getApproved());
            param.put("dApproved", "");
            param.put("dSendDate", loDetail.getSendDate());
            param.put("cTranStat", loDetail.getTranStat());
            param.put("sModified", poSession.getEmployeeID());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSendLeaveApplication(poConfig.isBackUpServer()),
                    param.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = "No server response";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                Log.e(TAG, "Failed to save leave application to server. Message: " + lsMessage);
                message = lsMessage;
                return false;
            }

            poDao.updateSendStatus(
                    new AppConstants().DATE_MODIFIED(),
                    fsVal,
                    loResponse.getString("sTransNox"));
            Log.d("Employee Leave", "Leave info updated!");
            message = "Leave application has been save to server.";
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadLeaveApplications(){
        try{
            List<EEmployeeLeave> loDetail = poDao.getUnsentEmployeeLeave();

            if(loDetail == null){
                message = "No leave application found.";
                return false;
            }

            if(loDetail.size() == 0){
                message = "No leave application found.";
                return false;
            }

            for(int x = 0; x < loDetail.size(); x++){
                EEmployeeLeave leave = loDetail.get(x);
                JSONObject param = new JSONObject();
                param.put("sTransNox", leave.getTransNox());
                param.put("dTransact", leave.getTransact());
                param.put("sEmployID", poSession.getEmployeeID());
                param.put("dDateFrom", leave.getDateFrom());
                param.put("dDateThru", leave.getDateThru());
                param.put("nNoDaysxx", leave.getNoDaysxx());
                param.put("sPurposex", leave.getPurposex());
                param.put("cLeaveTyp", leave.getLeaveTyp());
                param.put("dAppldFrx", leave.getDateFrom());
                param.put("dAppldTox", leave.getDateThru());
                param.put("sEntryByx", leave.getEmployID());
                param.put("dEntryDte", leave.getEntryDte());
                param.put("nWithOPay", leave.getWithOPay());
                param.put("nEqualHrs", leave.getEqualHrs());
                param.put("sApproved", leave.getApproved());
                param.put("dApproved", leave.getApproved());
                param.put("dSendDate", AppConstants.CURRENT_DATE);
                param.put("cTranStat", leave.getTranStat());
                param.put("sModified", poSession.getEmployeeID());

                Log.d(TAG, "Uploading leave application no.: " + x + " out of " + loDetail.size());
                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlSendLeaveApplication(poConfig.isBackUpServer()),
                        param.toString(),
                        poHeaders.getHeaders());
                if(lsResponse == null){
                    Log.e(TAG, "Sending employee leave. server no response");

                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String result = loResponse.getString("result");
                if(result.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    Log.e(TAG, loError.getString("message"));

                    Thread.sleep(1000);
                    continue;
                }

                poDao.updateSendStatus(
                        new AppConstants().DATE_MODIFIED(),
                        leave.getTransNox(),
                        loResponse.getString("sTransNox"));
                Log.d("Employee Leave", "Leave info updated!");

                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean DownloadApplications(){
        try{
            String lsAddress = poApi.getUrlGetLeaveApplication(poConfig.isBackUpServer());

            String lsResponse = WebClient.sendRequest(
                    lsAddress,
                    new JSONObject().toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "No server response";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray jsonA = loResponse.getJSONArray("payload");
            for (int x = 0; x < jsonA.length(); x++) {
                JSONObject loJson = jsonA.getJSONObject(x);
                EEmployeeLeave loDetail = poDao.GetEmployeeLeave(loJson.getString("sTransNox"));
                if(loDetail == null){
                    EEmployeeLeave leave = new EEmployeeLeave();
                    leave.setTransNox(loJson.getString("sTransNox"));
                    leave.setTransact(loJson.getString("dTransact"));
                    leave.setEmployID(loJson.getString("xEmployee"));
                    leave.setEntryByx(loJson.getString("sEmployID"));
                    leave.setBranchNm(loJson.getString("sBranchNm"));
                    leave.setDeptName(loJson.getString("sDeptName"));
                    leave.setPositnNm(loJson.getString("sPositnNm"));
                    leave.setAppldFrx(loJson.getString("dAppldFrx"));
                    leave.setAppldTox(loJson.getString("dAppldTox"));
                    leave.setNoDaysxx(loJson.getString("nNoDaysxx"));
                    leave.setPurposex(loJson.getString("sPurposex"));
                    leave.setLeaveTyp(loJson.getString("cLeaveTyp"));
                    leave.setLveCredt(loJson.getString("nLveCredt"));
                    leave.setTranStat(loJson.getString("cTranStat"));
                    leave.setTimeStmp(loJson.getString("dTimeStmp"));
                    leave.setSentStat("1");
                    poDao.insertApplication(leave);
                    Log.d(TAG, "New leave application save!");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)){
                        loDetail.setTransNox(loJson.getString("sTransNox"));
                        loDetail.setTransact(loJson.getString("dTransact"));
                        loDetail.setEmployID(loJson.getString("xEmployee"));
                        loDetail.setEntryByx(loJson.getString("sEmployID"));
                        loDetail.setBranchNm(loJson.getString("sBranchNm"));
                        loDetail.setDeptName(loJson.getString("sDeptName"));
                        loDetail.setPositnNm(loJson.getString("sPositnNm"));
                        loDetail.setAppldFrx(loJson.getString("dAppldFrx"));
                        loDetail.setAppldTox(loJson.getString("dAppldTox"));
                        loDetail.setNoDaysxx(loJson.getString("nNoDaysxx"));
                        loDetail.setPurposex(loJson.getString("sPurposex"));
                        loDetail.setLeaveTyp(loJson.getString("cLeaveTyp"));
                        loDetail.setLveCredt(loJson.getString("nLveCredt"));
                        loDetail.setTranStat(loJson.getString("cTranStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.updateApplication(loDetail);
                        Log.d(TAG, "Leave application record has been updated.");
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

    public boolean DownloadLeaveApplication(String fsVal){
        try{
            if(fsVal == null){
                message = "Please enter transaction no.";
                return false;
            }

            if(fsVal.trim().isEmpty()){
                message = "Please enter transaction no.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("sTransNox", fsVal);
            String lsAddress = poApi.getUrlGetLeaveApplication(poConfig.isBackUpServer());
            Log.d(TAG, "Connecting to " + lsAddress + "...");
            String lsResponse = WebClient.sendRequest(
                    lsAddress,
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "No server response";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;

            }

            JSONArray jsonA = loResponse.getJSONArray("payload");
            for (int x = 0; x < jsonA.length(); x++) {
                JSONObject loJson = jsonA.getJSONObject(x);
                EEmployeeLeave loDetail = poDao.GetEmployeeLeave(loJson.getString("sTransNox"));
                if(loDetail == null){
                    EEmployeeLeave leave = new EEmployeeLeave();
                    leave.setTransNox(loJson.getString("sTransNox"));
                    leave.setTransact(loJson.getString("dTransact"));
                    leave.setEmployID(loJson.getString("xEmployee"));
                    leave.setEntryByx(loJson.getString("sEmployID"));
                    leave.setBranchNm(loJson.getString("sBranchNm"));
                    leave.setDeptName(loJson.getString("sDeptName"));
                    leave.setPositnNm(loJson.getString("sPositnNm"));
                    leave.setAppldFrx(loJson.getString("dAppldFrx"));
                    leave.setAppldTox(loJson.getString("dAppldTox"));
                    leave.setNoDaysxx(loJson.getString("nNoDaysxx"));
                    leave.setPurposex(loJson.getString("sPurposex"));
                    leave.setLeaveTyp(loJson.getString("cLeaveTyp"));
                    leave.setLveCredt(loJson.getString("nLveCredt"));
                    leave.setTranStat(loJson.getString("cTranStat"));
                    leave.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.insertApplication(leave);
                    Log.d(TAG, "New leave application save!");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)){
                        loDetail.setTransNox(loJson.getString("sTransNox"));
                        loDetail.setTransact(loJson.getString("dTransact"));
                        loDetail.setEmployID(loJson.getString("xEmployee"));
                        loDetail.setEntryByx(loJson.getString("sEmployID"));
                        loDetail.setBranchNm(loJson.getString("sBranchNm"));
                        loDetail.setDeptName(loJson.getString("sDeptName"));
                        loDetail.setPositnNm(loJson.getString("sPositnNm"));
                        loDetail.setAppldFrx(loJson.getString("dAppldFrx"));
                        loDetail.setAppldTox(loJson.getString("dAppldTox"));
                        loDetail.setNoDaysxx(loJson.getString("nNoDaysxx"));
                        loDetail.setPurposex(loJson.getString("sPurposex"));
                        loDetail.setLeaveTyp(loJson.getString("cLeaveTyp"));
                        loDetail.setLveCredt(loJson.getString("nLveCredt"));
                        loDetail.setTranStat(loJson.getString("cTranStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.updateApplication(loDetail);
                        Log.d(TAG, "Leave application record has been updated.");
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

    public String SaveLeaveApproval(LeaveApprovalInfo foVal){
       try{
           EEmployeeLeave loDetail = poDao.GetEmployeeLeave(foVal.getTransNox());
           if(loDetail == null){
               message = "Cannot find business trip to approve.";
               return null;
           }
           loDetail.setTransNox(foVal.getTransNox());
           loDetail.setTransact(AppConstants.CURRENT_DATE);
           loDetail.setAppldFrx(foVal.getAppldFrx());
           loDetail.setAppldTox(foVal.getAppldTox());
           loDetail.setTranStat(foVal.getTranStat());
           loDetail.setWithPayx(foVal.getWithPayx());
           loDetail.setWithOPay(foVal.getWithOPay());
           loDetail.setApproved(foVal.getApprovex());
           loDetail.setDApproved(foVal.getApproved());
           poDao.updateApplication(loDetail);
           return loDetail.getTransNox();
       } catch (Exception e){
           e.printStackTrace();
           message = e.getMessage();
           return null;
       }
    }

    public boolean PostLeaveApproval(String fsVal){
        try{
            EEmployeeLeave loDetail = poDao.GetEmployeeLeave(fsVal);
            if(loDetail == null){
                message = "Unable to find leave application to upload.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("dTransact", loDetail.getTransact());
            params.put("dAppldFrx", loDetail.getAppldFrx());
            params.put("dAppldTox", loDetail.getAppldTox());
            params.put("cTranStat", loDetail.getTranStat());
            params.put("nWithPayx", loDetail.getWithPayx());
            params.put("nWithOPay", loDetail.getWithOPay());
            params.put("sApproved", loDetail.getApproved());
            params.put("dApproved", loDetail.getDApproved());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlConfirmLeaveApplication(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null) {
                message = "No server response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                Log.e(TAG, lsMessage);
                message = lsMessage;
                return false;
            }

            poDao.updatePostedApproval(fsVal);
            message = "Leave approval has been posted.";
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

    public static class LeaveApplication {
        private String EmploName = "";
        private String BranchNme = "";
        private String leaveType = "";
        private String dateFromx = "";
        private String dateThrux = "";
        private int noOfDaysx = 0;
        private String Remarksxx = "";
        private int noOfHours = 0;

        private String message = "";

        public LeaveApplication() {
        }

        public String getMessage() {
            return message;
        }

        public String getEmploName() {
            return EmploName;
        }

        public void setEmploName(String emploName) {
            EmploName = emploName;
        }

        public String getBranchNme() {
            return BranchNme;
        }

        public void setBranchNme(String branchNme) {
            BranchNme = branchNme;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getDateFromx() {
            return FormatUIText.toSqlValue(dateFromx);
        }

        public void setDateFromx(String dateFromx) {
            this.dateFromx = dateFromx;
        }

        public String getDateThrux() {
            return FormatUIText.toSqlValue(dateThrux);
        }

        public void setDateThrux(String dateThrux) {
            this.dateThrux = dateThrux;
        }

        public int getNoOfDaysx() {
            return noOfDaysx;
        }

        public void setNoOfDaysx(int noOfDaysx) {
            this.noOfDaysx = noOfDaysx;
        }

        public String getRemarksxx() {
            return Remarksxx.trim();
        }

        public void setRemarksxx(String remarksxx) {
            Remarksxx = remarksxx;
        }

        public int getNoOfHours(){
            noOfHours = noOfDaysx * 8;
            return noOfHours;
        }

        public boolean isDataValid(){
            return isLeaveTypeValid() && isDateFromValid() && isDateThruValid() && isRemarksValid();
        }

        private boolean isDateFromValid(){
            if(dateFromx.isEmpty()){
                message = "Please select starting date of leave";
                return false;
            }
            return true;
        }

        private boolean isDateThruValid(){
            if(dateThrux.isEmpty()){
                message = "Please select ending date of leave";
                return false;
            }
            return true;
        }

        private boolean isLeaveTypeValid(){
            if(leaveType.isEmpty()){
                message = "Please select type of leave";
                return false;
            }
            return true;
        }

        private boolean isRemarksValid(){
            if(Remarksxx.isEmpty()){
                message = "Please provide your purpose on remarks area";
                return false;
            }
            return true;
        }
    }

    public static class LeaveApprovalInfo {

        private String TransNox = "";
        private String AppldFrx = "";
        private String AppldTox = "";
        private String WithPayx = "";
        private String WithOPay = "";
        private String Approved = "";
        private String Approvex = "";
        private String TranStat = "";

        private String message;

        public LeaveApprovalInfo(){

        }

        public String getMessage(){
            return message;
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

        public String getWithPayx() {
            return WithPayx;
        }

        public void setWithPayx(String withPayx) {
            WithPayx = withPayx;
        }

        public String getWithOPay() {
            return WithOPay;
        }

        public void setWithOPay(String withOPay) {
            WithOPay = withOPay;
        }

        public String getApprovex() {
            return Approvex;
        }

        public void setApprovex(String approvex) {
            Approvex = approvex;
        }

        public String getApproved() {
            return Approved;
        }

        public void setApproved(String approved) {
            Approved = approved;
        }

        public String getTranStat() {
            return TranStat;
        }

        public void setTranStat(String tranStat) {
            TranStat = tranStat;
        }

        public boolean isDataValid(){
            if(TransNox.isEmpty()){
                message = "No leave to approve or cancel";
                return false;
            } else if(WithPayx == null || WithPayx.isEmpty()){
                message = "With pay value is invalid.";
                return false;
            } else if(WithOPay == null || WithOPay.isEmpty()){
                message = "Without pay value is invalid.";
                return false;
            } else if (Integer.parseInt(WithPayx) < 0){
                message = "Negative numbers are not allowed";
                return false;
            } else if(Integer.parseInt(WithOPay) < 0){
                message = "Negative numbers are not allowed";
                return false;
            }
            return true;
        }
    }
}
