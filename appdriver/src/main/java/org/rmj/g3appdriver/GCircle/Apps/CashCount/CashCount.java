package org.rmj.g3appdriver.GCircle.Apps.CashCount;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECashCount;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.Etc.DeptCode;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CashCount {
    private static final String TAG = CashCount.class.getSimpleName();

    private final DCashCount poDao;

    private final EmployeeMaster poUser;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;
    private final EmployeeSession poSession;

    private String message;

    public CashCount(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CashCountDao();
        this.poUser = new EmployeeMaster(instance);
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = EmployeeSession.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EBranchInfo> GetBranchForCashCount(String args){
        return poDao.GetBranchForCashCount(args);
    }

    public List<EBranchInfo> GetBranchesForCashCount(){
        try{
            int lnLogsxx = poDao.CheckIfHasSelfieLog(AppConstants.CURRENT_DATE());
            if(lnLogsxx == 0){
                message = "No selfie log record found for this day.";
                return null;
            }

            List<EBranchInfo> loList = poDao.GetBranchesForCashCount(AppConstants.CURRENT_DATE());
            if(loList.size() == 0 ){
                message = "All branches on selfie log has cash count record.";
                return null;
            }

            return loList;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public String SaveCashCount(JSONObject foVal){
        try{
            String lsBranchCd = foVal.getString("sBranchCd");
            double lsPettyAmt = foVal.getDouble("nPettyAmt");
            String lsEntryDte = foVal.getString("dEntryDte");
            String lsTransact = AppConstants.CURRENT_DATE();
            int lnCn0001cx = foVal.getInt("nCn0001cx");
            int lnCn0005cx = foVal.getInt("nCn0005cx");
            int lnCn0010cx = foVal.getInt("nCn0010cx");
            int lnCn0025cx = foVal.getInt("nCn0025cx");
            int lnCn0050cx = foVal.getInt("nCn0050cx");
            int lnCn0001px = foVal.getInt("nCn0001px");
            int lnCn0005px = foVal.getInt("nCn0005px");
            int lnCn0010px = foVal.getInt("nCn0010px");
            int lnNte0020p = foVal.getInt("nNte0020p");
            int lnNte0050p = foVal.getInt("nNte0050p");
            int lnNte0100p = foVal.getInt("nNte0100p");
            int lnNte0200p = foVal.getInt("nNte0200p");
            int lnNte0500p = foVal.getInt("nNte0500p");
            int lnNte1000p = foVal.getInt("nNte1000p");

            ECashCount loDetail = poDao.CheckCashCountIfExist(lsBranchCd,
                    lsPettyAmt,
                    lsEntryDte,
                    lsTransact,
                    lnCn0001cx,
                    lnCn0005cx,
                    lnCn0010cx,
                    lnCn0025cx,
                    lnCn0050cx,
                    lnCn0001px,
                    lnCn0005px,
                    lnCn0010px,
                    lnNte0020p,
                    lnNte0050p,
                    lnNte0100p,
                    lnNte0200p,
                    lnNte0500p,
                    lnNte1000p);

            if(loDetail != null){
                if(loDetail.getSendStat() == 1){
                    message = "Cash count entry was already save.";
                    return null;
                }

                return loDetail.getTransNox();
            }

            ECashCount loCash = new ECashCount();
            String lsTransNo = CreateUniqueID();
            loCash.setTransNox(lsTransNo);
            loCash.setBranchCd(foVal.getString("sBranchCd"));
            loCash.setTransact(AppConstants.CURRENT_DATE());
            loCash.setCn0001cx(foVal.getInt("nCn0001cx"));
            loCash.setCn0005cx(foVal.getInt("nCn0005cx"));
            loCash.setCn0010cx(foVal.getInt("nCn0010cx"));
            loCash.setCn0025cx(foVal.getInt("nCn0025cx"));
            loCash.setCn0050cx(foVal.getInt("nCn0050cx"));
            loCash.setCn0001px(foVal.getInt("nCn0001px"));
            loCash.setCn0005px(foVal.getInt("nCn0005px"));
            loCash.setCn0010px(foVal.getInt("nCn0010px"));
            loCash.setNte0020p(foVal.getInt("nNte0020p"));
            loCash.setNte0050p(foVal.getInt("nNte0050p"));
            loCash.setNte0100p(foVal.getInt("nNte0100p"));
            loCash.setNte0200p(foVal.getInt("nNte0200p"));
            loCash.setNte0500p(foVal.getInt("nNte0500p"));
            loCash.setNte1000p(foVal.getInt("nNte1000p"));
            loCash.setORNoxxxx(foVal.getString("sORNoxxxx"));
            loCash.setSINoxxxx(foVal.getString("sSINoxxxx"));
            loCash.setPRNoxxxx(foVal.getString("sPRNoxxxx"));
            loCash.setCRNoxxxx(foVal.getString("sCRNoxxxx"));
            loCash.setEntryDte(foVal.getString("dEntryDte"));
            loCash.setReqstdBy(foVal.getString("sReqstdBy"));
            loCash.setORNoxNPt(foVal.getString("sORNoxNPt"));
            loCash.setPRNoxNPt(foVal.getString("sPRNoxNPt"));
            loCash.setDRNoxxxx(foVal.getString("sDRNoxxxx"));
            loCash.setPettyAmt(foVal.getDouble("nPettyAmt"));
            loCash.setRemarksx(foVal.getString("sRemarksx"));
            poDao.SaveCashCount(loCash);
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public List<QuickSearchNames> GetQuickSearchNames(String fsVal){
        try{
            List<QuickSearchNames> loNames = new ArrayList<>();

            JSONObject params = new JSONObject();
            params.put("reqstdnm", fsVal);
            params.put("bsearch", true);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlKwiksearch(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONArray jsonA = loResponse.getJSONArray("detail");
            for (int x = 0;  x < jsonA.length(); x++) {
                JSONObject jsonDetail = jsonA.getJSONObject(x);
                QuickSearchNames loDetails = new QuickSearchNames(
                        jsonDetail.getString("reqstdnm"),
                        jsonDetail.getString("reqstdid"),
                        jsonDetail.getString("department"));
                loNames.add(loDetails);
            }

            return loNames;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean UploadCashCount(String fsVal){
        try{
            ECashCount loCash = poDao.GetCashCountDetail(fsVal);

            if(loCash == null){
                message = "Unable to find record for posting.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("nCn0001cx", loCash.getCn0001cx());
            params.put("nCn0005cx", loCash.getCn0005cx());
            params.put("nCn0010cx", loCash.getCn0010cx());
            params.put("nCn0025cx", loCash.getCn0025cx());
            params.put("nCn0050cx", loCash.getCn0050cx());
            params.put("nCn0001px", loCash.getCn0001px());
            params.put("nCn0005px", loCash.getCn0005px());
            params.put("nCn0010px", loCash.getCn0010px());
            params.put("nNte0020p", loCash.getNte0020p());
            params.put("nNte0050p", loCash.getNte0050p());
            params.put("nNte0100p", loCash.getNte0100p());
            params.put("nNte0200p", loCash.getNte0200p());
            params.put("nNte0500p", loCash.getNte0500p());
            params.put("nNte1000p", loCash.getNte1000p());
            params.put("sBranchCd", loCash.getBranchCd());
            params.put("nPettyAmt", loCash.getPettyAmt());
            params.put("sORNoxxxx", loCash.getORNoxxxx());
            params.put("sSINoxxxx", loCash.getSINoxxxx());
            params.put("sPRNoxxxx", loCash.getPRNoxxxx());
            params.put("sCRNoxxxx", loCash.getCRNoxxxx());
            params.put("sORNoxNPt", loCash.getORNoxNPt());
            params.put("sPRNoxNPt", loCash.getPRNoxNPt());
            params.put("sDRNoxxxx", loCash.getDRNoxxxx());
            params.put("dTransact", loCash.getTransact());
            params.put("dEntryDte", loCash.getEntryDte());
            params.put("sReqstdBy", loCash.getReqstdBy());
            params.put("sRemarksx", loCash.getRemarksx());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSubmitCashcount(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            String lsTransNo = loResponse.getString("realnox");
            String lsRecvedx = loResponse.getString("received");

            poDao.UpdateUploadedCashCount(lsTransNo, loCash.getTransNox(), lsRecvedx);
            Log.d(TAG, "Cash count uploaded successfully");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadCashCountEntries(){
        try{
            List<ECashCount> loList = poDao.GetUnsentCashCountEntries();

            if(loList == null){
                message = "No cash count record found";
                return false;
            }

            if(loList.size() == 0){
                message = "No cash count record found";
                return false;
            }

            for(int x= 0; x < loList.size(); x++){
                ECashCount loCash = loList.get(x);

                JSONObject params = new JSONObject();
                params.put("nCn0001cx", loCash.getCn0001cx());
                params.put("nCn0005cx", loCash.getCn0005cx());
                params.put("nCn0010cx", loCash.getCn0010cx());
                params.put("nCn0025cx", loCash.getCn0025cx());
                params.put("nCn0050cx", loCash.getCn0050cx());
                params.put("nCn0001px", loCash.getCn0001px());
                params.put("nCn0005px", loCash.getCn0005px());
                params.put("nCn0010px", loCash.getCn0010px());
                params.put("nNte0020p", loCash.getNte0020p());
                params.put("nNte0050p", loCash.getNte0050p());
                params.put("nNte0100p", loCash.getNte0100p());
                params.put("nNte0200p", loCash.getNte0200p());
                params.put("nNte0500p", loCash.getNte0500p());
                params.put("nNte1000p", loCash.getNte1000p());
                params.put("sBranchCd", loCash.getBranchCd());
                params.put("nPettyAmt", loCash.getPettyAmt());
                params.put("sORNoxxxx", loCash.getORNoxxxx());
                params.put("sSINoxxxx", loCash.getSINoxxxx());
                params.put("sPRNoxxxx", loCash.getPRNoxxxx());
                params.put("sCRNoxxxx", loCash.getCRNoxxxx());
                params.put("sORNoxNPt", loCash.getORNoxNPt());
                params.put("sPRNoxNPt", loCash.getPRNoxNPt());
                params.put("sDRNoxxxx", loCash.getDRNoxxxx());
                params.put("dTransact", loCash.getTransact());
                params.put("dEntryDte", loCash.getEntryDte());
                params.put("sReqstdBy", loCash.getReqstdBy());
                params.put("sRemarksx", loCash.getRemarksx());

                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlSubmitCashcount(),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = SERVER_NO_RESPONSE;
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = getErrorMessage(loError);
                    Thread.sleep(1000);
                    continue;
                }

                String lsTransNo = loResponse.getString("realnox");
                String lsRecvedx = loResponse.getString("received");

                poDao.UpdateUploadedCashCount(lsTransNo, loCash.getTransNox(), lsRecvedx);
                Log.d(TAG, "Cash count uploaded successfully");
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    /**
     *
     * @param fsVal pass the branch code for the cash count entry basis
     * @return method returns an integer value which will indicate the result of validation
     *  0. if an error occurred during the validation of entry
     *  1. if no cash count entries. Which will make the user to proceed on cash count entry
     *  2. if a cash count entry has already exist. This must warned the user and user must have an option to proceed or cancel.
     *  3. if the current user is not authorized to create cash count entry.
     *  3. if no branch is selected for new entry.
     */
    public int ValidateCashCount(String fsVal){
        try{
            int lsEmpLvl = poDao.GetEmployeeLevel();

            if(lsEmpLvl != DeptCode.LEVEL_AREA_MANAGER){
                message = "User is not authorize to create cash count entry.";
                return 3;
            }

            if(fsVal == null){
                message = "No branch code basis for new cash count entry.";
                return 3;
            }

            if(fsVal.isEmpty()){
                message = "No branch code basis for new cash count entry.";
                return 3;
            }

            String lsAreaCd = poDao.GetBranchAreaCode(fsVal);
            String lsUserCd = poDao.GetUserAreaCode();

            if(!lsUserCd.equalsIgnoreCase(lsAreaCd)){
                message = "User is not authorize to create cash count entry for branches not included in area.";
                return 3;
            }

            ECashCount loDetail = poDao.GetCashCountForBranch(fsVal, AppConstants.CURRENT_DATE());

            if(loDetail == null){
                message = "No cash count entries";
                return 1;
            }

            message = "Cash count for this branch already exist.";
            return 2;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    public boolean HasUnfinishedCashCount(){
        try{
            if(!poSession.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))){
                message = "Employee is not authorized to use cash count.";
                return false;
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
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
}
