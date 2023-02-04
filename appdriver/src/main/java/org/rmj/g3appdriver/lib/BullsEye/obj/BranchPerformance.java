package org.rmj.g3appdriver.lib.BullsEye.obj;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.BullsEye.ABPM;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class BranchPerformance implements ABPM {
    private static final String TAG = BranchPerformance.class.getSimpleName();

    private DBranchPerformance poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public BranchPerformance(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).BranchPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public boolean ImportData(String args) {
        try{
            int lsUserLvl = poDao.GetUserLevel();
            String lsDeptIDx = poDao.GetUserDepartment();

            if(!lsDeptIDx.equalsIgnoreCase(DeptCode.SALES) ||
                    !lsDeptIDx.equalsIgnoreCase(DeptCode.CREDIT_SUPPORT_SERVICES)){
                message = "Your department code is not authorize to download branch performance info";
                return false;
            }

            if(lsUserLvl != DeptCode.LEVEL_AREA_MANAGER ||
                    lsUserLvl != DeptCode.LEVEL_BRANCH_HEAD){
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

    @Override
    public LiveData<List<EAreaPerformance>> GetPeriodicAreaPerformance() {
        return null;
    }

    @Override
    public LiveData<List<EAreaPerformance>> GetBranchesPerformance(String args) {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
