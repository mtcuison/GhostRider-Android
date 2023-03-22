package org.rmj.g3appdriver.lib.BullsEye.obj;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.BullsEye.ABPM;
import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;

import java.util.ArrayList;
import java.util.List;

public class BranchPerformance extends ABPM {
    private static final String TAG = BranchPerformance.class.getSimpleName();

    private final DBranchPerformance poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public BranchPerformance(Context context) {
        super(context);
        this.poDao = GGC_GriderDB.getInstance(context).BranchPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(context);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(context);
    }

    @Override
    public boolean ImportData() {
        try{
            int lsUserLvl = 4;
            String lsDeptIDx = poDao.GetUserDepartment();

//            if(!lsDeptIDx.equalsIgnoreCase(DeptCode.SALES) ||
//                    !lsDeptIDx.equalsIgnoreCase(DeptCode.CREDIT_SUPPORT_SERVICES)){
//                message = "Your department code is not authorize to download branch performance info";
//                return false;
//            }

            if(!lsDeptIDx.equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)) {
                if (lsUserLvl != DeptCode.LEVEL_AREA_MANAGER ||
                        lsUserLvl != DeptCode.LEVEL_BRANCH_HEAD) {
                    message = "User is not authorize to download area/branch performance";
                    return false;
                }
            }

            String lsAreaCode = poDao.GetAreaCode();

            if(lsAreaCode == null){
                message = "Unable to retrieve area code. Please re-login account.";
                return false;
            }

            ArrayList<String> lsPeriod = PerformancePeriod.getList();
            for(int i = 0; i < lsPeriod.size(); i++){

                JSONObject params = new JSONObject();
                params.put("period", lsPeriod.get(i));
                params.put("areacd", lsAreaCode);

                String lsResponse = WebClient.sendRequest(
                        poApi.getImportBranchPerformance(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                }

                JSONArray laJson = loResponse.getJSONArray("detail");
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = laJson.getJSONObject(x);

                    EBranchPerformance info = new EBranchPerformance();
                    info.setPeriodxx(loJson.getString("sPeriodxx"));
                    info.setBranchCd(loJson.getString("sBranchCd"));
                    info.setBranchNm(loJson.getString("sBranchNm"));
                    info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                    info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                    info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                    info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                    info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                    info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                    info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                    poDao.insert(info);
                    Log.d(TAG, "Branch performance for " + loJson.getString("sBranchCd") + " has been saved.");
//                    if(lsUserLvl != DeptCode.LEVEL_AREA_MANAGER){
//                    } else if(lsUserLvl != DeptCode.LEVEL_BRANCH_HEAD){
//
//                        String lsBranchCd = poDao.GetBranchCode();
//
//                        if(lsBranchCd.equalsIgnoreCase(loJson.getString("sBranchCd"))) {
//                            info.setPeriodxx(loJson.getString("sPeriodxx"));
//                            info.setBranchCd(loJson.getString("sBranchCd"));
//                            info.setBranchNm(loJson.getString("sBranchNm"));
//                            info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
//                            info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
//                            info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
//                            info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
//                            info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
//                            info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
//                            info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
//                            poDao.insert(info);
//                            Log.d(TAG, "Branch performance for " + loJson.getString("sBranchCd") + " has been saved.");
//                        }
//                    }
                }

                Thread.sleep(1000);

            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LiveData<String> GetCurrentMCSalesPerformance() {
        String lsBranchCD = poDao.GetBranchCode();
        return poDao.GetMCSalesPerformance(lsBranchCD);
    }

    @Override
    public LiveData<String> GetCurentSPSalesPerformance() {
        String lsBranchCD = poDao.GetBranchCode();
        return poDao.GetSPSalesPerformance(lsBranchCD);
    }

    @Override
    public LiveData<String> GetJobOrderPerformance() {
        String lsBranchCD = poDao.GetBranchCode();
        return poDao.GetJobOrderPerformance(lsBranchCD);
    }

    public LiveData<String> GetCurrentMCSalesPerformance(String args) {
        return poDao.GetMCSalesPerformance(args);
    }

    public LiveData<String> GetCurentSPSalesPerformance(String args) {
        return poDao.GetSPSalesPerformance(args);
    }

    public LiveData<String> GetJobOrderPerformance(String args) {
        return poDao.GetJobOrderPerformance(args);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetMCSalesPeriodicPerformance(String BranchCd){
        return poDao.GetMCSalesPeriodicPerformance(BranchCd);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetSPSalesPeriodicPerformance(String BranchCd){
        return poDao.GetSPSalesPeriodicPerformance(BranchCd);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetJobOrderPeriodicPerformance(String BranchCd){
        return poDao.GetJobOrderPeriodicPerformance(BranchCd);
    }


}
