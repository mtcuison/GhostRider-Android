package org.rmj.g3appdriver.lib.BullsEye.obj;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.BullsEye.ABPM;
import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;

import java.util.ArrayList;
import java.util.List;

public class AreaPerformance extends ABPM {
    private static final String TAG = AreaPerformance.class.getSimpleName();

    private final DAreaPerformance poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public AreaPerformance(Context context) {
        super(context);
        this.poDao = GGC_GriderDB.getInstance(context).AreaPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(context);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(context);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean ImportData() {
        try{
            int lsUserLvl = poDao.GetUserLevel();
            String lsDeptIDx = poDao.GetUserDepartment();

//            if(lsDeptIDx.equalsIgnoreCase(DeptCode.SALES)){
//                message = "Your department code is not authorize to download branch performance info";
//                return false;
//            }
//
//            if(lsUserLvl != DeptCode.LEVEL_AREA_MANAGER ||
//                    lsUserLvl != DeptCode.LEVEL_BRANCH_HEAD){
//                message = "User is not authorize to download area/branch performance";
//                return false;
//            }
//
            String lsAreaCode = poDao.GetAreaCode();
//
//            if(lsAreaCode == null){
//                message = "Unable to retrieve area code. Please re-login account.";
//                return false;
//            }

            ArrayList<String> lsPeriod = PerformancePeriod.getList();
            for(int i = 0; i < lsPeriod.size(); i++){
                JSONObject params = new JSONObject();
                params.put("period", lsPeriod.get(i));
                params.put("areacd", lsAreaCode);

                String lsRespones = WebClient.sendRequest(
                        poApi.getImportAreaPerformance(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsRespones == null){
                    message = "Server no response.";
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsRespones);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Thread.sleep(1000);
                    continue;
                }

                JSONArray laJson = loResponse.getJSONArray("detail");
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = laJson.getJSONObject(x);
                    EAreaPerformance loDetail = poDao.GetAreaPerformance(
                            loJson.getString("sPeriodxx"),
                            loJson.getString("sAreaCode"));

                    if(loDetail == null){

                        EAreaPerformance loArea = new EAreaPerformance();
                        loArea.setPeriodxx(loJson.getString("sPeriodxx"));
                        loArea.setAreaCode(loJson.getString("sAreaCode"));
                        loArea.setAreaDesc(loJson.getString("sAreaDesc"));
                        loArea.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                        loArea.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                        loArea.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                        loArea.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                        loArea.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                        loArea.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                        loArea.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                        poDao.insert(loArea);
                        Log.d(TAG, "Area performance for " + lsAreaCode + " has been saved.");

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

    public LiveData<List<EAreaPerformance>> GetPeriodicAreaPerformance() {
        return poDao.getAllAreaPerformanceInfo();
    }

    @Override
    public LiveData<String> GetCurrentMCSalesPerformance() {
        return poDao.GetMCSalesPerformance();
    }

    @Override
    public LiveData<String> GetCurentSPSalesPerformance() {
        return poDao.GetSPSalesPerformance();
    }

    @Override
    public LiveData<String> GetJobOrderPerformance() {
        return poDao.GetJobOrderPerformance();
    }

    public LiveData<String> getAreaDescription(){
        return poDao.getAreaDescription();
    }

    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForMCSales(){
        return poDao.GetTopBranchPerformerForMCSales();
    }

    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForSPSales(){
        return poDao.GetTopBranchPerformerForSPSales();
    }

    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForJobOrder(){
        return poDao.GetTopBranchPerformerForJobOrder();
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetMCSalesBranchesPerformance(){
        return poDao.GetMCSalesBranchesPerformance();
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetSPSalesBranchesPerformance(){
        return poDao.GetSPSalesBranchesPerformance();
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetJobOrderBranchesPerformance(){
        return poDao.GetJobOrderBranchesPerformance();
    }

    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetMCSalesPeriodicPerformance(){
        return poDao.GetMCSalesPeriodicPerformance();
    }

    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetSPSalesPeriodicPerformance(){
        return poDao.GetSPSalesPeriodicPerformance();
    }

    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetJobOrderPeriodicPerformance(){
        return poDao.GetJobOrderPeriodicPerformance();
    }

}
