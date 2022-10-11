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

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.BranchPerformancePeriod;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class RBranchPerformance {
    private static final String TAG = RBranchPerformance.class.getSimpleName();

    private DBranchPerformance poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RBranchPerformance(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).BranchPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insertBulkData(List<EBranchPerformance> list) {
        poDao.insertBulkData(list);
    }

    public LiveData<List<EBranchPerformance>> getBranchPerformanceForDashBoard(){
        return poDao.getBranchPerformanceForDashBoard();
    }

    public LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceASC(){
        return poDao.getMCSalesBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceDESC(){
        return poDao.getMCSalesBranchPerformanceDESC();
    }

    public LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceASC(){
        return poDao.getSPSalesBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceDESC(){
        return poDao.getSPSalesBranchPerformanceDESC();
    }

    public LiveData<List<EBranchPerformance>> getJOBranchPerformanceASC(){
        return poDao.getJOBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getJOBranchPerformanceDESC(){
        return poDao.getJOBranchPerformanceDESC();
    }

    public String getUserAreaCode(){
        return poDao.getUserAreaCode();
    }

    public LiveData<List<EBranchPerformance>> getAllBranchPerformanceInfoByBranch(String branchCD){
        return poDao.getAllBranchPerformanceInfoByBranch(branchCD);
    }

    // For Area Monitoring
    public LiveData<List<EBranchPerformance>> getAreaBranchesMCSalesPerformance(String fsPeriodx) {
        return poDao.getAreaBranchesMCSalesPerformance(fsPeriodx);
    }

    public LiveData<List<EBranchPerformance>> getAreaBranchesSPSalesPerformance(String fsPeriodx) {
        return poDao.getAreaBranchesSPSalesPerformance(fsPeriodx);
    }

    public LiveData<DBranchPerformance.ActualGoal> getMCBranchPerformance(){
        return poDao.getMCBranchPerformance();
    }

    public LiveData<DBranchPerformance.ActualGoal> getSPBranchPerformance(){
        return poDao.getSPBranchPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getMCBranchPeriodicalPerformance(){
        return poDao.getMCBranchPeriodicalPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getSPBranchPeriodicalPerformance(){
        return poDao.getSPBranchPeriodicalPerformance();
    }

    public LiveData<EBranchPerformance> getCurrentPeriodPerformance(){
        return poDao.getCurrentPeriodPerformance();
    }

    public LiveData<DBranchPerformance.PeriodRange> getPeriodRange(){
        return poDao.getPeriodRange();
    }

    public LiveData<DBranchPerformance.MonthlyPieChart> getMonthlyPieChartData(String fsPeriodx) {
        return poDao.getMonthlyPieChartData(fsPeriodx);
    }

    public LiveData<DBranchPerformance.MonthlyPieChart> get12MonthPieChartData(String fsValue1, String fsValue2) {
        return poDao.get12MonthPieChartData(fsValue1, fsValue2);
    }

    public LiveData<DBranchPerformance.MonthlyPieChart> get12MonthBranchPieChartData(String sBranchCd, String fsValue1, String fsValue2) {
        return poDao.get12MonthBranchPieChartData(sBranchCd, fsValue1, fsValue2);
    }

    public boolean ImportData(){
        try{
            String lsUserLvl = poDao.GetUserLevel();
            String lsDeptIDx = poDao.GetUserDepartment();

            if(!lsDeptIDx.equalsIgnoreCase(DeptCode.SALES) ||
            !lsDeptIDx.equalsIgnoreCase(DeptCode.CREDIT_SUPPORT_SERVICES)){
                message = "Your department code is not authorize to download branch performance info";
                return false;
            }

            if(!lsUserLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER)) ||
                    !lsUserLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_BRANCH_HEAD))){
                message = "Your user level is not authorize to download area/branch performance";
                return false;
            }

            String lsAreaCode = poDao.GetAreaCode();

            if(lsAreaCode == null){
                message = "Unable to retrieve area code. Please re-login account.";
                return false;
            }

            ArrayList<String> lsPeriod = BranchPerformancePeriod.getList();
            for(int i = 0; i < lsPeriod.size(); i++){

                JSONObject params = new JSONObject();
                params.put("period", lsPeriod.get(i));
                params.put("areacd", lsAreaCode);

                String lsResponse = WebClient.httpsPostJSon(
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
                List<EBranchPerformance> branchInfo = new ArrayList<>();
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = laJson.getJSONObject(x);
                    EBranchPerformance loDetail = poDao.GetBranchPerformance(
                            loJson.getString("sPeriodxx"),
                            loJson.getString("sBranchCd"));

                    if(loDetail == null){

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

                    } else {

                    }
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
}
