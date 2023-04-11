package org.rmj.g3appdriver.lib.GawadPacita.Obj;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPacita;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Pacita {
    private static final String TAG = Pacita.class.getSimpleName();

    private final DPacita poDao;
    private final HttpHeaders poHeaders;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;

    private String message;

    public Pacita(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).pacitaDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
    }

    public String getMessage() {
        return message;
    }

    public List<BranchRate> GetBranchRates(){
        try{
            List<BranchRate> loRatings = new ArrayList<>();

            loRatings.add(new BranchRate(1, "Branch Cleanliness", ""));
            loRatings.add(new BranchRate(2, "Comfort Room Cleanliness", ""));
            loRatings.add(new BranchRate(3, "Store Ambiance", ""));
            loRatings.add(new BranchRate(4, "Product Presence", ""));
            loRatings.add(new BranchRate(5, "Services and Accommodation", ""));

            return new ArrayList<>();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public LiveData<List<EBranchInfo>> GetBranchesList(){
        return poDao.GetBranchList();
    }

    public LiveData<List<DPacita.BranchRecords>> GetBranchRecords(String BranchCD){
        return poDao.GetBranchRecords(BranchCD);
    }

    /**
     *
     * @return true if the operation for importing pacita rules is successful else false if failed.
     * call getMessage() method to identify the error message
     */
    public boolean ImportPacitaRules(){
        try{
            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlGetPacitaRules(poConfig.isBackUpServer()),
                    new JSONObject().toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                Log.e(TAG, message);
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);

                int lnEntryNo = loJson.getInt("nEntryNox");

                EPacitaRule loDetail = poDao.GetPacitaRule(lnEntryNo);

                if(loDetail == null){

                    EPacitaRule loInfo = new EPacitaRule();
                    loInfo.setEntryNox(loJson.getInt("nEntryNox"));
                    loInfo.setFieldNmx(loJson.getString("sFieldNmx"));
                    loInfo.setMaxValue(loJson.getDouble("nMaxValue"));
                    loInfo.setRecdStat(loJson.getString("cRecdStat"));
                    loInfo.setParentxx(loJson.getString("cParentxx"));
                    loInfo.setModified(loJson.getString("dModified"));
                    loInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.Save(loInfo);
                    Log.d(TAG, "Pacita rule info has been saved.");
                } else {

                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setEntryNox(loJson.getInt("nEntryNox"));
                        loDetail.setFieldNmx(loJson.getString("sFieldNmx"));
                        loDetail.setMaxValue(loJson.getDouble("nMaxValue"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setParentxx(loJson.getString("cParentxx"));
                        loDetail.setModified(loJson.getString("dModified"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.Update(loDetail);
                        Log.d(TAG, "Pacita rule info has been updated.");
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

    /**
     *
     * @param BranchCD pass the branch code in order to identify which record of
     *                 evaluations should be retrieve from server and local.
     * @return true if import operation succeeds else false if failed.
     * Call the getMessage() method to identify the error message.
     */
    public boolean ImportPacitaEvaluations(String BranchCD){
        try{
            JSONObject params = new JSONObject();
            params.put("sBranchCd", BranchCD);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlGetPacitaEvaluations(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                Log.e(TAG, message);
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);

                String lsTransNox = loJson.getString("sTransNox");
                EPacitaEvaluation loDetail = poDao.GetEvaluationForInitialization(lsTransNox);

                if(loDetail == null){

                    EPacitaEvaluation loInfo = new EPacitaEvaluation();
                    loInfo.setTransNox(loJson.getString("sTransNox "));
                    loInfo.setTransact(loJson.getString("dTransact"));
                    loInfo.setUserIDxx(loJson.getString("sUserIDxx"));
                    loInfo.setBranchCD(loJson.getString("sBranchCD"));
                    loInfo.setPayloadx(loJson.getString("sPayloadx"));
                    loInfo.setRatingxx(loJson.getDouble("nRatingxx"));
                    loInfo.setModified(loJson.getString("dModified"));
                    loInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.Save(loInfo);
                    Log.d(TAG, "Pacita evaluation info has been saved.");
                } else {

                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setTransNox(loJson.getString("sTransNox "));
                        loDetail.setTransact(loJson.getString("dTransact"));
                        loDetail.setUserIDxx(loJson.getString("sUserIDxx"));
                        loDetail.setBranchCD(loJson.getString("sBranchCD"));
                        loDetail.setPayloadx(loJson.getString("sPayloadx"));
                        loDetail.setRatingxx(loJson.getDouble("nRatingxx"));
                        loDetail.setModified(loJson.getString("dModified"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.Update(loDetail);
                        Log.d(TAG, "Pacita evaluation info has been updated.");
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

    public boolean UpdateBranchRate(String TransNox, int EntryNox, String Result){
        try{
            EPacitaEvaluation loDetail = poDao.GetEvaluation(TransNox);

            String lsPayload = loDetail.getPayloadx();

            JSONArray laJson = new JSONArray(lsPayload);
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                int lnEntryNo = loJson.getInt("nEntryNox");
                if(EntryNox == lnEntryNo){
                    loJson.put("xRatingxx", Result);
                    laJson.put(loJson);
                    Log.d(TAG, "Entry No. " + EntryNox + ", has been updated!");
                    break;
                }
            }

            loDetail.setPayloadx(laJson.toString());
            poDao.Update(loDetail);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    /**
     *
     * @param TransNox pass the Transaction No. of the evaluation record to identify which record will be retrieve and upload to server.
     * @return true if the operation is successful else false if failed.
     * call the getMessage() method to identify the error message.
     */
    public boolean SaveBranchRatings(String TransNox){
        try{
            EPacitaEvaluation loDetail = poDao.GetEvaluation(TransNox);

            if(loDetail == null){
                message = "Unable to find record for posting.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("sBranchCD", loDetail.getBranchCD());
            params.put("sEvalType", loDetail.getEvalType());

            JSONArray loPayload = new JSONArray(loDetail.getPayloadx());
            params.put("sPayloadx", loPayload);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSubmitPacitaResult(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                Log.e(TAG, message);
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            loDetail.setTranStat("1");
            poDao.Update(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    /**
     *
     * @param BranchCD pass the branch code on parameter to determine which branch is needed for evaluation
     * @return returns Transaction No. of the evaluation record else null if something went wrong.
     * Call the getMessage() method to identify the error message.
     *
     * Check the getTranStat value if the value is equal to 1 means the evaluation is no longer available to be update by the user.
     * else if zero evaluation is still able to be update by the user.
     *
     *
     */
    public String InitializePacitaEvaluation(String BranchCD){
        try{
            EPacitaEvaluation loDetail = poDao.GetEvaluationForInitialization(BranchCD);

            if(loDetail != null){
                return loDetail.getTransNox();
            }

            //Create a JSONObject for all the rules on pacita_rule
            JSONArray laJson = new JSONArray();
            List<Integer> loFields = poDao.GetPacitaRules();

            if(loFields == null){
                message = "Unable to find pacita rules.";
                return null;
            }

            if(loFields.size() == 0){
                message = "Unable to find pacita rules.";
                return null;
            }

            for (int x = 0; x < loFields.size(); x++){
                JSONObject loJson = new JSONObject();
                loJson.put("nEntryNox", loFields.get(x));
                loJson.put("xRatingxx", "");
            }

            EPacitaEvaluation loInfo = new EPacitaEvaluation();
            String lsTransNo = CreateUniqueID();
            loInfo.setTransNox(lsTransNo);
            loInfo.setTransact(AppConstants.CURRENT_DATE);
            loInfo.setPayloadx(laJson.toString());
            loInfo.setRatingxx(0.0);
            loInfo.setBranchCD(BranchCD);
            loInfo.setUserIDxx(poDao.GetUserID());
            loInfo.setTimeStmp(new AppConstants().DATE_MODIFIED);
            poDao.Save(loInfo);
            return loInfo.getTransNox();

        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
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
