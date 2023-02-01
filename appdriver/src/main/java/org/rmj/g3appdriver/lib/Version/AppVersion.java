package org.rmj.g3appdriver.lib.Version;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.dev.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.WebApi;

public class AppVersion {
    private static final String TAG = AppVersion.class.getSimpleName();

    private final AppConfigPreference poConfig;
    private final Telephony poTlphony;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;
    private final EmployeeMaster poUser;

    private String message;

    public AppVersion(Application instance) {
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poTlphony = new Telephony(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poUser = new EmployeeMaster(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean SubmitUserAppVersion(){
        try{
            EEmployeeInfo loUser = poUser.getUserNonLiveData();
            JSONObject params = new JSONObject();
            params.put("sUserIDxx", loUser.getUserIDxx());
            params.put("sProdctID", poConfig.ProducID());
            params.put("sIMEINoxx", poTlphony.getDeviceID());
            params.put("sAppVersn", poConfig.getVersionName());

            String lsAddress = poApi.getUrlSubmitAppVersion(poConfig.isBackUpServer());

            String lsResponse = WebClient.sendRequest(
                    lsAddress,
                    new JSONObject().toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                Log.e(TAG, loError.toString());
                return false;
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public VersionInfo GetUpdateInfo(){
        try{
            VersionInfo loVersion = new VersionInfo();
            String lsAddress = poApi.getUrlCheckUpdate(poConfig.isBackUpServer());

            String lsResponse = WebClient.sendRequest(
                    lsAddress,
                    new JSONObject().toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                Log.e(TAG, loError.toString());
                return null;
            }

            return loVersion;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public VersionInfo GetCurrentVersionInfo(){
        try{

            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public String GetVersionName(){
        return poConfig.getVersionName();
    }
}
